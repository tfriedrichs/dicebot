package io.github.tfriedrichs.dicebot.result;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DiceRoll {

    private final int[] rolls;
    private final DieMetaData[] metaData;

    public DiceRoll(MetaData metaData, int... rolls) {
        this.rolls = rolls;
        this.metaData = new DieMetaData[rolls.length];
        for (int i = 0; i < this.metaData.length; i++) {
            this.metaData[i] = new DieMetaData(metaData);
        }
    }

    public DiceRoll(int... rolls) {
        this.rolls = rolls;
        this.metaData = new DieMetaData[rolls.length];
        Arrays.setAll(metaData, i -> new DieMetaData());
    }

    private DiceRoll(int[] rolls, DieMetaData[] metaData) {
        this.rolls = rolls;
        this.metaData = metaData;
    }

    public DiceRoll(DiceRoll roll) {
        this.rolls = Arrays.copyOf(roll.getRolls(), roll.getRolls().length);
        this.metaData = new DieMetaData[rolls.length];
        for (int i = 0; i < metaData.length; i++) {
            this.metaData[i] = new DieMetaData(roll.getMetaDataForRoll(i));
        }
    }

    public static DiceRoll concat(DiceRoll left, DiceRoll right) {
        int[] rolls = IntStream.concat(IntStream.of(left.rolls), IntStream.of(right.rolls))
            .toArray();
        DieMetaData[] metaData = Stream.concat(Stream.of(left.metaData), Stream.of(right.metaData))
            .toArray(DieMetaData[]::new);
        return new DiceRoll(rolls, metaData);
    }

    public boolean addMetaDataToRoll(int index, MetaData metaData) {
        return this.metaData[index].metaData.add(metaData);
    }

    public int countMetadata(MetaData metaData) {
        return Math.toIntExact(
            Stream.of(this.metaData).filter(data -> data.metaData.contains(metaData)).count());
    }

    public EnumSet<MetaData> getMetaDataForRoll(int index) {
        return metaData[index].metaData;
    }

    public int[] getRolls() {
        return this.rolls;
    }

    public enum MetaData {
        DROPPED,
        ADDED,
        EXPLODED,
        REROLLED,
        SUCCESS,
        FAILURE,
        FUMBLE
    }

    private static class DieMetaData {

        private EnumSet<MetaData> metaData;

        DieMetaData() {
            metaData = EnumSet.noneOf(MetaData.class);
        }

        public DieMetaData(MetaData metaData) {
            this.metaData = EnumSet.of(metaData);
        }

        public DieMetaData(EnumSet<MetaData> metaData) {
            this.metaData = EnumSet.copyOf(metaData);
        }

        @Override
        public String toString() {
            return metaData.toString();
        }
    }

}
