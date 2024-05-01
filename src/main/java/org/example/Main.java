package org.example;


import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.example.applicationcontext.ApplicationContext;
import org.example.tomekcat.servlet.ServletTomek;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
     System.out.println("Start Aplikacji");
     ServletTomek servletTomek = ServletTomek.startServer();





    }








    public static void BannerForSummerBoot()
    {
        System.out.println("\n" +
                "\n" +
                " __    __    __     _________                                    __________               __    __    __    __   \n" +
                " \\ \\   \\ \\   \\ \\   /   _____/__ __  _____   _____   ___________  \\______   \\ ____   _____/  |_  \\ \\   \\ \\   \\ \\  \n" +
                "  \\ \\   \\ \\   \\ \\  \\_____  \\|  |  \\/     \\ /     \\_/ __ \\_  __ \\  |    |  _//  _ \\ /  _ \\   __\\  \\ \\   \\ \\   \\ \\ \n" +
                "  / /   / /   / /  /        \\  |  /  Y Y  \\  Y Y  \\  ___/|  | \\/  |    |   (  <_> |  <_> )  |    / /   / /   / / \n" +
                " /_/   /_/   /_/  /_______  /____/|__|_|  /__|_|  /\\___  >__|     |______  /\\____/ \\____/|__|   /_/   /_/   /_/  \n" +
                "                          \\/            \\/      \\/     \\/                \\/                                      \n" +
                "\n");
    }



}

