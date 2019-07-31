package io.github.tfriedrichs.dicebot.operator;

import java.util.function.IntUnaryOperator;

public enum UnaryOperator {
    MINUS("-", a -> -a, 30),
    ABS("abs", Math::abs, 100);

    private final String symbol;
    private final IntUnaryOperator function;
    private final int precedence;

    UnaryOperator(String symbol, IntUnaryOperator function, int precedence) {
        this.symbol = symbol;
        this.function = function;
        this.precedence = precedence;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getPrecedence() {
        return precedence;
    }

    public int apply(int argument) {
        return function.applyAsInt(argument);
    }
}
