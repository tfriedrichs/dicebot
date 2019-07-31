package io.github.tfriedrichs.dicebot.selector;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.selector.ComparisonSelector.Mode;
import org.junit.jupiter.api.Test;

class ComparisonSelectorTest {

    @Test
    void selectLesser() {
        DiceRoll roll = new DiceRoll(2, 5, 1, 1, 3, 4, 6);
        ComparisonSelector selector = new ComparisonSelector(Mode.LESSER, 3);
        assertArrayEquals(new int[]{0, 2, 3}, selector.select(roll).toArray());
    }

    @Test
    void selectLesserEquals() {
        DiceRoll roll = new DiceRoll(2, 5, 1, 1, 3, 4, 6);
        ComparisonSelector selector = new ComparisonSelector(Mode.LESSER_EQUALS, 3);
        assertArrayEquals(new int[]{0, 2, 3, 4}, selector.select(roll).toArray());
    }

    @Test
    void selectEquals() {
        DiceRoll roll = new DiceRoll(2, 5, 1, 1, 3, 4, 6);
        ComparisonSelector selector = new ComparisonSelector(Mode.EQUALS, 3);
        assertArrayEquals(new int[]{4}, selector.select(roll).toArray());
    }

    @Test
    void selectGreater() {
        DiceRoll roll = new DiceRoll(2, 5, 1, 1, 3, 4, 6);
        ComparisonSelector selector = new ComparisonSelector(Mode.GREATER, 3);
        assertArrayEquals(new int[]{1, 5, 6}, selector.select(roll).toArray());
    }

    @Test
    void selectGreaterEquals() {
        DiceRoll roll = new DiceRoll(2, 5, 1, 1, 3, 4, 6);
        ComparisonSelector selector = new ComparisonSelector(Mode.GREATER_EQUALS, 3);
        assertArrayEquals(new int[]{1, 4, 5, 6}, selector.select(roll).toArray());
    }


}