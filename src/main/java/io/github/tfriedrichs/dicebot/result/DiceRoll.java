package io.github.tfriedrichs.dicebot.result;

import io.github.tfriedrichs.dicebot.evaluator.DiceRollEvaluator;
import java.util.Arrays;

public class DiceRoll implements DiceRollResult {

    private final DiceRollEvaluator evaluator;
    private final int[] rolls;

    public DiceRoll(DiceRollEvaluator evaluator, int... rolls) {
        this.evaluator = evaluator;
        this.rolls = rolls;
    }

    public int[] getRolls() {
        return rolls;
    }

    public int evaluate() {
        return evaluator.evaluate(rolls);
    }

    @Override
    public String toString() {
        return Arrays.toString(rolls) + " -> " + evaluator.evaluate(rolls);
    }
}
