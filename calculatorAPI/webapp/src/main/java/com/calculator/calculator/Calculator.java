package com.calculator.calculator;

import com.calculator.Calc;
import com.calculator.ICalc;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Calculator {

    ICalc calcObj;
    {
        try {
            calcObj = new Calc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    double result;

    @PostMapping("/index")
    public String calcApp(@RequestParam(name="problem", required=true) String problem,
                          Model model){
        model.addAttribute("resultText", "Result of your problem: ");
        try {
            result = calcObj.solve(problem);
        }
        catch(Exception e){
            model.addAttribute("resultText", "invalid Entry!");
        }
        model.addAttribute("result",result);
        return "index";
    }
}
