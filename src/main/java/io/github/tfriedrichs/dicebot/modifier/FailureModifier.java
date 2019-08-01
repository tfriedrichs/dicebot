package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.selector.DiceSelector;

public class FailureModifier extends DiceAnnotator {
    public FailureModifier(DiceSelector selector) {
        super(DiceRoll.MetaData.FAILURE, selector);
    }
}
