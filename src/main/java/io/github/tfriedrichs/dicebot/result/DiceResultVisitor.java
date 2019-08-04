package io.github.tfriedrichs.dicebot.result;

/**
 * Visitor for calculating results on a {@link DiceResult}.
 *
 * @param <T> the return type of this visitor.
 */
public interface DiceResultVisitor<T> {

    /**
     * Visit a dice result and dynamically dispatch the call to the respective sub-method.
     *
     * @param context the result to visit
     * @return the calculated result
     */
    default T visit(DiceResult context) {
        return context.accept(this);
    }

    /**
     * Visit a binary operator.
     *
     * @param context the binary operator
     * @return the result
     */
    T visitBinaryOperator(BinaryOperatorResult context);

    /**
     * Visit an unary operator.
     *
     * @param context the unary operator
     * @return the result
     */
    T visitUnaryOperator(UnaryOperatorResult context);

    /**
     * Visit a dice roll.
     *
     * @param context the dice roll
     * @return the result
     */
    T visitDiceRoll(DiceRollResult context);

    /**
     * Visit a number.
     *
     * @param context the number
     * @return the result
     */
    T visitNumber(NumberResult context);

}
