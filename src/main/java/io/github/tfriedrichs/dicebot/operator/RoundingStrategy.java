package io.github.tfriedrichs.dicebot.operator;

import java.util.function.DoubleToIntFunction;

public enum RoundingStrategy {
    DOWN(a -> (int) Math.floor(a)),
    UP(a -> (int) Math.ceil(a)),
    NEAREST(a -> (int) Math.round(a));

    private final DoubleToIntFunction function;

    RoundingStrategy(DoubleToIntFunction function) {
        this.function = function;
    }

    public int apply(double number) {
        return function.applyAsInt(number);
    }
}
