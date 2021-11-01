package com.example.ultimatemathcompanion.controller;

import com.example.ultimatemathcompanion.datamodel.Expression;
import com.example.ultimatemathcompanion.datamodel.ExpressionTypes;
import com.example.ultimatemathcompanion.math.Calculations;
import com.example.ultimatemathcompanion.service.ExpressionService;
import com.example.ultimatemathcompanion.service.ExpressionTypesService;
import javassist.expr.Expr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ExpressionController {

    @Autowired
    private ExpressionService expressionService;

    @Autowired
    private ExpressionTypesService expressionTypesService;

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

    @GetMapping("/updateExpression")
    public String updateExpression(@RequestParam("id") int id, Model model) {

        Expression expressions = expressionService.findById(id);
        model.addAttribute("theExpression", expressions);
        return "expression-form";
    }

    @PostMapping("/save")
    public String saveExpression(@ModelAttribute("theExpression") Expression expression) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date currentDate = Date.valueOf(LocalDateTime.now().format(dateTimeFormatter));
        String theExpression = expression.getExpression();
        int typeId = Calculations.getExpressionTypeId(theExpression);

        expression.setAnswer(Calculations.calculate(theExpression));
        expression.setDate(currentDate);
        expression.setExpressionTypes(expressionTypesService.findById(typeId));

        expressionService.save(expression);
        return "redirect:/";
    }

}
