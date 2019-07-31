package io.github.tfriedrichs.dicebot.evaluator;

import io.github.tfriedrichs.dicebot.result.DiceRoll;

import java.util.EnumSet;
import java.util.stream.IntStream;

public class CountingEvaluator implements DiceRollEvaluator {

    private final DiceRoll.MetaData positive;
    private final DiceRoll.MetaData negative;

    public CountingEvaluator(DiceRoll.MetaData positive, DiceRoll.MetaData negative) {
        this.positive = positive;
        this.negative = negative;
    }

    public CountingEvaluator(DiceRoll.MetaData positive) {
        this.positive = positive;
        this.negative = null;
    }


    @Override
    public int evaluate(DiceRoll roll) {
        return IntStream.range(0, roll.getRolls().length)
                .map(index -> mapIndexToValue(index, roll))
                .sum();
    }

    private int mapIndexToValue(int index, DiceRoll roll) {
        EnumSet<DiceRoll.MetaData> data = roll.getMetaDataForRoll(index);
        int result = 0;
        if (data.contains(positive)) {
            result++;
        }
        if (negative != null) {
            if (data.contains(negative)) {
                result--;
            }
        }
        return result;
    }
}
