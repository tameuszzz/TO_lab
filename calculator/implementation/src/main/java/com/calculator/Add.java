package com.calculator;

public class Add implements IPlugins{
    private String operator = "+";
    private int operatorWeight = 1;

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
        return a + b;
    }
}
