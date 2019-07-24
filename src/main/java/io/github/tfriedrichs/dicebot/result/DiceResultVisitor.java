package io.github.tfriedrichs.dicebot.result;

public interface DiceResultVisitor<T> {

    default T visit(DiceResult context) {
        return context.accept(this);
    }

    T visitBinaryOperator(BinaryOperatorResult context);

    T visitUnaryOperator(UnaryOperatorResult context);

    T visitDiceRoll(DiceRollResult context);

    T visitNumber(NumberResult context);

}
