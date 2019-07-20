package io.github.tfriedrichs.dicebot.result;

public class FixedNumberResult implements DiceRollResult {

    private final int value;

    public FixedNumberResult(int value) {
        this.value = value;
    }

    @Override
    public int evaluate() {
        return value;
    }
}
