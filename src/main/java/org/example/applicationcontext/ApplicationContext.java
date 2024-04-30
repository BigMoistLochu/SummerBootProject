package org.example.applicationcontext;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.example.Main;
import org.example.restapiclient.models.Book;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public final class ApplicationContext {

    private static ApplicationContext INSTANCE;

    private ApplicationContext()
    {
        Main.BannerForSummerBoot();

        //tutaj bedzie skanowanie wszystkich pakietuw jeszcze
        ScanResult scanResult = new ClassGraph()
                .enableAllInfo() //skanowania wszystkich informacji
                .scan();//skanowanie wszystkich pakietow

        ClassInfoList classes = scanResult.getClassesWithAnnotation("org.example.tomekcat.annotations.rest.RestController");
        classes.forEach(clazz -> {
            Class<?> klasa = clazz.loadClass();

            try {
                Object instance = klasa.getDeclaredConstructor(null).newInstance();
                Method getBooksInstanceMethod = klasa.getDeclaredMethod("getAllBooksToUser");
                var result  = getBooksInstanceMethod.invoke(instance);
                System.out.println(result);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static synchronized ApplicationContext getINSTANCE()
    {
        if(INSTANCE==null)
        {
            INSTANCE = new ApplicationContext();
        }
        return INSTANCE;
    }





}
