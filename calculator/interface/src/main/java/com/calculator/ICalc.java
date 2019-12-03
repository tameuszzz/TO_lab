package com.calculator;

import java.lang.reflect.InvocationTargetException;

public interface ICalc {
    double solve(String expression) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
