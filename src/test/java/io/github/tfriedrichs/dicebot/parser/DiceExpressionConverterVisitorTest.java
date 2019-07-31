package io.github.tfriedrichs.dicebot.parser;

import io.github.tfriedrichs.dicebot.expression.DiceExpression;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DiceExpressionConverterVisitorTest {

    @Test
    void shouldRespectPrecedenceRulesPre() {
        String input = "2+2*5";
        DiceExpression expression = DiceExpression.parse(input);
        assertEquals(12, expression.roll().getValue());
    }

    @Test
    void shouldRespectPrecedenceRulesPost() {
        String input = "2*5+2";
        DiceExpression expression = DiceExpression.parse(input);
        assertEquals(12, expression.roll().getValue());
    }

    @Test
    void shouldRespectPrecedenceRulesNeg() {
        String input = "-2*5+2";
        DiceExpression expression = DiceExpression.parse(input);
        assertEquals(-8, expression.roll().getValue());
    }

    @Test
    void shouldRespectPrecedenceRulesParen() {
        String input = "2*(5+2)";
        DiceExpression expression = DiceExpression.parse(input);
        assertEquals(14, expression.roll().getValue());
    }

    @Test
    void shouldRespectPrecedenceRulesFunction() {
        String input = "2*max(3+3, 2*2)";
        DiceExpression expression = DiceExpression.parse(input);
        assertEquals(12, expression.roll().getValue());
    }

}