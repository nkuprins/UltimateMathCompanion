package com.example.ultimatemathcompanion;

import com.example.ultimatemathcompanion.controller.ExpressionController;
import com.example.ultimatemathcompanion.math.Calculate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class UltimateMathCompanionApplicationTests {

    private final ExpressionController expressionController = new ExpressionController();

    @ParameterizedTest
    @ValueSource(strings = {
            "272 + 250", "430 + 876", "-900 + 800", "200 - 25 * 100 / 25"
    })
    public void correctFormatTrue(String expression) {
        Assertions.assertTrue(expressionController.isValidFormat(expression));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Hello272A + 250", "430 + 87GD6 = 1306", "-900 + 800 = -10MIW0",
            "272+ 250", "430+876 1306", "900 + 800 =-100",
            "+ 250 522", "430+ 1306", "900 + 800D",
            "272+ 250  522", "430876 1306"
    })
    public void correctFormatFalse(String expression) {
        Assertions.assertFalse(expressionController.isValidFormat(expression));
    }


    @ParameterizedTest
    @CsvSource(value = {
            "80 / -2 / 2=-20.0000",
            "-81 / -52 - 34 - 3 - 46 * 13 / 67 - 8 - 20 / 42=-52.8439",
            "60 - 86 / 79 - 74 * 5 / 77 * 55 * 95 * 94 + 39=-2359976.0686",
            "47 * 17 - 99 * 20 * 31 - 86 / 89 - 5 + 16 + 94=-60476.9663",
            "2 + 2 / 7=2.2857",
            "2 / 7 + 2=2.2857",
            "2 + 4 - 5 + 10=11",
            "2 + 2=4"
    }, delimiter = '=')
    public void calculateWithString(String expressions, BigDecimal result) {
        Assertions.assertEquals(result, Calculate.solveExpression(expressions));
    }
}
