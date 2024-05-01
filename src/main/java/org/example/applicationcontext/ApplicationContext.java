package org.example.applicationcontext;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.example.Main;
import org.example.tomekcat.annotations.rest.GetMapping;
import org.example.tomekcat.models.HttpServletRequest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public final class ApplicationContext {

    private static ApplicationContext INSTANCE;

    private final Class<?> clazz_With_Rest_Controller_Annotation;

    private ApplicationContext()
    {
        Main.BannerForSummerBoot();
        this.clazz_With_Rest_Controller_Annotation = findClassWithResControllerAnnotation();

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

        ClassInfoList classes = scanResult.getClassesWithAnnotation("org.example.tomekcat.annotations.rest.RestController");

        if(classes.size()==0) return null;
        if(classes.size()>1) throw new ClassFormatError("There must be one class with the RestController annotation per application");

        Class<?> klasa = classes.getFirst().loadClass();
        return klasa;
    }


    public void invoker(HttpServletRequest httpServletRequest)
    {

        try {
            //referencje do metadanych klasy ktora ma @RestController annotation
            Class<?> clazz = clazz_With_Rest_Controller_Annotation;
            //utworzenie jej obiektu
            Object instance = clazz.getDeclaredConstructor(null).newInstance();

            String method = "GET";
            String endpoint = httpServletRequest.getEndpoint();
            String body = httpServletRequest.getBody();


            if(method.equals("GET"))
            {
                for (Method methodFromClazz: clazz.getDeclaredMethods())
                {
                    if(methodFromClazz.isAnnotationPresent(GetMapping.class))
                    {
                        System.out.println(methodFromClazz.getDeclaredAnnotation(GetMapping.class).value());
                    }
                }
            }




        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    //Method getBooksInstanceMethod = klasa.getDeclaredMethod("getAllBooksToUser");
    //            //wywolanie tej metody
    //            var result  = getBooksInstanceMethod.invoke(instance);
    //            System.out.println(result);



}
