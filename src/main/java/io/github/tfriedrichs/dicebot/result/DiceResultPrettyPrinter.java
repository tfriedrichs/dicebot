package io.github.tfriedrichs.dicebot.result;

import io.github.tfriedrichs.dicebot.operator.BinaryOperator;

/**
 * {@link DiceResultVisitor} which pretty prints a {@link DiceResult}.
 * <p>
 * This visitor observes proper order of operations and only inserts parentheses if necessary.
 * Dice rolls are expanded, so that every roll is visible. Any metadata is ignored by this representation.
 */
public class DiceResultPrettyPrinter implements DiceResultVisitor<String> {

    /**
     * Pretty print a given result.
     *
     * @param diceResult the result to print
     * @return a string representation of the result
     */
    public String prettyPrint(DiceResult diceResult) {
        return diceResult.getValue() + " = " + visit(diceResult);
    }

    @Override
    public String visitBinaryOperator(BinaryOperatorResult context) {
        if (context.getOperator().getType() == BinaryOperator.Type.INFIX) {
            String left = context.getLeft().getPrecedence() < context.getPrecedence() ? surroundWithParentheses(visit(context.getLeft())) : visit(context.getLeft());
            String right = context.getRight().getPrecedence() <= context.getPrecedence() ? surroundWithParentheses(visit(context.getRight())) : visit(context.getRight());
            return left + " " + context.getOperator().getSymbol() + " " + right;
        } else {
            return context.getOperator().getSymbol() + surroundWithParentheses(visit(context.getLeft()) + ", " + visit(context.getRight()));
        }
    }

    @Override
    public String visitUnaryOperator(UnaryOperatorResult context) {
        return context.getOperator().getSymbol() + (context.getInner().getPrecedence() <= context.getPrecedence() ? surroundWithParentheses(visit(context.getInner())) : visit(context.getInner()));
    }

    @Override
    public String visitDiceRoll(DiceRollResult context) {
        return context.toString();
    }

    @Override
    public String visitNumber(NumberResult context) {
        return context.toString();
    }

    private String surroundWithParentheses(String string) {
        return "(" + string + ")";
    }
}
