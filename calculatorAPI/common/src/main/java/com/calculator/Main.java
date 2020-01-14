package com.calculator;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.logging.*;

public class Main {
    private final static Logger logr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main( String[] args )
    {
        FileHandler fh;

        try {
            fh = new FileHandler(".\\myLogger.log");
            logr.addHandler(fh);
        } catch (IOException e) {
            logr.log(Level.SEVERE, e.getMessage(), e);
        }

        File pluginsDir = new File(".\\plugins");
        File plugins = new File(".\\plugins\\com\\calculator");
        String[] files = plugins.list();

        Calc calc = new Calc();

        if (files != null && files.length != 0) {
            try {
                URL loadPath = pluginsDir.toURI().toURL();
                URL[] classUrl = new URL[]{loadPath};
                ClassLoader cl = new URLClassLoader(classUrl);

                System.out.print("Plugins loaded: ");
                for (String file : files) {
                    System.out.print(file + ", ");
                    Class loadedClass = cl.loadClass("com.calculator." + file.replace(".class", ""));
                    Object obj = loadedClass.newInstance();
                    IPlugins plugin = (IPlugins) obj;
                    calc.setAllOperators(plugin.getOperator());
                    calc.setOperatorMap(plugin.getOperator(), plugin.getOperatorWeight());
                    calc.setOperationMap(plugin.getOperator(), plugin);
                }
            } catch (MalformedURLException | IllegalAccessException | InstantiationException | ClassNotFoundException e ) {
                logr.log(Level.SEVERE, e.getMessage(), e);
            }

            System.out.println();

//          calc.solve("((12 - 2&9) / 3)^2");

            Scanner scan = new Scanner(System.in);

            while(true) {
                System.out.print("\nEnter the expression (q - to exit): ");
                String s = scan.nextLine();
                if(s.isEmpty()) {
                    System.out.println("Empty input");
                    continue;
                }
                if(s.equals("q")) {
                    System.exit(0);
                } else {
                    try {
                        System.out.println("Result: " + calc.solve(s));
                    } catch (NumberFormatException | EmptyStackException | ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid input");
                    } catch (NoSuchMethodException | IllegalAccessException e) {
                        logr.log(Level.SEVERE, e.getMessage(), e);
                    }
                }
            }
        }
    }
}
