package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.selector.DiceSelector;
import io.github.tfriedrichs.dicebot.source.Die;

/**
 * {@link DiceRollModifier} that adds metadata to rolls based on a {@link DiceSelector}.
 */
public class DiceAnnotator implements DiceRollModifier {

    private final DiceRoll.MetaData metaData;
    private final DiceSelector selector;

    /**
     * Constructor.
     *
     * @param metaData the metadata to add
     * @param selector the selector
     */
    public DiceAnnotator(DiceRoll.MetaData metaData,
                         DiceSelector selector) {
        this.metaData = metaData;
        this.selector = selector;
    }

    @Override
    public DiceRoll modifyRoll(DiceRoll roll, Die die) {
        DiceRoll result = new DiceRoll(roll);
        selector.select(roll).forEach(index -> result.addMetaDataToRoll(index, metaData));
        return result;
    }

    /**
     * Enum for strategies for handling dropped dice.
     */
    public enum Mode {
        USE_DROPPED,
        SKIP_DROPPED
    }
}
