package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.selector.DiceSelector;

/**
 * {@link DiceRollModifier} that annotates each selected dice as a success.
 */
public class SuccessModifier extends DiceAnnotator {

    /**
     * Constructor.
     *
     * @param selector the selector
     */
    public SuccessModifier(DiceSelector selector) {
        super(DiceRoll.MetaData.SUCCESS, selector);
    }

}
