package com.calculator;

public interface IPlugins {
    double compute(double a, double b);
    String getOperator();
    int getOperatorWeight();
}
