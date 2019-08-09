package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.selector.ComparisonSelector;
import io.github.tfriedrichs.dicebot.selector.DiceSelector.DropMode;
import io.github.tfriedrichs.dicebot.source.Die;
import io.github.tfriedrichs.dicebot.source.FixedRandomSource;
import io.github.tfriedrichs.dicebot.source.RandomSource;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PenetrateModifierTest {

    private static final int MAX_DEPTH = 100;

    @Test
    void shouldReturnIdentityIfNoPenetrations() {
        RandomSource randomSource = new FixedRandomSource(1, 2, 3);
        DiceRoll rolls = new DiceRoll(2, 3, 4, 4, 1, 4);
        DiceRollModifier modifier = new PenetrateModifier(MAX_DEPTH,
            new ComparisonSelector(ComparisonSelector.Mode.GREATER_EQUALS,
                DropMode.SKIP, 5
            ));
        DiceRoll modified = modifier.modifyRoll(rolls, new Die(randomSource, 6));
        assertArrayEquals(rolls.getRolls(), modified.getRolls());
        assertEquals(0, modified.countMetadata(DiceRoll.MetaData.EXPLODED));
        assertEquals(0, modified.countMetadata(DiceRoll.MetaData.ADDED));
    }

    @Test
    void shouldPenetrateOnceIfNoFurtherPenetrations() {
        RandomSource randomSource = new FixedRandomSource(1, 2, 3);
        DiceRoll rolls = new DiceRoll(5, 1, 6);
        DiceRollModifier modifier = new PenetrateModifier(MAX_DEPTH,
            new ComparisonSelector(ComparisonSelector.Mode.GREATER_EQUALS,
                DropMode.SKIP, 5
            ));
        DiceRoll modified = modifier.modifyRoll(rolls, new Die(randomSource, 6));
        assertArrayEquals(new int[]{5, 1, 6, 0, 1}, modified.getRolls());
        assertEquals(2, modified.countMetadata(DiceRoll.MetaData.EXPLODED));
        assertEquals(2, modified.countMetadata(DiceRoll.MetaData.ADDED));
    }

    @Test
    void shouldPenetrateMultipleTimesIfFurtherPenetrations() {
        RandomSource randomSource = new FixedRandomSource(6, 5, 3, 3, 3);
        DiceRoll rolls = new DiceRoll(5, 1, 6);
        DiceRollModifier modifier = new PenetrateModifier(MAX_DEPTH,
            new ComparisonSelector(ComparisonSelector.Mode.GREATER_EQUALS,
                DropMode.SKIP, 5
            ));
        DiceRoll modified = modifier.modifyRoll(rolls, new Die(randomSource, 6));
        assertArrayEquals(new int[]{5, 1, 6, 5, 4, 2}, modified.getRolls());
        assertEquals(3, modified.countMetadata(DiceRoll.MetaData.EXPLODED));
        assertEquals(3, modified.countMetadata(DiceRoll.MetaData.ADDED));
    }

    @Test
    void shouldPenetrateUntilMaxDepth() {
        RandomSource randomSource = new FixedRandomSource(1);
        DiceRoll rolls = new DiceRoll(1);
        DiceRollModifier modifier = new PenetrateModifier(100,
            new ComparisonSelector(ComparisonSelector.Mode.LESSER_EQUALS,
                DropMode.SKIP, 1
            ));
        assertArrayEquals(IntStream.concat(IntStream.of(1), IntStream.generate(() -> 0)).limit(101).toArray(), modifier.modifyRoll(rolls, new Die(randomSource, 1)).getRolls());
    }

}