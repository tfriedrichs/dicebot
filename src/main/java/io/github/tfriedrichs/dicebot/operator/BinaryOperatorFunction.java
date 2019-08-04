package io.github.tfriedrichs.dicebot.operator;

@FunctionalInterface
public interface BinaryOperatorFunction {

    /**
     * Applies the binary operator.
     *
     * @param roundingStrategy the rounding strategy
     * @param left             the left operand value
     * @param right            the right operand value
     * @return the result
     */
    int apply(RoundingStrategy roundingStrategy, int left, int right);

}
