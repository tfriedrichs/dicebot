package io.github.tfriedrichs.dicebot.result;

public class FixedNumberResult implements DiceResult {

    private final int value;

    public FixedNumberResult(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return this.value;
    }
}
