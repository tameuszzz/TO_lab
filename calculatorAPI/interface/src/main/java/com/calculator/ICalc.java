package com.calculator;

public interface ICalc {
    double solve(String expression) throws NoSuchMethodException, IllegalAccessException;
}
