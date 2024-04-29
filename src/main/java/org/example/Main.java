package org.example;


import org.example.tomekcat.servlet.ServletTomek;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
     System.out.println("Start Aplikacji");
     ServletTomek servletTomek = ServletTomek.startServer();




    }



}

