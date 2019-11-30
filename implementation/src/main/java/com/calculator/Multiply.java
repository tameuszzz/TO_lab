package com.calculator;

public class Multiply implements IPlugins{
    private String operator = "*";
    private int operatorWeight = 2;

    @Override
    public String getOperator() {
        return operator;
    }

    @Override
    public int getOperatorWeight() {
        return operatorWeight;
    }

    @Override
    public double compute(double a, double b) {
        return a * b;
    }
}
