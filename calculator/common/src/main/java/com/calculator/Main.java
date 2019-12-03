package com.calculator;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.EmptyStackException;
import java.util.Scanner;

public class Main {
    public static void main( String[] args )
    {
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

                System.out.println();

                calc.solve("((12 - 2&9) / 3)^2");

                Scanner scan = new Scanner(System.in);
                System.out.print("Enter your expression (q - to quit): ");

                while(scan.hasNextLine()) {
                    String s = scan.nextLine();
                    if(s.equals("q")) {
                        System.exit(0);
                    } else {
                        calc.solve(s);
                    }
                    System.out.print("Enter the expression (q - to exit): ");
                }

            } catch (MalformedURLException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();

            } catch (NumberFormatException | EmptyStackException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid input");
            }
        }
    }
}
