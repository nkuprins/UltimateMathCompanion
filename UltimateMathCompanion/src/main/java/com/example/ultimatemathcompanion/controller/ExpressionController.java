package com.example.ultimatemathcompanion.controller;

import com.example.ultimatemathcompanion.datamodel.Expression;
import com.example.ultimatemathcompanion.datamodel.ExpressionTypes;
import com.example.ultimatemathcompanion.math.Calculations;
import com.example.ultimatemathcompanion.service.ExpressionService;
import com.example.ultimatemathcompanion.service.ExpressionTypesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    private boolean isValid = true;

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
        model.addAttribute("format", isValid);
        isValid = true;

        return "expression-form";
    }

    @GetMapping("/updateExpression")
    public String updateExpression(@RequestParam("id") int id, Model model) {

        Expression expressions = expressionService.findById(id);
        model.addAttribute("theExpression", expressions);
        model.addAttribute("format", isValid);
        isValid = true;

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

        int typeId = Calculations.getExpressionTypeId(theExpression);
        ExpressionTypes expressionTypes = expressionTypesService.findById(typeId);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date currentDate = Date.valueOf(LocalDateTime.now().format(dateTimeFormatter));

        expression.setAnswer(answer);
        expression.setDate(currentDate);
        expression.setExpressionTypes(expressionTypes);
    }

    @PostMapping("/save")
    public String saveExpression(@ModelAttribute("theExpression") Expression expression, HttpServletRequest request) {

        String theExpression = expression.getExpression();
        isValid = Calculations.isValidFormat(theExpression);
        if (!isValid)
            return "redirect:"+ request.getHeader("Referer");

        processExpression(expression);
        expressionService.save(expression);
        return "redirect:/";
    }

}
