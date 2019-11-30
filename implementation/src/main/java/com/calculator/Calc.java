package com.calculator;

import java.util.*;
import java.lang.reflect.InvocationTargetException;

public class Calc implements ICalc {

    private Map<String, Object> operationsMap = new HashMap<>();
    private Map<String, Integer> operatorsMap = new HashMap<>();

    private String allOperators = "+-/*()";

    public Calc() {
        operationsMap.put("+", new Add());
        operationsMap.put("-", new Subtract());
        operationsMap.put("*", new Multiply());
        operationsMap.put("/", new Divide());

        operatorsMap.put("+", 1);
        operatorsMap.put("-", 1);
        operatorsMap.put("*", 2);
        operatorsMap.put("/", 2);
        operatorsMap.put("(", -1);
        operatorsMap.put(")", -1);
    }

    public double solve(String expression) {
        Stack<String> operators = new Stack<>();
        List<String> numbers = new ArrayList<>();

        String exp = expression;

        for(int i = 0; i < allOperators.length(); i++)
        {
            if(expression.contains(Character.toString(allOperators.charAt(i))))
            {
                exp = exp.replace(Character.toString(allOperators.charAt(i)), " " + allOperators.charAt(i) + " ");
            }
        }

        exp = exp.replace("  ", " ");
        System.out.println("Expression: " + exp);
        String[] elements = exp.split(" ");

        for(String a: elements)
        {
            if(!a.equals(""))
            {
                if(!a.matches("["+allOperators.toString()+"]"))
                {
                    numbers.add(a);
                }

                else if (a.contains("("))
                {
                    operators.push(a);
                }

                else if (a.contains(")"))
                {
                    while (!operators.isEmpty() && !operators.peek().contains("("))
                    {
                        numbers.add(operators.pop());
                    }
                    operators.pop();
                }
                else
                {
                    while (!operators.isEmpty() && operatorsMap.get(a) <= operatorsMap.get(operators.peek()))
                    {
                        numbers.add(operators.pop());
                    }
                    operators.push(a);
                }
            }
        }

        while (!operators.isEmpty())
        {
            numbers.add(operators.pop());
        }

        System.out.println("RPN: " + numbers);

        for(int i = 0; i < numbers.size(); i++)
        {
            if(numbers.get(i).matches("["+allOperators.toString()+"]")) {
                try {
                    numbers.add(i + 1, operationsMap.get(numbers.get(i)).getClass().getMethod("compute", double.class, double.class).invoke(operationsMap.get(numbers.get(i)), Double.parseDouble(numbers.get(i - 2)), Double.parseDouble(numbers.get(i - 1))).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                for (int j = 0; j < 3; j++) {
                    numbers.remove(i - 2);
                }
                i = 0;
            }
        }

        System.out.println("Result: " + numbers.get(0));
        return Double.parseDouble(numbers.get(0));
    }

    public void setOperationMap(String sign , Object operation) {
        operationsMap.put(sign, operation);
    }

    public void setAllOperators(String sign) {
        allOperators += sign;
    }

    public void setOperatorMap(String sign, Integer weight) {
        operatorsMap.put(sign, weight);
    }
}
