package io.github.tfriedrichs.dicebot.result;

public class NumberResult implements DiceResult {

    private final int value;

    public NumberResult(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return this.value;
    }
}
