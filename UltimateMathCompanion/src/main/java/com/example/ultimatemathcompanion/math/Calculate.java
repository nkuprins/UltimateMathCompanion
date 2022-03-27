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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// Utility class for Math calculations.
public final class Calculate {

    private final static Set<Character> MATH_SIGNS = new HashSet<>(Arrays.asList('+', '-', '*', 'x', '/', '÷'));

    private Calculate() {}

    public static long countDigits(String str) {
        return str.chars().filter(Character::isDigit).count();
    }

    public static Set<Character> getMathSigns(String str) {
        return Arrays.stream(str.split(" "))
                .filter(s -> s.length() == 1)
                .map(s -> s.charAt(0))
                .filter(MATH_SIGNS::contains)
                .collect(Collectors.toSet());
    }

    private static String[] splitExpression(String expression) {
        // Splits expression by math order.
        // Input: 2 * 3 - 1 / 3 + 2
        // Output: [ 2 * 3, - , 1 / 3 , + , 2 ]
        //
        // Input: 2 * 3 / 5 * 7
        // Output: [ 2, * , 3 , / , 5 , * , 7 ]
        Set<Character> signs = getMathSigns(expression);
        if (!signs.contains('+') && !signs.contains('-'))
            return expression.split(" ");

        return expression.split("(?<=[^/*])\\s(?=[^/*])");
    }

    private static BigDecimal solveExpression(BigDecimal num1, BigDecimal num2, String operation) {
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

    public static BigDecimal solveExpression(String expression) {
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal temp;
        String operation = "+";
        String[] expressionOrders = splitExpression(expression);

        for (String s : expressionOrders) {
            if (s.length() == 1 && MATH_SIGNS.contains(s.charAt(0))) {
                // Variable s is an operation symbol
                operation = s;
                continue;
            }

            if (s.chars().anyMatch(item -> item == ' ')) {
                // Variable s is expression(with / or * operators. For example, s='2 * 3 / 2'). 
		        // Hence, recursive case, we should split and count s='2 * 3 / 2'
                temp = solveExpression(s);
            } else {
                // Variable s is number
                temp = new BigDecimal(s);
            }

            result = solveExpression(result, temp, operation);
        }

        return result;
    }
}
