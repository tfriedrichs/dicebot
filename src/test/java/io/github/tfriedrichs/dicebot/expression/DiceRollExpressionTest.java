package io.github.tfriedrichs.dicebot.expression;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.github.tfriedrichs.dicebot.evaluator.DiceRollEvaluator;
import io.github.tfriedrichs.dicebot.modifier.DiceRollModifier;
import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.result.DiceRollResult;
import io.github.tfriedrichs.dicebot.source.FixedRandomSource;
import io.github.tfriedrichs.dicebot.source.RandomSource;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

class DiceRollExpressionTest {

    @Test
    void shouldRollSingleFixedDieWithoutModifiers() {
        RandomSource source = new FixedRandomSource(1, 2, 3);
        DiceRollExpression expression = new DiceRollExpression.Builder()
            .withRandomSource(source)
            .withNumberOfDice(1)
            .withNumberOfSides(6)
            .build();
        assertEquals(1, expression.roll().evaluate());
        assertEquals(2, expression.roll().evaluate());
    }

    @Test
    void shouldRollStackedExpressionWithoutModifiers() {
        RandomSource source = new FixedRandomSource(1, 2, 3, 4, 5);
        DiceRollExpression expression = new DiceRollExpression.Builder()
            .withRandomSource(source)
            .withNumberOfDice(new DiceRollExpression.Builder()
                .withRandomSource(source)
                .withNumberOfDice(1)
                .withNumberOfSides(6)
                .build())
            .withNumberOfSides(6)
            .build();
        assertEquals(2, expression.roll().evaluate());
        assertEquals(10, expression.roll().evaluate());
    }

    @Test
    void shouldRollStackedSidesExpressionWithoutModifiers() {
        RandomSource source = new FixedRandomSource(1, 2, 3, 4, 5);
        DiceRollExpression expression = new DiceRollExpression.Builder()
            .withRandomSource(source)
            .withNumberOfDice(2)
            .withNumberOfSides(new DiceRollExpression.Builder()
                .withRandomSource(source)
                .withNumberOfDice(1)
                .withNumberOfSides(6)
                .build())
            .build();
        assertEquals(5, expression.roll().evaluate());
        assertEquals(6, expression.roll().evaluate());
    }

    @Test
    void shouldThrowForNegativeNumberOfDice() {
        RandomSource source = new FixedRandomSource(1, 2, 3, 4, 5);
        DiceRollExpression expression = new DiceRollExpression.Builder()
            .withRandomSource(source)
            .withNumberOfDice(-2)
            .withNumberOfSides(6)
            .build();
        assertThrows(IllegalArgumentException.class, () -> expression.roll().evaluate());

    }


    @Test
    void shouldThrowForNegativeNumberOfSides() {
        RandomSource source = new FixedRandomSource(1, 2, 3, 4, 5);
        DiceRollExpression expression = new DiceRollExpression.Builder()
            .withRandomSource(source)
            .withNumberOfDice(23)
            .withNumberOfSides(-2)
            .build();
        assertThrows(IllegalArgumentException.class, () -> expression.roll().evaluate());
    }

    @Test
    void shouldCorrectlyApplyModifiers() {
        RandomSource source = new FixedRandomSource(1, 2, 3, 4, 5);
        DiceRollModifier modifier = mock(DiceRollModifier.class);
        when(modifier.modifyRoll(any(int[].class), anyInt(), anyInt()))
            .thenReturn(new int[]{3, 3, 3});
        DiceRollExpression expression = new DiceRollExpression.Builder()
            .withRandomSource(source)
            .withNumberOfDice(3)
            .withNumberOfSides(6)
            .withModifier(modifier)
            .build();

        DiceRollResult result = expression.roll();
        assertTrue(result instanceof DiceRoll);
        int[] roll = ((DiceRoll) result).getRolls();
        assertArrayEquals(new int[]{3, 3, 3}, roll);
    }

    @Test
    void shouldCorrectlyApplyModifiersInOrder() {
        RandomSource source = new FixedRandomSource(1, 2, 3, 4, 5);
        DiceRollModifier modifier1 = mock(DiceRollModifier.class);
        when(modifier1.modifyRoll(any(int[].class), anyInt(), anyInt()))
            .thenReturn(new int[]{2, 2, 2});
        DiceRollModifier modifier2 = mock(DiceRollModifier.class);
        when(modifier2.modifyRoll(any(int[].class), anyInt(), anyInt()))
            .thenReturn(new int[]{3, 3, 3});
        DiceRollModifier modifier3 = mock(DiceRollModifier.class);
        when(modifier3.modifyRoll(any(int[].class), anyInt(), anyInt()))
            .thenReturn(new int[]{4, 4, 4});

        InOrder inOrder = inOrder(modifier1, modifier2, modifier3);
        DiceRollExpression expression = new DiceRollExpression.Builder()
            .withRandomSource(source)
            .withNumberOfDice(3)
            .withNumberOfSides(6)
            .withModifier(modifier1)
            .withModifier(modifier2)
            .withModifier(modifier3)
            .build();

        expression.roll();

        inOrder.verify(modifier1).modifyRoll(new int[]{1, 2, 3}, 1, 7);
        inOrder.verify(modifier2).modifyRoll(new int[]{2, 2, 2}, 1, 7);
        inOrder.verify(modifier3).modifyRoll(new int[]{3, 3, 3}, 1, 7);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldUseAssignedEvaluator() {
        RandomSource source = new FixedRandomSource(1, 2, 3);
        DiceRollEvaluator evaluator = mock(DiceRollEvaluator.class);
        when(evaluator.evaluate(any(int[].class))).thenReturn(100);
        InOrder inOrder = inOrder(evaluator);
        DiceRollExpression expression = new DiceRollExpression.Builder()
            .withRandomSource(source)
            .withNumberOfDice(1)
            .withNumberOfSides(6)
            .withEvaluator(evaluator)
            .build();
        assertEquals(100, expression.roll().evaluate());
        inOrder.verify(evaluator).evaluate(new int[]{1});
        inOrder.verifyNoMoreInteractions();
    }


}