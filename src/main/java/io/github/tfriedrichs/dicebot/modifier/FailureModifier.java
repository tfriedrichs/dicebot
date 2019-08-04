package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.selector.DiceSelector;

/**
 * {@link DiceRollModifier} that annotates each selected dice as a failure.
 */
public class FailureModifier extends DiceAnnotator {

    /**
     * Constructor.
     *
     * @param selector the selector
     */
    public FailureModifier(DiceSelector selector) {
        super(Mode.SKIP_DROPPED, DiceRoll.MetaData.FAILURE, selector);
    }
}
