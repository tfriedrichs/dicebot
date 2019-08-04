package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.selector.DiceSelector;

/**
 * {@link DiceRollModifier} that drops all selected dice.
 */
public class DropModifier extends DiceAnnotator {

    /**
     * Constructor.
     *
     * @param selector the selector.
     */
    public DropModifier(DiceSelector selector) {
        super(DiceRoll.MetaData.DROPPED, selector);
    }
}