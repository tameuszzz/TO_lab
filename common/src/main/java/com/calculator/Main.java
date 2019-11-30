package com.calculator;

public class Main {
    public static void main( String[] args )
    {
        ICalc calc = new Calc();
        calc.solve("(3+9)/4");
    }
}
