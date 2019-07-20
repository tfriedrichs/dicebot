package io.github.tfriedrichs.dicebot.result;

import java.util.function.IntBinaryOperator;

public class BinaryOperatorResult implements DiceRollResult {

    private final Operator operator;
    private final DiceRollResult left;
    private final DiceRollResult right;

    public BinaryOperatorResult(
        Operator operator, DiceRollResult left,
        DiceRollResult right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public int evaluate() {
        return operator.getOperator().applyAsInt(left.evaluate(), right.evaluate());
    }

    public Operator getOperator() {
        return operator;
    }

    public DiceRollResult getLeft() {
        return left;
    }

    public DiceRollResult getRight() {
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
