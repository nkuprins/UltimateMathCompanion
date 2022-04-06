package com.example.ultimatemathcompanion.controller;

import com.example.ultimatemathcompanion.datamodel.Expression;
import com.example.ultimatemathcompanion.service.ExpressionService;
import com.example.ultimatemathcompanion.service.TypesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Pattern;
import java.util.List;

/*
@Validated: - Valid format is: 2 / 3 + 1 * 4
            - No redundant spaces or unclear symbols.
            - All numbers are only integers.
 */

@Controller
@Validated
public class PageController {

    private final ExpressionService expressionService;
    private final TypesService typesService;
    private final ExpressionController expressionController;

    public PageController(ExpressionService expressionService, TypesService typesService,
                          ExpressionController expressionController) {
        this.expressionService = expressionService;
        this.typesService = typesService;
        this.expressionController = expressionController;
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/")
    public String showPage(@RequestParam(required = false) String id, Model model) {

        List<Expression> expressions = expressionService.findAll();
        String expr = id == null ? "" : expressionService.findById(Integer.parseInt(id)).getExpression();

        model.addAttribute("listOfExpressions", expressions);
        model.addAttribute("exprId", id);
        model.addAttribute("expr", expr);

        return "index";
    }

    @GetMapping("/deleteExpression")
    public String deleteExpression(@RequestParam int id) {

        expressionService.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/save")
    public String saveExpression(@RequestParam String exprId,
                                 @Pattern(regexp = "^-?\\d+( [+\\-*/] -?\\d+)+$")
                                 @RequestParam String expr) {
        Expression expression = expressionController.processExpression(exprId, expr, typesService);
        expressionService.save(expression);

        return "redirect:/";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    private String handleConstraintViolationException() {
        return "redirect:/"; // SERVER SIDE VALIDATION. If format is not valid reset the page.
    }

}
