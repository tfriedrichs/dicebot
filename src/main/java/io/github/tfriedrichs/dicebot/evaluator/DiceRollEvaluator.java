package io.github.tfriedrichs.dicebot.evaluator;

import io.github.tfriedrichs.dicebot.result.DiceRoll;

public interface DiceRollEvaluator {

    int evaluate(DiceRoll roll);

}
