package io.github.tfriedrichs.dicebot.selector;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.selector.DirectionSelector.Direction;
import org.junit.jupiter.api.Test;

class DirectionSelectorTest {

    @Test
    void shouldThrowForNegativeNumberOfDiceToSelect() {
        assertThrows(IllegalArgumentException.class,
            () -> new DirectionSelector(Direction.HIGH, -2));
    }

    @Test
    void selectShouldSelectAllIfNumberToSelectIsGreaterThanRolls() {
        DiceRoll roll = new DiceRoll(2, 5, 1, 1, 3, 4, 6);
        DirectionSelector selector = new DirectionSelector(Direction.HIGH, 12);
        assertArrayEquals(new int[]{6, 1, 5, 4, 0, 2, 3}, selector.select(roll).toArray());
    }

    @Test
    void selectHighShouldSelectHighest() {
        DiceRoll roll = new DiceRoll(2, 5, 1, 1, 3, 4, 6);
        DirectionSelector selector = new DirectionSelector(Direction.HIGH, 3);
        assertArrayEquals(new int[]{6, 1, 5}, selector.select(roll).toArray());
    }

    @Test
    void selectLowShouldSelectLowest() {
        DiceRoll roll = new DiceRoll(2, 5, 1, 1, 3, 4, 6);
        DirectionSelector selector = new DirectionSelector(Direction.LOW, 3);
        assertArrayEquals(new int[]{2, 3, 0}, selector.select(roll).toArray());
    }


}