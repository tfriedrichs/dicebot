package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.selector.ComparisonSelector;
import io.github.tfriedrichs.dicebot.selector.DiceSelector.DropMode;
import io.github.tfriedrichs.dicebot.source.Die;
import io.github.tfriedrichs.dicebot.source.FixedRandomSource;
import io.github.tfriedrichs.dicebot.source.RandomSource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CompoundModifierTest {

    private static final int MAX_DEPTH = 100;

    @Test
    void shouldReturnIdentityIfNoCompounds() {
        RandomSource randomSource = new FixedRandomSource(1, 2, 3);
        DiceRoll rolls = new DiceRoll(2, 3, 4, 4, 1, 4);
        DiceRollModifier modifier = new CompoundModifier(MAX_DEPTH,
            new ComparisonSelector(ComparisonSelector.Mode.GREATER_EQUALS,
                DropMode.SKIP, 5
            ));
        DiceRoll modified = modifier.modifyRoll(rolls, new Die(randomSource, 6));
        assertArrayEquals(rolls.getRolls(), modified.getRolls());
        assertEquals(0, modified.countMetadata(DiceRoll.MetaData.EXPLODED));
        assertEquals(0, modified.countMetadata(DiceRoll.MetaData.ADDED));
    }

    @Test
    void shouldCompoundOnceIfNoFurtherCompounds() {
        RandomSource randomSource = new FixedRandomSource(1, 2, 3);
        DiceRoll rolls = new DiceRoll(5, 1, 6);
        DiceRollModifier modifier = new CompoundModifier(MAX_DEPTH,
            new ComparisonSelector(ComparisonSelector.Mode.GREATER_EQUALS,
                DropMode.SKIP, 5
            ));
        DiceRoll modified = modifier.modifyRoll(rolls, new Die(randomSource, 6));
        assertArrayEquals(new int[]{6, 1, 8}, modified.getRolls());
        assertEquals(2, modified.countMetadata(DiceRoll.MetaData.COMPOUNDED));
    }

    @Test
    void shouldCompoundMultipleTimesIfFurtherCompounds() {
        RandomSource randomSource = new FixedRandomSource(5, 5, 3, 3, 3);
        DiceRoll rolls = new DiceRoll(5, 1, 6);
        DiceRollModifier modifier = new CompoundModifier(MAX_DEPTH,
            new ComparisonSelector(ComparisonSelector.Mode.GREATER_EQUALS,
                DropMode.SKIP, 5
            ));
        DiceRoll modified = modifier.modifyRoll(rolls, new Die(randomSource, 6));
        assertArrayEquals(new int[]{13, 1, 14}, modified.getRolls());
        assertEquals(2, modified.countMetadata(DiceRoll.MetaData.COMPOUNDED));
    }

    @Test
    void shouldCompoundUntilMaxDepth() {
        RandomSource randomSource = new FixedRandomSource(1);
        DiceRoll rolls = new DiceRoll(1);
        DiceRollModifier modifier = new CompoundModifier(100,
            new ComparisonSelector(ComparisonSelector.Mode.EQUALS,
                DropMode.SKIP, 1
            ));
        assertArrayEquals(new int[]{101}, modifier.modifyRoll(rolls, new Die(randomSource, 1)).getRolls());
    }

}