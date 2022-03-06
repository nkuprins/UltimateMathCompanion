package com.example.ultimatemathcompanion.controller;

import com.example.ultimatemathcompanion.datamodel.Expression;
import com.example.ultimatemathcompanion.datamodel.ExpressionTypes;
import com.example.ultimatemathcompanion.math.Calculations;
import com.example.ultimatemathcompanion.service.ExpressionService;
import com.example.ultimatemathcompanion.service.ExpressionTypesService;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/")
    public String showPage(@RequestParam(value = "id", required = false) String id, Model model) {
        List<Expression> expressions = expressionService.findAll();
        model.addAttribute("theExpression", expressions);
        Expression formExpression = id == null ? new Expression() : expressionService.findById(Integer.parseInt(id));
        model.addAttribute("formExpression", formExpression);

        return "index";
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
    public String saveExpression(@ModelAttribute("formExpression") Expression expression, HttpServletRequest request) {

        formatIsValid = Calculations.isValidFormat(expression.getExpression());
        if (!formatIsValid)
            return "redirect:/"; // Format is not valid reset the page.

        processExpression(expression);
        expressionService.save(expression);
        return "redirect:/";
    }

}
