package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.source.Die;

public interface DiceRollModifier {

    DiceRoll modifyRoll(DiceRoll roll, Die die);

}
