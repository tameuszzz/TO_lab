package com.calculator;

public interface ICalc {
    double solve(String expression);
    void setOperatorMap(String sign, Integer weight);
    void setAllOperators(String sign);
    public void setOperationMap(String sign , Object operation);

}
