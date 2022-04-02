package com.example.ultimatemathcompanion.controller;

import com.example.ultimatemathcompanion.datamodel.Expression;
import com.example.ultimatemathcompanion.service.ExpressionService;
import com.example.ultimatemathcompanion.service.TypesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PageController {

    private final ExpressionService expressionService;
    private final TypesService typesService;
    private final ExpressionController expressionController;

    public PageController(ExpressionService expressionService, TypesService typesService, ExpressionController expressionController) {
        this.expressionService = expressionService;
        this.typesService = typesService;
        this.expressionController = expressionController;
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
    public String saveExpression(@Valid @ModelAttribute("formExpression") Expression expression,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "redirect:/"; // SERVER SIDE VALIDATION. If format is not valid reset the page.

        expressionController.processExpression(expression, typesService);
        expressionService.save(expression);
        return "redirect:/";
    }

}
