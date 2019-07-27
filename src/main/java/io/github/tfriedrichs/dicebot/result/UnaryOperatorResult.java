package io.github.tfriedrichs.dicebot.result;

import io.github.tfriedrichs.dicebot.operator.UnaryOperator;

public class UnaryOperatorResult implements DiceResult {

    private final UnaryOperator operator;
    private final DiceResult inner;

    public UnaryOperatorResult(UnaryOperator operator, DiceResult inner) {
        this.operator = operator;
        this.inner = inner;
    }

    @Override
    public int getValue() {
        return operator.apply(inner.getValue());
    }

    @Override
    public <T> T accept(DiceResultVisitor<T> visitor) {
        return visitor.visitUnaryOperator(this);
    }

    public DiceResult getInner() {
        return inner;
    }

    @Override
    public String toString() {
        return operator + "(" + inner + ")";
    }

    @Override
    public int getPrecedence() {
        return operator.getPrecedence();
    }

    public UnaryOperator getOperator() {
        return this.operator;
    }
}