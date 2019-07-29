package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.source.Die;
import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DropModifier implements DiceRollModifier {

    private final Direction direction;
    private final int numberOfDiceToDrop;

    public DropModifier(Direction direction,
                        int numberOfDiceToDrop) {
        if (numberOfDiceToDrop < 0) {
            throw new IllegalArgumentException("Number of dice to drop must be positive.");
        }
        this.direction = direction;
        this.numberOfDiceToDrop = numberOfDiceToDrop;
    }

    @Override
    public DiceRoll modifyRoll(DiceRoll roll, Die die) {
        DiceRoll result = new DiceRoll(roll);
        Stream<Integer> indices = IntStream.range(0, result.getRolls().length).boxed();
        if (direction == Direction.HIGH) {
            indices.sorted(
                    Comparator.comparing((Integer index) -> result.getRolls()[index]).reversed())
                    .limit(numberOfDiceToDrop)
                    .forEach(index -> result.addMetaDataToRoll(index, DiceRoll.MetaData.DROPPED));
        } else {
            indices.sorted(Comparator.comparing(index -> result.getRolls()[index]))
                    .limit(numberOfDiceToDrop)
                    .forEach(index -> result.addMetaDataToRoll(index, DiceRoll.MetaData.DROPPED));
        }

        return result;
    }

    public enum Direction {
        HIGH,
        LOW
    }
}
