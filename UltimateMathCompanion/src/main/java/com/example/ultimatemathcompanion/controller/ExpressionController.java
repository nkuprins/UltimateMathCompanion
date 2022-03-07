package com.example.ultimatemathcompanion.controller;

import com.example.ultimatemathcompanion.datamodel.Expression;
import com.example.ultimatemathcompanion.math.Calculate;
import com.example.ultimatemathcompanion.service.TypesService;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
    private final Pattern validFormat = Pattern.compile("^-?\\d+( [+\\-*/] -?\\d+)+$");

    @AllArgsConstructor
    private enum ExprTypes {
        SumSubtract(1),             // +-
        SumSubtrDivMultipl(2),      // */+-
        DivisionMultipl(3),         // */
        LongExpression(4);          // */+-*/+-

        @Getter private final int id;
    }

    private int getExpressionTypeId(String expression) {

        if (Calculate.countDigits(expression) >= 30)
            return ExprTypes.LongExpression.id;

        Set<Character> signs = Calculate.getMathSigns(expression);

        if (!signs.contains('/') && !signs.contains('*'))
            return ExprTypes.SumSubtract.id;

        if (!signs.contains('+') && !signs.contains('-'))
            return ExprTypes.DivisionMultipl.id;

        return ExprTypes.SumSubtrDivMultipl.id;
    }

    public void processExpression(Expression expression, TypesService typesService) {

        String theExpression = expression.getExpression();
        expression.setAnswer(Calculate.solve(theExpression));

        int typeId = getExpressionTypeId(theExpression);
        expression.setTypes(typesService.findById(typeId));

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        expression.setDate(Date.valueOf(LocalDateTime.now().format(dateTimeFormatter)));
    }

    public boolean isValidFormat(String expression) {

        Matcher matcher = validFormat.matcher(expression);
        return matcher.matches();
    }
}
