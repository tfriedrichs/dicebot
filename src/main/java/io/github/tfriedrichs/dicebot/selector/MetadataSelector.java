package io.github.tfriedrichs.dicebot.selector;

import io.github.tfriedrichs.dicebot.result.DiceRoll;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MetadataSelector implements DiceSelector {

    private final Mode mode;
    private final DiceRoll.MetaData[] metaData;

    public MetadataSelector(Mode mode, DiceRoll.MetaData... metaData) {
        this.mode = mode;
        this.metaData = metaData;
    }

    @Override
    public IntStream select(DiceRoll roll) {
        IntStream indices = IntStream.range(0, roll.getRolls().length);
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


    public enum Mode {
        ALL,
        SOME,
        NONE
    }
}
