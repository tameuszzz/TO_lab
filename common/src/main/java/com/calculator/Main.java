package com.calculator;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Main {
    public static void main( String[] args )
    {
        File pluginsDir = new File("C:\\Users\\mateu\\Desktop\\TO_lab\\plugins");
        File plugins = new File("C:\\Users\\mateu\\Desktop\\TO_lab\\plugins\\com\\calculator");
        String[] files = plugins.list();

        ICalc calc = new Calc();

        if (files.length != 0) {
            try {
                URL loadPath = pluginsDir.toURI().toURL();
                URL[] classUrl = new URL[]{loadPath};
                ClassLoader cl = new URLClassLoader(classUrl);
                System.out.print("Załadowane pluginy: ");
                for (String file : files) {
                    System.out.print(file + ", ");
                    Class loadedClass = cl.loadClass("com.calculator." + file.replace(".class", ""));
                    Object obj = loadedClass.newInstance();
                    IPlugins plugin = (IPlugins) obj;
                    calc.setAllOperators(plugin.getOperator());
                    calc.setOperatorMap(plugin.getOperator(), plugin.getOperatorWeight());
                    calc.setOperationMap(plugin.getOperator(), plugin);
                }

                calc.solve("((4 + 5) / 3)^2");

            } catch (MalformedURLException | ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
