package io.github.tfriedrichs.dicebot.result;

public class NumberResult implements DiceResult {

    private static final int PRECEDENCE = 100;

    private final int value;

    public NumberResult(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public <T> T accept(DiceResultVisitor<T> visitor) {
        return visitor.visitNumber(this);
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }

    @Override
    public int getPrecedence() {
        return PRECEDENCE;
    }
}
