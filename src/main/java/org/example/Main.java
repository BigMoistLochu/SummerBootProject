package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.tomekcat.ServletTomek;
import org.example.tomekcat.annotations.Person;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
     System.out.println("Start Aplikacji");
     ServletTomek servletTomek = ServletTomek.startServer();

//        Book bookTestReflection = new Book();
//
//        Field ksiazka = bookTestReflection.getClass().getDeclaredField("author");
//        ksiazka.setAccessible(true);
//
//        ksiazka.set(bookTestReflection,"wcisnalem wartosc");
//
//        System.out.println(bookTestReflection);
//
//        Method methoda = bookTestReflection.getClass().getDeclaredMethod("setAuthor",String.class);
//        methoda.invoke(bookTestReflection,"cwel cwel");
//
//        System.out.println(bookTestReflection);


//        Person persontest = new Person();
//        persontest.setName("personRandomName");
//        persontest.setSurname("personRandomName");
//        persontest.getPersonList().add(new Person("json","parker"));
//        ObjectMapper objectMapper = new ObjectMapper();
//        StringBuilder stringBuilder = new StringBuilder();
//
//        Method method = persontest.getClass().getDeclaredMethod("getPersonList");
//        System.out.println("brpoin");
////        System.out.println(method.invoke(persontest));
//        byte[] listOfbytes = objectMapper.writeValueAsBytes(method.invoke(persontest));
//
//        System.out.println(listOfbytes.length);
//        for (byte _byte:listOfbytes)
//        {
//            stringBuilder.append((char)_byte);
//        }
//
//        System.out.println(stringBuilder);



        //jak skonczymy robic servleta i skonczymy robic rescontroller
        //
    }



}

