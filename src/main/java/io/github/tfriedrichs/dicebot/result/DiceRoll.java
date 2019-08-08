package io.github.tfriedrichs.dicebot.result;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Class representing the result of one or more rolled dice.
 * <p>
 * It stores the rolled values after they have been modified by any {@link io.github.tfriedrichs.dicebot.modifier.DiceRollModifier}.
 * Each single roll can be annotated with instances of {@link MetaData},
 * which represent different operations that have been made to this roll.
 */
public class DiceRoll {

    private final int[] rolls;
    private final DieMetaData[] metaData;

    /**
     * Constructor which constructs rolls sharing a common metadata instance.
     *
     * @param metaData the shared metadata
     * @param rolls    the rolled values
     */
    public DiceRoll(MetaData metaData, int... rolls) {
        this.rolls = rolls;
        this.metaData = new DieMetaData[rolls.length];
        for (int i = 0; i < this.metaData.length; i++) {
            this.metaData[i] = new DieMetaData(metaData);
        }
    }

    /**
     * Constructor.
     *
     * @param rolls the rolled values
     */
    public DiceRoll(int... rolls) {
        this.rolls = rolls;
        this.metaData = new DieMetaData[rolls.length];
        Arrays.setAll(metaData, i -> new DieMetaData());
    }

    private DiceRoll(int[] rolls, DieMetaData[] metaData) {
        this.rolls = rolls;
        this.metaData = metaData;
    }

    /**
     * Copy constructor creating a deep copy.
     *
     * @param roll the roll to copy
     */
    public DiceRoll(DiceRoll roll) {
        this.rolls = Arrays.copyOf(roll.getRolls(), roll.getRolls().length);
        this.metaData = new DieMetaData[rolls.length];
        for (int i = 0; i < metaData.length; i++) {
            this.metaData[i] = new DieMetaData(roll.getMetaDataForRoll(i));
        }
    }

    /**
     * Concatenate two dice rolls to a single one.
     * <p>
     * This creates a new instance and does not modify the arguments.
     *
     * @param left the left result
     * @param right the right result
     * @return a concatenated result
     */
    public static DiceRoll concat(DiceRoll left, DiceRoll right) {
        int[] rolls = IntStream.concat(IntStream.of(left.rolls), IntStream.of(right.rolls))
                .toArray();
        DieMetaData[] metaData = Stream.concat(Stream.of(left.metaData), Stream.of(right.metaData))
                .toArray(DieMetaData[]::new);
        return new DiceRoll(rolls, metaData);
    }

    /**
     * Add a metadata instance to a die roll.
     *
     * @param index    the index of the die
     * @param metaData the metadata to add
     */
    public void addMetaDataToRoll(int index, MetaData metaData) {
        this.metaData[index].metaData.add(metaData);
    }

    /**
     * Count the occurrences of a metadata instance among all rolled dice.
     *
     * @param metaData the metadata to check
     * @return the number of occurrences
     */
    public int countMetadata(MetaData metaData) {
        return Math.toIntExact(
                Stream.of(this.metaData).filter(data -> data.metaData.contains(metaData)).count());
    }

    /**
     * Gets the metadata for a single die.
     *
     * @param index the index of the roll
     * @return the metadata
     */
    public Set<MetaData> getMetaDataForRoll(int index) {
        return metaData[index].metaData;
    }

    /**
     * Gets the roll values.
     *
     * @return the rolls
     */
    public int[] getRolls() {
        return this.rolls;
    }

    /**
     * Enum for the metadata
     */
    public enum MetaData {
        DROPPED,
        ADDED,
        EXPLODED,
        REROLLED,
        SUCCESS,
        CRITICAL_SUCCESS,
        CRITICAL_FAILURE,
        FAILURE,
        COMPOUNDED
    }

    private static class DieMetaData {

        private final EnumSet<MetaData> metaData;

        DieMetaData() {
            metaData = EnumSet.noneOf(MetaData.class);
        }

        public DieMetaData(MetaData metaData) {
            this.metaData = EnumSet.of(metaData);
        }

        public DieMetaData(Set<MetaData> metaData) {
            this.metaData = EnumSet.copyOf(metaData);
        }

        @Override
        public String toString() {
            return metaData.toString();
        }
    }

}
