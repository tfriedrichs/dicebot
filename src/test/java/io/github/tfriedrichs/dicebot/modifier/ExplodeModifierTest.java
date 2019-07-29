package io.github.tfriedrichs.dicebot.modifier;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.result.DiceRoll.MetaData;
import io.github.tfriedrichs.dicebot.source.Die;
import io.github.tfriedrichs.dicebot.source.FixedRandomSource;
import io.github.tfriedrichs.dicebot.source.RandomSource;
import io.github.tfriedrichs.dicebot.util.RecursionDepthException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ExplodeModifierTest {

    @Test
    void shouldReturnIdentityIfNoExplosions() {
        RandomSource randomSource = new FixedRandomSource(1, 2, 3);
        DiceRoll rolls = new DiceRoll(2, 3, 4, 4, 1, 4);
        DiceRollModifier modifier = new ExplodeModifier(5);
        DiceRoll modified = modifier.modifyRoll(rolls, new Die(randomSource, 6));
        assertArrayEquals(rolls.getRolls(), modified.getRolls());
        assertEquals(0, modified.countMetadata(MetaData.EXPLODED));
        assertEquals(0, modified.countMetadata(MetaData.ADDED));
    }

    @Test
    void shouldExplodeOnceIfNoFurtherExplosions() {
        RandomSource randomSource = new FixedRandomSource(1, 2, 3);
        DiceRoll rolls = new DiceRoll(5, 1, 6);
        DiceRollModifier modifier = new ExplodeModifier(5);
        DiceRoll modified = modifier.modifyRoll(rolls, new Die(randomSource, 6));
        assertArrayEquals(new int[]{5, 1, 6, 1, 2}, modified.getRolls());
        assertEquals(2, modified.countMetadata(MetaData.EXPLODED));
        assertEquals(2, modified.countMetadata(MetaData.ADDED));
    }

    @Test
    void shouldExplodeMultipleTimesIfFurtherExplosions() {
        RandomSource randomSource = new FixedRandomSource(5, 5, 3, 3, 3);
        DiceRoll rolls = new DiceRoll(5, 1, 6);
        DiceRollModifier modifier = new ExplodeModifier(5);
        DiceRoll modified = modifier.modifyRoll(rolls, new Die(randomSource, 6));
        assertArrayEquals(new int[]{5, 1, 6, 5, 5, 3, 3}, modified.getRolls());
        assertEquals(4, modified.countMetadata(MetaData.EXPLODED));
        assertEquals(4, modified.countMetadata(MetaData.ADDED));
    }

    @Test
    @Tag("RecursionDepth")
    void shouldThrowForInfiniteRecursion() {
        RandomSource randomSource = new FixedRandomSource(6);
        DiceRoll rolls = new DiceRoll(5, 1, 6);
        DiceRollModifier modifier = new ExplodeModifier(5);
        assertThrows(RecursionDepthException.class,
            () -> modifier.modifyRoll(rolls, new Die(randomSource, 6)));
    }

}