package io.github.tfriedrichs.dicebot.operator;

/**
 * Enum for binary operators for use in {@link io.github.tfriedrichs.dicebot.expression.BinaryOperatorExpression}.
 */
public enum BinaryOperator {
    PLUS("+", (r, x, y) -> x + y, 10, Type.INFIX),
    MINUS("-", (r, x, y) -> x - y, 10, Type.INFIX),
    TIMES("*", (r, x, y) -> x * y, 20, Type.INFIX),
    DIVIDE("/", (r, x, y) -> r.apply((double) x / y), 20, Type.INFIX),
    MAX("max", (r, x, y) -> Math.max(x, y), 100, Type.PREFIX),
    MIN("min", (r, x, y) -> Math.min(x, y), 100, Type.PREFIX);

    private final String symbol;
    private final BinaryOperatorFunction function;
    private final int precedence;
    private final Type type;

    /**
     * Constructor.
     *
     * @param symbol     the string representation of the operator
     * @param function   the function of this operator
     * @param precedence the operator precedence
     * @param type       if this operator is infix or prefix
     */
    BinaryOperator(String symbol,
                   BinaryOperatorFunction function, int precedence,
                   Type type) {
        this.symbol = symbol;
        this.function = function;
        this.precedence = precedence;
        this.type = type;
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
     * Gets the type.
     *
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * Applies the operate and gets the result.
     *
     * @param roundingStrategy the rounding strategy
     * @param left the left operand value
     * @param right the right operand value
     * @return the calculated value
     */
    public int apply(RoundingStrategy roundingStrategy, int left, int right) {
        return function.apply(roundingStrategy, left, right);
    }

    /**
     * The operator type.
     */
    public enum Type {
        PREFIX,
        INFIX
    }
}
