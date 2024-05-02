package org.example.applicationcontext;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.example.Main;
import org.example.tomekcat.annotations.GetMapping;
import org.example.tomekcat.annotations.PostMapping;
import org.example.tomekcat.models.HttpServletRequest;

import java.lang.reflect.Method;

public final class ApplicationContext {

    private static ApplicationContext INSTANCE;

    private final Class<?> clazzRestControllerAnnotation;

    private ApplicationContext() {
        Main.BannerForSummerBoot();
        clazzRestControllerAnnotation = findClassWithResControllerAnnotation();
    }

    public static synchronized ApplicationContext getINSTANCE()
    {
        if(INSTANCE==null)
        {
            INSTANCE = new ApplicationContext();
        }
        return INSTANCE;
    }

    private Class<?> findClassWithResControllerAnnotation() {
        ScanResult scanResult = new ClassGraph()
                .enableAllInfo() //skanowania wszystkich informacji
                .scan();//skanowanie wszystkich pakietow

        ClassInfoList classesWithAnnotationRestController = scanResult
                .getClassesWithAnnotation("org.example.tomekcat.annotations.rest.RestController");

        if(classesWithAnnotationRestController.isEmpty()) return null;
        if(classesWithAnnotationRestController.size()>1) throw new ClassFormatError("There must be one class with the RestController annotation per application");
        return classesWithAnnotationRestController.getFirst().loadClass();
    }


    public void invokeMethodFromRestController(HttpServletRequest httpServletRequest)
    {

        try {
            //referencje do metadanych klasy ktora ma @RestController annotation

            //utworzenie jej obiektu
            Object instance = clazzRestControllerAnnotation.getDeclaredConstructor(null).newInstance();

            String method = httpServletRequest.getMethod();
            String endpoint = httpServletRequest.getEndpoint();
            String body = httpServletRequest.getBody();


            if(method.equals("GET"))
            {
                for (Method methodFromClazz: clazzRestControllerAnnotation.getDeclaredMethods())
                {
                    if(methodFromClazz.isAnnotationPresent(GetMapping.class))
                    {
                        if(endpoint.equals(methodFromClazz.getDeclaredAnnotation(GetMapping.class).value()))
                        {
                            var result = methodFromClazz.invoke(instance);
                            System.out.println(result);
                        }
                    }
                }
            }

            if(method.equals("POST")){

                for (Method methodFromClazz: clazzRestControllerAnnotation.getDeclaredMethods())
                {
                    if(methodFromClazz.isAnnotationPresent(PostMapping.class))
                    {
                        if(endpoint.equals(methodFromClazz.getDeclaredAnnotation(PostMapping.class).value()))
                        {
                            var result = methodFromClazz.invoke(instance);
                            System.out.println(result);
                        }
                    }
                }
            }




        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }






}
