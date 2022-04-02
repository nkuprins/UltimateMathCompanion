package com.example.ultimatemathcompanion.controller;

import com.example.ultimatemathcompanion.datamodel.Expression;
import com.example.ultimatemathcompanion.datamodel.Types;
import com.example.ultimatemathcompanion.math.Calculate;
import com.example.ultimatemathcompanion.service.TypesService;

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

    public void processExpression(Expression expression, TypesService typesService) {

        String theExpression = expression.getExpression();
        expression.setAnswer(Calculate.solveExpression(theExpression));

        int typeId = getExpressionTypeId(theExpression);
        expression.setTypes(typesService.findById(typeId));

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        expression.setDate(Date.valueOf(LocalDateTime.now().format(dateTimeFormatter)));
    }
}
