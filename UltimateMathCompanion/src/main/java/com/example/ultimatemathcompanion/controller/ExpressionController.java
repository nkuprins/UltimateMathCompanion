package com.example.ultimatemathcompanion.controller;

import com.example.ultimatemathcompanion.datamodel.Expression;
import com.example.ultimatemathcompanion.datamodel.ExpressionTypes;
import com.example.ultimatemathcompanion.math.Calculations;
import com.example.ultimatemathcompanion.service.ExpressionService;
import com.example.ultimatemathcompanion.service.ExpressionTypesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ExpressionController {

    private final ExpressionService expressionService;
    private final ExpressionTypesService expressionTypesService;

    // Keeps track if text in form field has valid format(SERVER-SIDE validation).
    private boolean formatIsValid = true;

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
        model.addAttribute("formatIsValid", formatIsValid);
        formatIsValid = true;

        return "expression-form";
    }

    @GetMapping("/updateExpression")
    public String updateExpression(@RequestParam("id") int id, Model model) {

        Expression expressions = expressionService.findById(id);
        model.addAttribute("theExpression", expressions);
        model.addAttribute("format", formatIsValid);
        formatIsValid = true;

        return "expression-form";
    }

    @GetMapping("/deleteExpression")
    public String deleteExpression(@RequestParam("id") int id) {

        expressionService.deleteById(id);
        return "redirect:/";
    }

    private void processExpression(Expression expression) {

        String theExpression = expression.getExpression();
        BigDecimal answer = Calculations.calculate(theExpression);
        expression.setAnswer(answer);

        int typeId = Calculations.getExpressionTypeId(theExpression);
        ExpressionTypes expressionTypes = expressionTypesService.findById(typeId);
        expression.setExpressionTypes(expressionTypes);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date currentDate = Date.valueOf(LocalDateTime.now().format(dateTimeFormatter));
        expression.setDate(currentDate);
    }

    @PostMapping("/save")
    public String saveExpression(@ModelAttribute("theExpression") Expression expression, HttpServletRequest request) {

        formatIsValid = Calculations.isValidFormat(expression.getExpression());
        if (!formatIsValid)
            return "redirect:"+ request.getHeader("Referer"); // Format is not valid reset the page.

        processExpression(expression);
        expressionService.save(expression);
        return "redirect:/";
    }

}
