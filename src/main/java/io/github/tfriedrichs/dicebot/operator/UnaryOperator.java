package io.github.tfriedrichs.dicebot.operator;

import java.util.function.IntUnaryOperator;

/**
 * Enum for unary operators for use in {@link io.github.tfriedrichs.dicebot.expression.UnaryOperatorExpression}.
 */
public enum UnaryOperator {
    MINUS("-", a -> -a, 30),
    ABS("abs", Math::abs, 100);

    private final String symbol;
    private final IntUnaryOperator function;
    private final int precedence;

    /**
     * Constructor.
     *
     * @param symbol     the string representation of this operator
     * @param function   the function
     * @param precedence the precedence
     */
    UnaryOperator(String symbol, IntUnaryOperator function, int precedence) {
        this.symbol = symbol;
        this.function = function;
        this.precedence = precedence;
    }

    /**
     * Gets the string representation of this operator.
     *
     * @return the string representation
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Gets the precedence.
     *
     * @return the precedence
     */
    public int getPrecedence() {
        return precedence;
    }

    /**
     * Applies the operator and gets the result.
     *
     * @param argument the operand value
     * @return the result
     */
    public int apply(int argument) {
        return function.applyAsInt(argument);
    }
}
