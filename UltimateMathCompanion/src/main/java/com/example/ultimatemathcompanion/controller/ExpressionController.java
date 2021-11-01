package com.example.ultimatemathcompanion.controller;

import com.example.ultimatemathcompanion.datamodel.Expression;
import com.example.ultimatemathcompanion.service.ExpressionService;
import javassist.expr.Expr;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ExpressionController {

    private ExpressionService expressionService;

    public ExpressionController(ExpressionService expressionService) {
        this.expressionService = expressionService;
    }

    @GetMapping("/")
    public String showPage(Model model) {

        List<Expression> expressions = expressionService.findAll();
        model.addAttribute("theExpression", expressions);
        return "index";
    }

    @GetMapping("/addExpression")
    public String addExpression(Model model) {

        Expression expressions = new Expression();
        model.addAttribute("theExpression", expressions);
        return "expression-form";
    }

    @PostMapping("/save")
    public String saveExpression(@ModelAttribute("theExpression") Expression expression) {
        expressionService.save(expression);
        return "redirect:/";
    }

}
