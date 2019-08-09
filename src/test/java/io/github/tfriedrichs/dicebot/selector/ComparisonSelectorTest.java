package io.github.tfriedrichs.dicebot.selector;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.selector.ComparisonSelector.Mode;
import io.github.tfriedrichs.dicebot.selector.DiceSelector.DropMode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ComparisonSelectorTest {

    @Test
    void selectLesser() {
        DiceRoll roll = new DiceRoll(2, 5, 1, 1, 3, 4, 6);
        ComparisonSelector selector = new ComparisonSelector(Mode.LESSER, DropMode.SKIP, 3);
        assertArrayEquals(new int[]{0, 2, 3}, selector.select(roll).toArray());
    }

    @Test
    void selectLesserEquals() {
        DiceRoll roll = new DiceRoll(2, 5, 1, 1, 3, 4, 6);
        ComparisonSelector selector = new ComparisonSelector(Mode.LESSER_EQUALS, DropMode.SKIP, 3);
        assertArrayEquals(new int[]{0, 2, 3, 4}, selector.select(roll).toArray());
    }

    @Test
    void selectEquals() {
        DiceRoll roll = new DiceRoll(2, 5, 1, 1, 3, 4, 6);
        ComparisonSelector selector = new ComparisonSelector(Mode.EQUALS, DropMode.SKIP, 3);
        assertArrayEquals(new int[]{4}, selector.select(roll).toArray());
    }

    @Test
    void selectGreater() {
        DiceRoll roll = new DiceRoll(2, 5, 1, 1, 3, 4, 6);
        ComparisonSelector selector = new ComparisonSelector(Mode.GREATER, DropMode.SKIP, 3);
        assertArrayEquals(new int[]{1, 5, 6}, selector.select(roll).toArray());
    }

    @Test
    void selectGreaterEquals() {
        DiceRoll roll = new DiceRoll(2, 5, 1, 1, 3, 4, 6);
        ComparisonSelector selector = new ComparisonSelector(Mode.GREATER_EQUALS, DropMode.SKIP, 3);
        assertArrayEquals(new int[]{1, 4, 5, 6}, selector.select(roll).toArray());
    }

    @Test
    void isInvertible() {
        DiceRoll roll = new DiceRoll(2, 5, 1, 1, 3, 4, 6);
        DiceSelector selector = new ComparisonSelector(Mode.GREATER_EQUALS, DropMode.SKIP, 3)
            .inverse();
        assertArrayEquals(new int[]{0, 2, 3}, selector.select(roll).toArray());
    }


}