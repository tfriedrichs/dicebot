package io.github.tfriedrichs.dicebot.selector;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.result.DiceRoll.MetaData;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * {@link DiceSelector} that selects dice according to their metadata.
 * <p>
 * It features different modes for this comarison. It selects dice if either {@code ALL}, {@code SOME},
 * or {@code NONE} of the given metadata is present.
 */
public class MetadataSelector implements DiceSelector {

    private final DropMode dropMode;
    private final Mode mode;
    private final DiceRoll.MetaData[] metaData;

    /**
     * Constructor.
     *
     * @param mode     the selection mode
     * @param dropMode the drop strategy
     * @param metaData the metadata to compare against
     */
    public MetadataSelector(Mode mode,
        DropMode dropMode,
        MetaData... metaData) {
        this.mode = mode;
        this.dropMode = dropMode;
        this.metaData = metaData;
    }

    @Override
    public IntStream select(DiceRoll roll) {
        IntStream indices = IntStream.range(0, roll.getRolls().length);
        if (dropMode == DropMode.SKIP) {
            indices = indices
                .filter(index -> !roll.getMetaDataForRoll(index).contains(MetaData.DROPPED));
        }
        switch (mode) {
            case ALL:
                return indices.filter(index -> Stream.of(metaData).allMatch(meta -> roll.getMetaDataForRoll(index).contains(meta)));
            case SOME:
                return indices.filter(index -> Stream.of(metaData).anyMatch(meta -> roll.getMetaDataForRoll(index).contains(meta)));
            case NONE:
                return indices.filter(index -> Stream.of(metaData).noneMatch(meta -> roll.getMetaDataForRoll(index).contains(meta)));
        }
        return null;
    }

    /**
     * Enum for the selection mode.
     */
    public enum Mode {
        ALL,
        SOME,
        NONE
    }
}
