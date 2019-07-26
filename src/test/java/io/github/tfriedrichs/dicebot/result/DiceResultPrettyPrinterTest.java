package io.github.tfriedrichs.dicebot.result;

import io.github.tfriedrichs.dicebot.operator.BinaryOperator;
import io.github.tfriedrichs.dicebot.operator.RoundingStrategy;
import io.github.tfriedrichs.dicebot.operator.UnaryOperator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiceResultPrettyPrinterTest {

    @Test
    void shouldPrettyPrintSimpleExpressions() {
        DiceResult result = new BinaryOperatorResult(RoundingStrategy.DOWN, BinaryOperator.PLUS, new DiceRollResult(14, new DiceRoll(3, 1, 3, 3, 4)), new NumberResult(12));
        DiceResultPrettyPrinter printer = new DiceResultPrettyPrinter();
        assertEquals("26 = 14[3, 1, 3, 3, 4] + 12", printer.prettyPrint(result));
    }

    @Test
    void shouldWrapExpressionsAccordingToTheirPrecedence() {
        DiceResult result = new BinaryOperatorResult(RoundingStrategy.DOWN, BinaryOperator.PLUS, new NumberResult(14), new BinaryOperatorResult(RoundingStrategy.DOWN, BinaryOperator.TIMES, new BinaryOperatorResult(RoundingStrategy.DOWN, BinaryOperator.MINUS, new NumberResult(2), new UnaryOperatorResult(UnaryOperator.MINUS, new NumberResult(2))), new NumberResult(3)));
        DiceResultPrettyPrinter printer = new DiceResultPrettyPrinter();
        assertEquals("26 = 14 + (2 - -2) * 3", printer.prettyPrint(result));
    }

    @Test
    void shouldPrintPrefixOperators() {
        DiceResult result = new BinaryOperatorResult(RoundingStrategy.DOWN, BinaryOperator.PLUS, new NumberResult(14), new BinaryOperatorResult(RoundingStrategy.DOWN, BinaryOperator.MAX, new BinaryOperatorResult(RoundingStrategy.DOWN, BinaryOperator.MINUS, new NumberResult(3), new UnaryOperatorResult(UnaryOperator.MINUS, new NumberResult(2))), new NumberResult(2)));
        DiceResultPrettyPrinter printer = new DiceResultPrettyPrinter();
        assertEquals("19 = 14 + max(3 - -2, 2)", printer.prettyPrint(result));
    }

    @Test
    void shouldPrintUnaryOperatorsWithCorrectPrecedence() {
        DiceResult result = new UnaryOperatorResult(UnaryOperator.MINUS, new BinaryOperatorResult(RoundingStrategy.DOWN, BinaryOperator.PLUS, new NumberResult(3), new NumberResult(4)));
        DiceResultPrettyPrinter printer = new DiceResultPrettyPrinter();
        assertEquals("-7 = -(3 + 4)", printer.prettyPrint(result));
    }

}