package io.github.tfriedrichs.dicebot.parser;

import io.github.tfriedrichs.dicebot.expression.DiceExpression;
import io.github.tfriedrichs.dicebot.source.FixedRandomSource;
import io.github.tfriedrichs.dicebot.source.RandomSource;
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

    @Test
    void shouldParseSimpleDiceExpressions() {
        RandomSource source = new FixedRandomSource(3);
        DiceExpression expression = DiceExpression.parse(source, "2d6 + 1");
        assertEquals(7, expression.roll().getValue());
    }

    @Test
    void shouldParseDiceExpressionWithDefaultDice() {
        RandomSource source = new FixedRandomSource(3);
        DiceExpression expression = DiceExpression.parse(source, "1d6 + 1");
        assertEquals(4, expression.roll().getValue());
    }

    @Test
    void shouldParseFateDie() {
        RandomSource source = new FixedRandomSource(0, 1, 2);
        DiceExpression expression = DiceExpression.parse(source, "4dF");
        assertEquals(-1, expression.roll().getValue());
    }

    @Test
    void shouldParsePercentageDie() {
        RandomSource source = new FixedRandomSource(20);
        DiceExpression expression = DiceExpression.parse(source, "4d%");
        assertEquals(80, expression.roll().getValue());
    }

    @Test
    void shouldParseMinFunction() {
        RandomSource source = new FixedRandomSource(20, 10);
        DiceExpression expression = DiceExpression.parse(source, "min(d%, d%)");
        assertEquals(10, expression.roll().getValue());
    }

    @Test
    void shouldParseKeepHighModifier() {
        RandomSource source = new FixedRandomSource(1, 5, 3, 4);
        DiceExpression expression = DiceExpression.parse(source, "4d6kh2");
        assertEquals(9, expression.roll().getValue());
    }

    @Test
    void shouldParseKeepLowModifier() {
        RandomSource source = new FixedRandomSource(1, 5, 3, 4);
        DiceExpression expression = DiceExpression.parse(source, "4d6kl2");
        assertEquals(4, expression.roll().getValue());
    }

    @Test
    void shouldParseMultipleDropModifiers() {
        RandomSource source = new FixedRandomSource(1, 5, 3, 4);
        DiceExpression expression = DiceExpression.parse(source, "4d6dhdl");
        assertEquals(7, expression.roll().getValue());
    }

    @Test
    void shouldParseExplodeModifiers() {
        RandomSource source = new FixedRandomSource(1, 5, 3, 4);
        DiceExpression expression = DiceExpression.parse(source, "4d6!>=5");
        assertEquals(14, expression.roll().getValue());
    }

    @Test
    void shouldParseExplodeModifiersWithArithmetic() {
        RandomSource source = new FixedRandomSource(1, 5, 3, 4);
        DiceExpression expression = DiceExpression.parse(source, "4d6!>=5 - abs(-3)");
        assertEquals(11, expression.roll().getValue());
    }

    @Test
    void shouldParseFateDice() {
        RandomSource source = new FixedRandomSource(0, 1, 2, 2);
        DiceExpression expression = DiceExpression.parse(source, "4dF");
        assertEquals(1, expression.roll().getValue());
    }

    @Test
    void shouldParsePercentileDice() {
        RandomSource source = new FixedRandomSource(70);
        DiceExpression expression = DiceExpression.parse(source, "d%");
        assertEquals(70, expression.roll().getValue());
    }

    @Test
    void shouldParseOrderedExplodeModifiers() {
        RandomSource source = new FixedRandomSource(5, 5, 6, 1);
        DiceExpression expression = DiceExpression.parse(source, "d6!>=5!c>5");
        assertEquals(22, expression.roll().getValue());
    }


}