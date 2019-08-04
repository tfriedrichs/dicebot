package io.github.tfriedrichs.dicebot.result;

import io.github.tfriedrichs.dicebot.result.DiceRoll.MetaData;

import java.util.Arrays;
import java.util.EnumSet;

/**
 * {@link DiceResult} from rolling a {@link io.github.tfriedrichs.dicebot.expression.DiceRollExpression}.
 */
public class DiceRollResult implements DiceResult {

    private static final int PRECEDENCE = 100;

    private final int value;
    private final DiceRoll rolls;

    /**
     * Constructor.
     *
     * @param value the calculated value
     * @param rolls the underlying roll
     */
    public DiceRollResult(int value, DiceRoll rolls) {
        this.value = value;
        this.rolls = rolls;
    }

    /**
     * Gets the roll values.
     *
     * @return an array of roll values
     */
    public int[] getRolls() {
        return rolls.getRolls();
    }

    /**
     * Gets the metadata for a single die roll.
     *
     * @param index the index of the roll
     * @return the metadata
     */
    public EnumSet<MetaData> getMetaDataForRoll(int index) {
        return rolls.getMetaDataForRoll(index);
    }

    @Override
    public String toString() {
        return this.value + Arrays.toString(getRolls());
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public <T> T accept(DiceResultVisitor<T> visitor) {
        return visitor.visitDiceRoll(this);
    }

    @Override
    public int getPrecedence() {
        return PRECEDENCE;
    }
}
