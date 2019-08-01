package io.github.tfriedrichs.dicebot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.tfriedrichs.dicebot.expression.DiceExpression;
import io.github.tfriedrichs.dicebot.result.DiceResult;
import io.github.tfriedrichs.dicebot.result.DiceResultPrettyPrinter;
import io.github.tfriedrichs.dicebot.source.FixedRandomSource;
import io.github.tfriedrichs.dicebot.source.RandomSource;
import org.junit.jupiter.api.Test;

public class AcceptanceTest {

    @Test
    void pathfinderExample() {
        RandomSource source = new FixedRandomSource(5, 1, 2, 3, 6, 6);
        DiceExpression expression = DiceExpression.parse(source, "d20 + 4");
        DiceResult result = expression.roll();
        DiceResultPrettyPrinter prettyPrinter = new DiceResultPrettyPrinter();
        assertEquals("9 = 5[5] + 4", prettyPrinter.prettyPrint(result));
    }

    @Test
    void dnd5eAdvantageExample() {
        RandomSource source = new FixedRandomSource(5, 11);
        DiceExpression expression = DiceExpression.parse(source, "2d20kh + 4");
        DiceResult result = expression.roll();
        DiceResultPrettyPrinter prettyPrinter = new DiceResultPrettyPrinter();
        assertEquals("15 = 11[5, 11] + 4", prettyPrinter.prettyPrint(result));
    }

    @Test
    void dnd5eDisadvantageExample() {
        RandomSource source = new FixedRandomSource(5, 11);
        DiceExpression expression = DiceExpression.parse(source, "2d20kl + 4");
        DiceResult result = expression.roll();
        DiceResultPrettyPrinter prettyPrinter = new DiceResultPrettyPrinter();
        assertEquals("9 = 5[5, 11] + 4", prettyPrinter.prettyPrint(result));
    }

    @Test
    void shadowrunExample() {
        RandomSource source = new FixedRandomSource(5, 1, 2, 3, 6, 2, 6, 2, 1, 5);
        DiceExpression expression = DiceExpression.parse(source, "10d6e5");
        DiceResult result = expression.roll();
        DiceResultPrettyPrinter prettyPrinter = new DiceResultPrettyPrinter();
        assertEquals("4 = 4[5, 1, 2, 3, 6, 2, 6, 2, 1, 5]", prettyPrinter.prettyPrint(result));
    }

    @Test
    void shadowrunExplodingExample() {
        RandomSource source = new FixedRandomSource(5, 1, 2, 3, 6, 2, 6, 2, 1, 5);
        DiceExpression expression = DiceExpression.parse(source, "10d6!e5");
        DiceResult result = expression.roll();
        DiceResultPrettyPrinter prettyPrinter = new DiceResultPrettyPrinter();
        assertEquals("5 = 5[5, 1, 2, 3, 6, 2, 6, 2, 1, 5, 5, 1]",
            prettyPrinter.prettyPrint(result));
    }

}
