package io.github.tfriedrichs.dicebot.result;

import io.github.tfriedrichs.dicebot.result.DiceRoll.MetaData;

import java.util.Arrays;
import java.util.EnumSet;

public class DiceRollResult implements DiceResult {

    private static final int PRECEDENCE = 100;

    private final int value;
    private final DiceRoll rolls;

    public DiceRollResult(int value, DiceRoll rolls) {
        this.value = value;
        this.rolls = rolls;
    }

    public int[] getRolls() {
        return rolls.getRolls();
    }

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
