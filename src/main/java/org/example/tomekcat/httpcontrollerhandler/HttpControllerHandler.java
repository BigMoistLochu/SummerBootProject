package org.example.tomekcat.httpcontrollerhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.example.Main;
import org.example.tomekcat.annotations.GetMapping;
import org.example.tomekcat.annotations.PostMapping;
import org.example.tomekcat.exceptions.MultipleRestControllerException;
import org.example.tomekcat.models.HttpServletRequest;

import java.lang.reflect.Method;

public final class HttpControllerHandler {

    private static HttpControllerHandler INSTANCE;
    private final Class<?> clazzRestControllerAnnotation;

    private HttpControllerHandler() {
        Main.BannerForSummerBoot();
        clazzRestControllerAnnotation = findClassWithResControllerAnnotation();
    }

    public static synchronized HttpControllerHandler getINSTANCE()
    {
        if(INSTANCE==null)
        {
            INSTANCE = new HttpControllerHandler();
        }
        return INSTANCE;
    }

    private Class<?> findClassWithResControllerAnnotation() {
        ScanResult scanResult = new ClassGraph()
                .enableAllInfo() //skanowania wszystkich informacji
                .scan();//skanowanie wszystkich pakietow

        ClassInfoList classesWithAnnotationRestController = scanResult
                .getClassesWithAnnotation("org.example.tomekcat.annotations.RestController");

        if(classesWithAnnotationRestController.isEmpty()) return null;
        if(classesWithAnnotationRestController.size()>1) throw new MultipleRestControllerException("There must be one class with the RestController annotation per application");
        //referencje do metadanych klasy ktora ma @RestController annotation
        System.out.println("Znaleziono RestController");
        return classesWithAnnotationRestController.getFirst().loadClass();
    }


    public Object invokeMethodFromRestController(HttpServletRequest httpServletRequest)
    {

        try {

            //utworzenie obiektu RestController
            if(clazzRestControllerAnnotation==null) return null;

            Object instance = clazzRestControllerAnnotation.getDeclaredConstructor(null).newInstance();

            String endpoint = httpServletRequest.getEndpoint();


            //ta funkcja nie ma side efektow,

            for (Method methodFromClazz: clazzRestControllerAnnotation.getDeclaredMethods()) {
                if(methodFromClazz.isAnnotationPresent(GetMapping.class)) {
                        if(endpoint.equals(methodFromClazz.getDeclaredAnnotation(GetMapping.class).value())) {
                            var result = methodFromClazz.invoke(instance);
                            return result;
                        }
                }

                if(methodFromClazz.isAnnotationPresent(PostMapping.class)) {
                    if(endpoint.equals(methodFromClazz.getDeclaredAnnotation(PostMapping.class).value())) {
                        var result = methodFromClazz.invoke(instance);
                        return result;
                    }
                }
            }

            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }








}
