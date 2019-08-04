package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.selector.DiceSelector;
import io.github.tfriedrichs.dicebot.source.Die;

/**
 * {@link DiceRollModifier} that adds metadata to rolls based on a {@link DiceSelector}.
 */
public class DiceAnnotator implements DiceRollModifier {

    private final Mode mode;
    private final DiceRoll.MetaData metaData;
    private final DiceSelector selector;

    /**
     * Constructor.
     *
     * @param mode     whether to use or skip dropped rolls
     * @param metaData the metadata to add
     * @param selector the selector
     */
    public DiceAnnotator(Mode mode,
                         DiceRoll.MetaData metaData,
                         DiceSelector selector) {
        this.mode = mode;
        this.metaData = metaData;
        this.selector = selector;
    }

    @Override
    public DiceRoll modifyRoll(DiceRoll roll, Die die) {
        DiceRoll result = new DiceRoll(roll);
        if (mode == Mode.USE_DROPPED) {
            selector.select(roll).forEach(index -> result.addMetaDataToRoll(index, metaData));
        } else if (mode == Mode.SKIP_DROPPED) {
            selector.select(roll)
                    .filter(index -> !result.getMetaDataForRoll(index).contains(DiceRoll.MetaData.DROPPED))
                    .forEach(index -> result.addMetaDataToRoll(index, metaData));
        }
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
