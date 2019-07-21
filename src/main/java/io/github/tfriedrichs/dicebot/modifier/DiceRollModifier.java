package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;

public interface DiceRollModifier {

    DiceRoll modifyRoll(DiceRoll roll, int min, int max);

}
