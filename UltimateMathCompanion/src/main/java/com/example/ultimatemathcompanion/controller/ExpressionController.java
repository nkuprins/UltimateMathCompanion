package com.example.ultimatemathcompanion.controller;

import com.example.ultimatemathcompanion.datamodel.Expression;
import com.example.ultimatemathcompanion.datamodel.Types;
import com.example.ultimatemathcompanion.math.Calculate;
import com.example.ultimatemathcompanion.service.TypesService;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionController {

    // Valid format is: 2 / 3 + 1 * 4
    // No redundant spaces or unclear symbols.
    // All numbers are only integers.
    private final static Pattern EXPRESSION_FORMAT = Pattern.compile("^-?\\d+( [+\\-*/] -?\\d+)+$");

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

    public boolean isValidFormat(String expression) {

        Matcher matcher = EXPRESSION_FORMAT.matcher(expression);
        return matcher.matches();
    }
}
