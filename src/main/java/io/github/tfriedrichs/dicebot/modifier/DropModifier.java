package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.selector.DiceSelector;

public class DropModifier extends DiceAnnotator {

    public DropModifier(DiceSelector selector) {
        super(Mode.SKIP_DROPPED, DiceRoll.MetaData.DROPPED, selector);
    }
}
