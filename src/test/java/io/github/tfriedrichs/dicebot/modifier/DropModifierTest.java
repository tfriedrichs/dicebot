package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.selector.DirectionSelector;
import io.github.tfriedrichs.dicebot.source.Die;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class DropModifierTest {

    @Test
    void shouldDropAllDiceIfNumberTooDropIsHigherThanTotalDice() {
        DiceRoll rolls = new DiceRoll(5, 1, 6);
        DropModifier modifier = new DropModifier(new DirectionSelector(DirectionSelector.Direction.HIGH, 6));
        DiceRoll modified = modifier.modifyRoll(rolls, Die.D6);
        assertTrue(IntStream.range(0, modified.getRolls().length)
                .mapToObj(modified::getMetaDataForRoll)
                .allMatch(metaData -> metaData.contains(DiceRoll.MetaData.DROPPED)));
    }

    @Test
    void shouldDropNoDiceIfNumberToDropIsZero() {
        DiceRoll rolls = new DiceRoll(5, 1, 6);
        DropModifier modifier = new DropModifier(new DirectionSelector(DirectionSelector.Direction.HIGH, 0));
        DiceRoll modified = modifier.modifyRoll(rolls, Die.D6);
        assertTrue(IntStream.range(0, modified.getRolls().length)
                .mapToObj(modified::getMetaDataForRoll)
                .noneMatch(metaData -> metaData.contains(DiceRoll.MetaData.DROPPED)));
    }

    @Test
    void shouldThrowForNegativeNumberOfDiceToDrop() {
        DiceRoll rolls = new DiceRoll(5, 1, 6);
        assertThrows(IllegalArgumentException.class,
                () -> new DropModifier(new DirectionSelector(DirectionSelector.Direction.HIGH, -2)));
    }

    @Test
    void shouldDropHigherDiceIfDirectionHigh() {
        DiceRoll rolls = new DiceRoll(5, 1, 6);
        DropModifier modifier = new DropModifier(new DirectionSelector(DirectionSelector.Direction.HIGH, 2));
        DiceRoll modified = modifier.modifyRoll(rolls, Die.D6);
        assertArrayEquals(rolls.getRolls(), modified.getRolls());
        assertTrue(modified.getMetaDataForRoll(0).contains(DiceRoll.MetaData.DROPPED));
        assertFalse(modified.getMetaDataForRoll(1).contains(DiceRoll.MetaData.DROPPED));
        assertTrue(modified.getMetaDataForRoll(2).contains(DiceRoll.MetaData.DROPPED));
    }

    @Test
    void shouldKeepLowerDiceIfDirectionLow() {
        DiceRoll rolls = new DiceRoll(5, 1, 6);
        DropModifier modifier = new DropModifier(new DirectionSelector(DirectionSelector.Direction.LOW, 2));
        DiceRoll modified = modifier.modifyRoll(rolls, Die.D6);
        assertArrayEquals(rolls.getRolls(), modified.getRolls());
        assertTrue(modified.getMetaDataForRoll(0).contains(DiceRoll.MetaData.DROPPED));
        assertTrue(modified.getMetaDataForRoll(1).contains(DiceRoll.MetaData.DROPPED));
        assertFalse(modified.getMetaDataForRoll(2).contains(DiceRoll.MetaData.DROPPED));
    }

}