package io.github.tfriedrichs.dicebot.evaluator;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import java.util.stream.IntStream;

public class SumEvaluator implements DiceRollEvaluator {

    @Override
    public int evaluate(DiceRoll roll) {
        return IntStream.of(roll.getRolls()).sum();
    }
}
