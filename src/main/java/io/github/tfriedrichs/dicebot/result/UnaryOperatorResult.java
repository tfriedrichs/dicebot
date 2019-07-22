package io.github.tfriedrichs.dicebot.result;

import java.util.function.IntUnaryOperator;

public class UnaryOperatorResult implements DiceResult {

    private final Operator operator;
    private final DiceResult inner;

    public UnaryOperatorResult(Operator operator, DiceResult inner) {
        this.operator = operator;
        this.inner = inner;
    }

    @Override
    public int getValue() {
        return operator.getOperator().applyAsInt(inner.getValue());
    }

    public DiceResult getInner() {
        return inner;
    }

    public enum Operator {
        MINUS("-", a -> -a);

        private final String representation;
        private final IntUnaryOperator operator;

        Operator(String representation, IntUnaryOperator operator) {
            this.representation = representation;
            this.operator = operator;
        }

        public String getRepresentation() {
            return representation;
        }

        public IntUnaryOperator getOperator() {
            return operator;
        }
    }
}
