package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.source.FixedRandomSource;
import io.github.tfriedrichs.dicebot.source.RandomSource;
import io.github.tfriedrichs.dicebot.util.RecursionDepthException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompoundModifierTest {

    @Test
    void shouldReturnIdentityIfNoCompounds() {
        RandomSource randomSource = new FixedRandomSource(1, 2, 3);
        DiceRoll rolls = new DiceRoll(2, 3, 4, 4, 1, 4);
        DiceRollModifier modifier = new CompoundModifier(randomSource, 5);
        DiceRoll modified = modifier.modifyRoll(rolls, 1, 7);
        assertArrayEquals(rolls.getRolls(), modified.getRolls());
        assertEquals(0, modified.countMetadata(DiceRoll.MetaData.EXPLODED));
        assertEquals(0, modified.countMetadata(DiceRoll.MetaData.ADDED));
    }

    @Test
    void shouldCompoundOnceIfNoFurtherCompounds() {
        RandomSource randomSource = new FixedRandomSource(1, 2, 3);
        DiceRoll rolls = new DiceRoll(5, 1, 6);
        DiceRollModifier modifier = new CompoundModifier(randomSource, 5);
        DiceRoll modified = modifier.modifyRoll(rolls, 1, 7);
        assertArrayEquals(new int[]{6, 1, 8}, modified.getRolls());
        assertEquals(2, modified.countMetadata(DiceRoll.MetaData.COMPOUNDED));
    }

    @Test
    void shouldCompoundMultipleTimesIfFurtherCompounds() {
        RandomSource randomSource = new FixedRandomSource(5, 5, 3, 3, 3);
        DiceRoll rolls = new DiceRoll(5, 1, 6);
        DiceRollModifier modifier = new CompoundModifier(randomSource, 5);
        DiceRoll modified = modifier.modifyRoll(rolls, 1, 7);
        assertArrayEquals(new int[]{13, 1, 14}, modified.getRolls());
        assertEquals(2, modified.countMetadata(DiceRoll.MetaData.COMPOUNDED));
    }

    @Test
    @Tag("RecursionDepth")
    void shouldThrowForInfiniteRecursion() {
        RandomSource randomSource = new FixedRandomSource(6);
        DiceRoll rolls = new DiceRoll(5, 1, 6);
        DiceRollModifier modifier = new CompoundModifier(randomSource, 5);
        assertThrows(RecursionDepthException.class, () -> modifier.modifyRoll(rolls, 1, 7));
    }

}