package io.github.tfriedrichs.dicebot.evaluator;

import io.github.tfriedrichs.dicebot.result.DiceRoll;

import java.util.EnumSet;
import java.util.stream.IntStream;

/**
 * {@link DiceRollEvaluator} which calculates its result
 * by counting the occurrence of {@link io.github.tfriedrichs.dicebot.result.DiceRoll.MetaData}
 * in the roll.
 * <p>
 * It can also be given a positive and a negative metadata instance so that the number of occurrences
 * of the negative metadata is subtracted from the end result.
 */
public class CountingEvaluator implements DiceRollEvaluator {

    private final DiceRoll.MetaData positive;
    private final DiceRoll.MetaData negative;

    /**
     * Constructor.
     *
     * @param positive metadata which occurrence is worth 1
     * @param negative metadata which occurrence is worth -1
     */
    public CountingEvaluator(DiceRoll.MetaData positive, DiceRoll.MetaData negative) {
        this.positive = positive;
        this.negative = negative;
    }

    /**
     * Constructor.
     *
     * @param positive metadata which occurrence is worth 1
     */
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
