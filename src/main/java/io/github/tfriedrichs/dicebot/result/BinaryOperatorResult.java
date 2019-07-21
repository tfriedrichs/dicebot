package io.github.tfriedrichs.dicebot.result;

import java.util.function.IntBinaryOperator;

public class BinaryOperatorResult implements DiceResult {

    private final Operator operator;
    private final DiceResult left;
    private final DiceResult right;

    public BinaryOperatorResult(
        Operator operator, DiceResult left,
        DiceResult right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public int getValue() {
        return operator.getOperator().applyAsInt(left.getValue(), right.getValue());
    }

    public Operator getOperator() {
        return operator;
    }

    public DiceResult getLeft() {
        return left;
    }

    public DiceResult getRight() {
        return right;
    }

    public enum Operator {
        PLUS("+", (a, b) -> a + b),
        MINUS("-", (a, b) -> a - b),
        TIMES("*", (a, b) -> a * b);

        private final String representation;
        private final IntBinaryOperator operator;

        Operator(String representation, IntBinaryOperator operator) {
            this.representation = representation;
            this.operator = operator;
        }

        public String getRepresentation() {
            return representation;
        }

        public IntBinaryOperator getOperator() {
            return operator;
        }
    }
}
