package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.result.DiceRoll.MetaData;
import io.github.tfriedrichs.dicebot.selector.DirectionSelector;
import io.github.tfriedrichs.dicebot.source.Die;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class KeepModifierTest {

    @Test
    void shouldKeepAllDiceIfNumberTooKeepIsHigherThanTotalDice() {
        DiceRoll rolls = new DiceRoll(5, 1, 6);
        KeepModifier modifier = new KeepModifier(new DirectionSelector(DirectionSelector.Direction.HIGH, 6));
        DiceRoll modified = modifier.modifyRoll(rolls, Die.D6);
        assertTrue(IntStream.range(0, modified.getRolls().length)
            .mapToObj(modified::getMetaDataForRoll)
            .noneMatch(metaData -> metaData.contains(MetaData.DROPPED)));
    }

    @Test
    void shouldKeepNoDiceIfNumberToKeepIsZero() {
        DiceRoll rolls = new DiceRoll(5, 1, 6);
        KeepModifier modifier = new KeepModifier(new DirectionSelector(DirectionSelector.Direction.HIGH, 0));
        DiceRoll modified = modifier.modifyRoll(rolls, Die.D6);
        assertTrue(IntStream.range(0, modified.getRolls().length)
            .mapToObj(modified::getMetaDataForRoll)
            .allMatch(metaData -> metaData.contains(MetaData.DROPPED)));
    }

    @Test
    void shouldThrowForNegativeNumberOfDiceToKeep() {
        DiceRoll rolls = new DiceRoll(5, 1, 6);
        assertThrows(IllegalArgumentException.class,
                () -> new KeepModifier(new DirectionSelector(DirectionSelector.Direction.HIGH, -2)));
    }

    @Test
    void shouldKeepHigherDiceIfDirectionHigh() {
        DiceRoll rolls = new DiceRoll(5, 1, 6);
        KeepModifier modifier = new KeepModifier(new DirectionSelector(DirectionSelector.Direction.HIGH, 2));
        DiceRoll modified = modifier.modifyRoll(rolls, Die.D6);
        assertArrayEquals(rolls.getRolls(), modified.getRolls());
        assertFalse(modified.getMetaDataForRoll(0).contains(MetaData.DROPPED));
        assertTrue(modified.getMetaDataForRoll(1).contains(MetaData.DROPPED));
        assertFalse(modified.getMetaDataForRoll(2).contains(MetaData.DROPPED));
    }

    @Test
    void shouldKeepLowerDiceIfDirectionLow() {
        DiceRoll rolls = new DiceRoll(5, 1, 6);
        KeepModifier modifier = new KeepModifier(new DirectionSelector(DirectionSelector.Direction.LOW, 2));
        DiceRoll modified = modifier.modifyRoll(rolls, Die.D6);
        assertArrayEquals(rolls.getRolls(), modified.getRolls());
        assertFalse(modified.getMetaDataForRoll(0).contains(MetaData.DROPPED));
        assertFalse(modified.getMetaDataForRoll(1).contains(MetaData.DROPPED));
        assertTrue(modified.getMetaDataForRoll(2).contains(MetaData.DROPPED));
    }

}