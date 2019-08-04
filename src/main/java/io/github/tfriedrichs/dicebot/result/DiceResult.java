package io.github.tfriedrichs.dicebot.result;

/**
 * Interfaces for the results of a rolled {@link io.github.tfriedrichs.dicebot.expression.DiceExpression}.
 * <p>
 * The structure closely mirrors that of a dice expression. Each expression subclass has a respective subclass implementing
 * this interface.
 */
public interface DiceResult {

    /**
     * Gets the precedence of this part of the expression.
     * The higher the value, the closer is the binding.
     *
     * @return the precedence
     */
    int getPrecedence();

    /**
     * Gets the value as calculated by the {@link io.github.tfriedrichs.dicebot.evaluator.DiceRollEvaluator}.
     *
     * @return the value.
     */
    int getValue();

    /**
     * Accept a {@link DiceResultVisitor} to simulate double dispatch.
     *
     * @param visitor the visitor
     * @param <T>     the return type of the visitor
     * @return the value calculated by the visitor.
     */
    <T> T accept(DiceResultVisitor<T> visitor);

}
