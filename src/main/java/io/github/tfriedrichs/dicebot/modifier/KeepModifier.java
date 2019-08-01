package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll.MetaData;
import io.github.tfriedrichs.dicebot.selector.DiceSelector;

public class KeepModifier extends DiceAnnotator {

    public KeepModifier(DiceSelector selector) {
        super(Mode.SKIP_DROPPED, MetaData.DROPPED, selector.inverse());
    }
}
