package com.example.ultimatemathcompanion.controller;

import com.example.ultimatemathcompanion.datamodel.Expression;
import com.example.ultimatemathcompanion.datamodel.Types;
import com.example.ultimatemathcompanion.math.Calculate;
import com.example.ultimatemathcompanion.service.TypesService;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class ExpressionController {

    private int getExpressionTypeId(String expression) {

        if (Calculate.countDigits(expression) >= 30)
            return Types.Kinds.LongExpression.getId();

        Set<Character> signs = Calculate.getMathSigns(expression);

        if (!signs.contains('/') && !signs.contains('*'))
            return Types.Kinds.SumSubtract.getId();

        if (!signs.contains('+') && !signs.contains('-'))
            return Types.Kinds.DivisionMultipl.getId();

        return Types.Kinds.SumSubtrDivMultipl.getId();
    }

    public Expression processExpression(String exprId, String expr, TypesService typesService) {

        BigDecimal answer = Calculate.solveExpression(expr);
        Types exprType = typesService.findById(getExpressionTypeId(expr));
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDateTime.now().format(dateFormatter);

        if (exprId.equals(""))
            return new Expression(expr, answer, date, exprType);
        else
            return new Expression(Integer.parseInt(exprId), expr, answer, date, exprType);
    }
}
