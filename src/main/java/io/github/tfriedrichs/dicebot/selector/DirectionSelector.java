package io.github.tfriedrichs.dicebot.selector;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.result.DiceRoll.MetaData;
import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * {@link DiceSelector} that selects dice by their sorted position.
 * <p>
 * Depending on the given {@link Direction} it selects either the highest or lowest valued dice.
 * This means that this selection is dependent on the value of the tested die and their corresponding surrounding roll.
 * <p>
 * This selector returns a given number of dice. If the number of dice is higher than the number of dice in the tested roll
 * then it returns all dice.
 */
public class DirectionSelector implements DiceSelector {

    private final DropMode dropMode;
    private final Direction direction;
    private final int numberOfDiceToReturn;

    /**
     * Constructor.
     *
     * @param direction            the direction
     * @param numberOfDiceToReturn the number of dice this selector returns
     * @throws IllegalArgumentException if the number of dice to keep is negative
     */
    public DirectionSelector(
        Direction direction,
        DropMode dropMode, int numberOfDiceToReturn) {
        this.dropMode = dropMode;
        if (numberOfDiceToReturn < 0) {
            throw new IllegalArgumentException("Number of dice to select must be positive.");
        }
        this.direction = direction;
        this.numberOfDiceToReturn = numberOfDiceToReturn;
    }

    @Override
    public IntStream select(DiceRoll roll) {
        Stream<Integer> indices = IntStream.range(0, roll.getRolls().length).boxed();
        if (dropMode == DropMode.SKIP) {
            indices = indices
                .filter(index -> !roll.getMetaDataForRoll(index).contains(MetaData.DROPPED));
        }
        if (direction == Direction.HIGH) {
            return indices
                .sorted(
                    Comparator.comparingInt((Integer index) -> roll.getRolls()[index]).reversed())
                .limit(numberOfDiceToReturn)
                .mapToInt(Integer::intValue);
        } else {
            return indices
                .sorted(Comparator.comparingInt(index -> roll.getRolls()[index]))
                .limit(numberOfDiceToReturn)
                .mapToInt(Integer::intValue);
        }
    }

    /**
     * Enum for the selection direction.
     */
    public enum Direction {
        HIGH,
        LOW
    }
}
