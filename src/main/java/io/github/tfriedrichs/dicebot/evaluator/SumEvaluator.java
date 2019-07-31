package io.github.tfriedrichs.dicebot.evaluator;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.result.DiceRoll.MetaData;

import java.util.stream.IntStream;

public class SumEvaluator implements DiceRollEvaluator {

    @Override
    public int evaluate(DiceRoll roll) {
        return IntStream.range(0, roll.getRolls().length)
            .filter(index -> !roll.getMetaDataForRoll(index).contains(MetaData.DROPPED))
            .map(index -> roll.getRolls()[index])
            .sum();
    }
}
