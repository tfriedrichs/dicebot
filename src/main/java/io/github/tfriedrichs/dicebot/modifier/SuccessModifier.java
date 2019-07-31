package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.selector.DiceSelector;

public class SuccessModifier extends DiceAnnotator {

    public SuccessModifier(DiceSelector selector) {
        super(Mode.SKIP_DROPPED, DiceRoll.MetaData.SUCCESS, selector);
    }

}
