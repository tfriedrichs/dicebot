package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.result.DiceRoll.MetaData;
import io.github.tfriedrichs.dicebot.source.Die;
import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class KeepModifier implements DiceRollModifier {

    private final Direction direction;
    private final int numberOfDiceToKeep;

    public KeepModifier(Direction direction,
        int numberOfDiceToKeep) {
        if (numberOfDiceToKeep < 0) {
            throw new IllegalArgumentException("Number of dice to keep must be positive.");
        }
        this.direction = direction;
        this.numberOfDiceToKeep = numberOfDiceToKeep;
    }

    @Override
    public DiceRoll modifyRoll(DiceRoll roll, Die die) {
        DiceRoll result = new DiceRoll(roll);
        Stream<Integer> indices = IntStream.range(0, result.getRolls().length).boxed();
        if (direction == Direction.HIGH) {
            indices.sorted(
                Comparator.comparing((Integer index) -> result.getRolls()[index]).reversed())
                .skip(numberOfDiceToKeep)
                .forEach(index -> result.addMetaDataToRoll(index, MetaData.DROPPED));
        } else {
            indices.sorted(Comparator.comparing(index -> result.getRolls()[index]))
                .skip(numberOfDiceToKeep)
                .forEach(index -> result.addMetaDataToRoll(index, MetaData.DROPPED));
        }

        return result;
    }

    public enum Direction {
        HIGH,
        LOW
    }

}
