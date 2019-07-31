package io.github.tfriedrichs.dicebot.selector;

import io.github.tfriedrichs.dicebot.result.DiceRoll;

import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DirectionSelector implements DiceSelector {

    private final Direction direction;
    private final int numberOfDiceToReturn;

    public DirectionSelector(
        Direction direction,
        int numberOfDiceToReturn) {
        if (numberOfDiceToReturn < 0) {
            throw new IllegalArgumentException("Number of dice to select must be positive.");
        }
        this.direction = direction;
        this.numberOfDiceToReturn = numberOfDiceToReturn;
    }

    @Override
    public IntStream select(DiceRoll roll) {
        Stream<Integer> indices = IntStream.range(0, roll.getRolls().length).boxed();
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

    public enum Direction {
        HIGH,
        LOW
    }
}
