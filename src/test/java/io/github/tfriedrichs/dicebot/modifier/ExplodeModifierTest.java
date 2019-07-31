package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.result.DiceRoll.MetaData;
import io.github.tfriedrichs.dicebot.selector.ComparisonSelector;
import io.github.tfriedrichs.dicebot.source.Die;
import io.github.tfriedrichs.dicebot.source.FixedRandomSource;
import io.github.tfriedrichs.dicebot.source.RandomSource;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ExplodeModifierTest {

    private static final int MAX_DEPTH = 100;

    @Test
    void shouldReturnIdentityIfNoExplosions() {
        RandomSource randomSource = new FixedRandomSource(1, 2, 3);
        DiceRoll rolls = new DiceRoll(2, 3, 4, 4, 1, 4);
        DiceRollModifier modifier = new ExplodeModifier(MAX_DEPTH, new ComparisonSelector(ComparisonSelector.Mode.GREATER_EQUALS, 5));
        DiceRoll modified = modifier.modifyRoll(rolls, new Die(randomSource, 6));
        assertArrayEquals(rolls.getRolls(), modified.getRolls());
        assertEquals(0, modified.countMetadata(MetaData.EXPLODED));
        assertEquals(0, modified.countMetadata(MetaData.ADDED));
    }

    @Test
    void shouldExplodeOnceIfNoFurtherExplosions() {
        RandomSource randomSource = new FixedRandomSource(1, 2, 3);
        DiceRoll rolls = new DiceRoll(5, 1, 6);
        DiceRollModifier modifier = new ExplodeModifier(MAX_DEPTH, new ComparisonSelector(ComparisonSelector.Mode.GREATER_EQUALS, 5));
        DiceRoll modified = modifier.modifyRoll(rolls, new Die(randomSource, 6));
        assertArrayEquals(new int[]{5, 1, 6, 1, 2}, modified.getRolls());
        assertEquals(2, modified.countMetadata(MetaData.EXPLODED));
        assertEquals(2, modified.countMetadata(MetaData.ADDED));
    }

    @Test
    void shouldExplodeMultipleTimesIfFurtherExplosions() {
        RandomSource randomSource = new FixedRandomSource(5, 5, 3, 3, 3);
        DiceRoll rolls = new DiceRoll(5, 1, 6);
        DiceRollModifier modifier = new ExplodeModifier(MAX_DEPTH, new ComparisonSelector(ComparisonSelector.Mode.GREATER_EQUALS, 5));
        DiceRoll modified = modifier.modifyRoll(rolls, new Die(randomSource, 6));
        assertArrayEquals(new int[]{5, 1, 6, 5, 5, 3, 3}, modified.getRolls());
        assertEquals(4, modified.countMetadata(MetaData.EXPLODED));
        assertEquals(4, modified.countMetadata(MetaData.ADDED));
    }

    @Test
    void shouldExplodeUntilMaxDepth() {
        RandomSource randomSource = new FixedRandomSource(1);
        DiceRoll rolls = new DiceRoll(1);
        DiceRollModifier modifier = new ExplodeModifier(100, new ComparisonSelector(ComparisonSelector.Mode.EQUALS, 1));
        assertArrayEquals(IntStream.generate(() -> 1).limit(101).toArray(), modifier.modifyRoll(rolls, new Die(randomSource, 1)).getRolls());
    }

}