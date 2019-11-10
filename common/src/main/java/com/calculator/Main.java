package com.calculator;

public class Main {
    public static void main( String[] args )
    {
        ICalc a = new Calc();
        System.out.println(a.multiply(2,5));
    }
}
