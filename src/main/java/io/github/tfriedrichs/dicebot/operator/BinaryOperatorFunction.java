package io.github.tfriedrichs.dicebot.operator;

@FunctionalInterface
public interface BinaryOperatorFunction {

    int apply(RoundingStrategy roundingStrategy, int left, int right);

}
