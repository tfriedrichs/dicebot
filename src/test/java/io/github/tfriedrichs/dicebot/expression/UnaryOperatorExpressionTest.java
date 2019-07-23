package io.github.tfriedrichs.dicebot.expression;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.tfriedrichs.dicebot.operator.UnaryOperator;
import org.junit.jupiter.api.Test;

class UnaryOperatorExpressionTest {

    @Test
    void minusShouldInvert() {
        DiceExpression inner = new NumberExpression(3);
        DiceExpression result = new UnaryOperatorExpression(UnaryOperator.MINUS, inner);
        assertEquals(-3, result.roll().getValue());
    }

}