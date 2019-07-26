package io.github.tfriedrichs.dicebot.operator;

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

    BinaryOperator(String symbol,
        BinaryOperatorFunction function, int precedence,
        Type type) {
        this.symbol = symbol;
        this.function = function;
        this.precedence = precedence;
        this.type = type;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getPrecedence() {
        return precedence;
    }

    public Type getType() {
        return type;
    }

    public int apply(RoundingStrategy roundingStrategy, int left, int right) {
        return function.apply(roundingStrategy, left, right);
    }

    public enum Type {
        PREFIX,
        INFIX
    }
}
