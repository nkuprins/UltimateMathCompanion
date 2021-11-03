package com.example.ultimatemathcompanion.controller;

import com.example.ultimatemathcompanion.datamodel.Expression;
import com.example.ultimatemathcompanion.math.Calculations;
import com.example.ultimatemathcompanion.service.ExpressionService;
import com.example.ultimatemathcompanion.service.ExpressionTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ExpressionController {

    private ExpressionService expressionService;

    private ExpressionTypesService expressionTypesService;

    public ExpressionController(ExpressionService expressionService, ExpressionTypesService expressionTypesService) {
        this.expressionService = expressionService;
        this.expressionTypesService = expressionTypesService;
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

    @GetMapping("/updateExpression")
    public String updateExpression(@RequestParam("id") int id, Model model) {

        Expression expressions = expressionService.findById(id);
        model.addAttribute("theExpression", expressions);
        return "expression-form";
    }

    @GetMapping("/deleteExpression")
    public String deleteExpression(@RequestParam("id") int id) {

        expressionService.deleteById(id);
        return "redirect:/";
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
