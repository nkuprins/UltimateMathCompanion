/*
 * Copyright 2021 Nikita Kuprins
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.ultimatemathcompanion.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Calculations {

    // Valid format is: 2 / 3 + 1 * 4
    // No redundant spaces or unclear symbols.
    // All numbers are only integers.
    private static final Pattern validFormat = Pattern.compile("^-?\\d+( [+\\-*/] -?\\d+)+$");
    private static final List<Character> mathSigns = Arrays.asList('+', '-', '*', 'x', '/', '÷');

    private enum Types {
        SumSubtract(1),             // +-
        SumSubtrDivMultipl(2),      // */+-
        DivisionMultipl(3),         // */
        LongExpression(4);          // */+-*/+-

        private final int id;

        Types(int id) {
            this.id = id;
        }
    }

    private Calculations() {}

    private static long countDigits(String str) {
        return str.chars().filter(Character::isDigit).count();
    }

    private static Set<Character> getExpressionSigns(String str) {
        return Arrays.stream(str.split(" "))
                .filter(s -> s.length() == 1)
                .map(s -> s.charAt(0))
                .filter(mathSigns::contains)
                .collect(Collectors.toSet());
    }

    public static int getExpressionTypeId(String str) {
        if (countDigits(str) >= 30)
            return Types.LongExpression.id;

        Set<Character> signs = getExpressionSigns(str);

        if (!signs.contains('/') && !signs.contains('*'))
            return Types.SumSubtract.id;

        if (!signs.contains('+') && !signs.contains('-'))
            return Types.DivisionMultipl.id;

        return Types.SumSubtrDivMultipl.id;
    }

    public static boolean isValidFormat(String str) {
        Matcher matcher = validFormat.matcher(str);
        return matcher.matches();
    }

    private static String[] splitByOperationsOrder(String expression) {
        // Splits expression by math order.
        // Input: 2 * 3 - 1 / 3 + 2
        // Output: [ 2 * 3, - , 1 / 3 , + , 2 ]
        //
        // Input: 2 * 3 / 5 * 7
        // Output: [ 2, * , 3 , / , 5 , * , 7 ]
        Set<Character> signs = getExpressionSigns(expression);
        if (!signs.contains('+') && !signs.contains('-'))
            return expression.split(" ");

        return expression.split("(?<=[^/*])\\s(?=[^/*])");
    }

    private static BigDecimal calculate(BigDecimal num1, BigDecimal num2, String operation) {
        switch (operation) {
            case "+":
                return num1.add(num2);
            case "-":
                return num1.subtract(num2);
            case "x": case "*":
                return num1.multiply(num2);
            case "÷": case "/":
                return num1.divide(num2, 4, RoundingMode.HALF_UP);
            default:
                return BigDecimal.ZERO;
        }
    }

    public static BigDecimal calculate(String expression) {
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal temp;
        String operation = "+";
        String[] expressionOrders = splitByOperationsOrder(expression);

        for (String s : expressionOrders) {
            if (s.length() == 1 && mathSigns.contains(s.charAt(0))) {
                // Variable s is an operation symbol
                operation = s;
                continue;
            }

            if (s.chars().anyMatch(item -> item == ' ')) {
                // Variable s is expression(with / or * operators. For example, s='2 * 3 / 2'). 
		        // Hence, recursive case, we should split and count s='2 * 3 / 2'
                temp = calculate(s);
            } else {
                // Variable s is number
                temp = new BigDecimal(s);
            }

            result = calculate(result, temp, operation);
        }

        return result;
    }
}
