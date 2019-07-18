package io.github.tfriedrichs.dicebot.modifier;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.tfriedrichs.dicebot.source.FixedRandomSource;
import io.github.tfriedrichs.dicebot.source.RandomSource;
import io.github.tfriedrichs.dicebot.util.RecursionDepthException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ExplodeModifierTest {

    @Test
    void shouldReturnIdentityIfNoExplosions() {
        RandomSource randomSource = new FixedRandomSource(1, 2, 3);
        int[] rolls = new int[]{2, 3, 4, 4, 1, 4};
        DiceRollModifier modifier = new ExplodeModifier(randomSource, 5);
        int[] modified = modifier.modifyRoll(rolls, 1, 7);
        assertArrayEquals(rolls, modified);
    }

    @Test
    void shouldExplodeOnceIfNoFurtherExplosions() {
        RandomSource randomSource = new FixedRandomSource(1, 2, 3);
        int[] rolls = new int[]{5, 1, 6};
        DiceRollModifier modifier = new ExplodeModifier(randomSource, 5);
        int[] modified = modifier.modifyRoll(rolls, 1, 7);
        assertArrayEquals(new int[]{5, 1, 6, 1, 2}, modified);
    }

    @Test
    void shouldExplodeMultipleTimesIfFurtherExplosions() {
        RandomSource randomSource = new FixedRandomSource(5, 5, 3, 3, 3);
        int[] rolls = new int[]{5, 1, 6};
        DiceRollModifier modifier = new ExplodeModifier(randomSource, 5);
        int[] modified = modifier.modifyRoll(rolls, 1, 7);
        assertArrayEquals(new int[]{5, 1, 6, 5, 5, 3, 3}, modified);
    }

    @Test
    @Tag("RecursionDepth")
    void shouldThrowForInfiniteRecursion() {
        RandomSource randomSource = new FixedRandomSource(6);
        int[] rolls = new int[]{5, 1, 6};
        DiceRollModifier modifier = new ExplodeModifier(randomSource, 5);
        assertThrows(RecursionDepthException.class, () -> modifier.modifyRoll(rolls, 1, 7));
    }

}