package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll.MetaData;
import io.github.tfriedrichs.dicebot.selector.DiceSelector;

/**
 * {@link DiceRollModifier} which drops all but the selected dice.
 */
public class KeepModifier extends DiceAnnotator {

    /**
     * Constructor.
     *
     * @param selector the selector
     */
    public KeepModifier(DiceSelector selector) {
        super(Mode.SKIP_DROPPED, MetaData.DROPPED, selector.inverse());
    }
}
