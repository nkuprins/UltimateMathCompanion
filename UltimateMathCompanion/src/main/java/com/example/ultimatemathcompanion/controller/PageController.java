package com.example.ultimatemathcompanion.controller;

import com.example.ultimatemathcompanion.datamodel.Expression;
import com.example.ultimatemathcompanion.service.ExpressionService;
import com.example.ultimatemathcompanion.service.TypesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class PageController {

    private final ExpressionService expressionService;
    private final TypesService typesService;
    private final ExpressionController expressionController = new ExpressionController();

    public PageController(ExpressionService expressionService, TypesService typesService) {
        this.expressionService = expressionService;
        this.typesService = typesService;
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/")
    public String showPage(@RequestParam(value = "id", required = false) String id, Model model) {

        List<Expression> expressions = expressionService.findAll();
        Expression formExpression = id == null ? new Expression() : expressionService.findById(Integer.parseInt(id));

        model.addAttribute("theExpressions", expressions);
        model.addAttribute("formExpression", formExpression);

        return "index";
    }

    @GetMapping("/deleteExpression")
    public String deleteExpression(@RequestParam("id") int id) {

        expressionService.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/save")
    public String saveExpression(@ModelAttribute("formExpression") Expression expression) {

        if (!expressionController.isValidFormat(expression.getExpression()))
            return "redirect:/"; // Format is not valid reset the page.

        expressionController.processExpression(expression, typesService);
        expressionService.save(expression);
        return "redirect:/";
    }

}
