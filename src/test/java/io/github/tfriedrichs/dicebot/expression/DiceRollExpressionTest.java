package io.github.tfriedrichs.dicebot.expression;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.github.tfriedrichs.dicebot.evaluator.DiceRollEvaluator;
import io.github.tfriedrichs.dicebot.modifier.DiceRollModifier;
import io.github.tfriedrichs.dicebot.result.DiceResult;
import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.result.DiceRollResult;
import io.github.tfriedrichs.dicebot.source.Die;
import io.github.tfriedrichs.dicebot.source.FixedRandomSource;
import io.github.tfriedrichs.dicebot.source.RandomSource;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

class DiceRollExpressionTest {

    @Test
    void shouldRollSingleFixedDieWithoutModifiers() {
        RandomSource source = new FixedRandomSource(1, 2, 3);
        DiceRollExpression expression = new DiceRollExpression.Builder()
            .withDie(new Die(source, 6))
            .withNumberOfDice(1)
            .build();
        assertEquals(1, expression.roll().getValue());
        assertEquals(2, expression.roll().getValue());
    }

    @Test
    void shouldRollStackedExpressionWithoutModifiers() {
        RandomSource source = new FixedRandomSource(1, 2, 3, 4, 5);
        DiceRollExpression expression = new DiceRollExpression.Builder()
            .withDie(new Die(source, 6))
            .withNumberOfDice(new DiceRollExpression.Builder()
                .withDie(new Die(source, 6))
                .withNumberOfDice(1)
                .build())
            .build();
        assertEquals(2, expression.roll().getValue());
        assertEquals(10, expression.roll().getValue());
    }

    @Test
    void shouldThrowForNegativeNumberOfDice() {
        RandomSource source = new FixedRandomSource(1, 2, 3, 4, 5);
        DiceRollExpression expression = new DiceRollExpression.Builder()
            .withDie(new Die(source, 6))
            .withNumberOfDice(-2)
            .build();
        assertThrows(IllegalArgumentException.class, () -> expression.roll().getValue());

    }


    @Test
    void shouldCorrectlyApplyModifiers() {
        RandomSource source = new FixedRandomSource(1, 2, 3, 4, 5);
        DiceRollModifier modifier = mock(DiceRollModifier.class);
        when(modifier.modifyRoll(any(DiceRoll.class), any(Die.class)))
            .thenReturn(new DiceRoll(3, 3, 3));
        DiceRollExpression expression = new DiceRollExpression.Builder()
            .withDie(new Die(source, 6))
            .withNumberOfDice(3)
            .withModifier(modifier)
            .build();

        DiceResult result = expression.roll();
        assertTrue(result instanceof DiceRollResult);
        int[] roll = ((DiceRollResult) result).getRolls();
        assertArrayEquals(new int[]{3, 3, 3}, roll);
    }

    @Test
    void shouldCorrectlyApplyModifiersInOrder() {
        RandomSource source = new FixedRandomSource(1, 2, 3, 4, 5);
        DiceRollModifier modifier1 = mock(DiceRollModifier.class);
        when(modifier1.modifyRoll(any(DiceRoll.class), any(Die.class)))
            .thenReturn(new DiceRoll(2, 2, 2));
        DiceRollModifier modifier2 = mock(DiceRollModifier.class);
        when(modifier2.modifyRoll(any(DiceRoll.class), any(Die.class)))
            .thenReturn(new DiceRoll(3, 3, 3));
        DiceRollModifier modifier3 = mock(DiceRollModifier.class);
        when(modifier3.modifyRoll(any(DiceRoll.class), any(Die.class)))
            .thenReturn(new DiceRoll(4, 4, 4));

        InOrder inOrder = inOrder(modifier1, modifier2, modifier3);
        DiceRollExpression expression = new DiceRollExpression.Builder()
            .withDie(new Die(source, 6))
            .withNumberOfDice(3)
            .withModifier(modifier1)
            .withModifier(modifier2)
            .withModifier(modifier3)
            .build();

        expression.roll();

        inOrder.verify(modifier1).modifyRoll(any(DiceRoll.class), any(Die.class));
        inOrder.verify(modifier2).modifyRoll(any(DiceRoll.class), any(Die.class));
        inOrder.verify(modifier3).modifyRoll(any(DiceRoll.class), any(Die.class));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldUseAssignedEvaluator() {
        RandomSource source = new FixedRandomSource(1, 2, 3);
        DiceRollEvaluator evaluator = mock(DiceRollEvaluator.class);
        when(evaluator.evaluate(any(DiceRoll.class))).thenReturn(100);
        InOrder inOrder = inOrder(evaluator);
        DiceRollExpression expression = new DiceRollExpression.Builder()
            .withDie(new Die(source, 6))
            .withNumberOfDice(1)
            .withEvaluator(evaluator)
            .build();
        assertEquals(100, expression.roll().getValue());
        inOrder.verify(evaluator).evaluate(any(DiceRoll.class));
        inOrder.verifyNoMoreInteractions();
    }


}