package io.github.tfriedrichs.dicebot.parser;

import io.github.tfriedrichs.dicebot.expression.BinaryOperatorExpression;
import io.github.tfriedrichs.dicebot.expression.DiceExpression;
import io.github.tfriedrichs.dicebot.expression.NumberExpression;
import io.github.tfriedrichs.dicebot.expression.UnaryOperatorExpression;
import io.github.tfriedrichs.dicebot.operator.BinaryOperator;
import io.github.tfriedrichs.dicebot.operator.RoundingStrategy;
import io.github.tfriedrichs.dicebot.operator.UnaryOperator;

public class DiceExpressionConverterVisitor extends DiceExpressionBaseVisitor<DiceExpression> {

    private final RoundingStrategy roundingStrategy;

    public DiceExpressionConverterVisitor(RoundingStrategy roundingStrategy) {
        this.roundingStrategy = roundingStrategy;
    }

    @Override
    public DiceExpression visitParse(DiceExpressionParser.ParseContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public DiceExpression visitParenExpression(DiceExpressionParser.ParenExpressionContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public DiceExpression visitAddExpression(DiceExpressionParser.AddExpressionContext ctx) {
        BinaryOperator op = ctx.op.getType() == DiceExpressionParser.PLUS ? BinaryOperator.PLUS : BinaryOperator.MINUS;

        return new BinaryOperatorExpression(roundingStrategy, BinaryOperator.PLUS,
                visit(ctx.expression(0)), visit(ctx.expression(1)));
    }

    @Override
    public DiceExpression visitMultExpression(DiceExpressionParser.MultExpressionContext ctx) {
        BinaryOperator op = ctx.op.getType() == DiceExpressionParser.MULT ? BinaryOperator.TIMES : BinaryOperator.DIVIDE;
        return new BinaryOperatorExpression(roundingStrategy, op,
                visit(ctx.expression(0)), visit(ctx.expression(1)));
    }

    @Override
    public DiceExpression visitMaxExpression(DiceExpressionParser.MaxExpressionContext ctx) {
        return new BinaryOperatorExpression(roundingStrategy, BinaryOperator.MAX,
                visit(ctx.expression(0)), visit(ctx.expression(1)));
    }

    @Override
    public DiceExpression visitMinExpression(DiceExpressionParser.MinExpressionContext ctx) {
        return new BinaryOperatorExpression(roundingStrategy, BinaryOperator.MIN,
                visit(ctx.expression(0)), visit(ctx.expression(1)));
    }

    @Override
    public DiceExpression visitAbsExpression(DiceExpressionParser.AbsExpressionContext ctx) {
        return new UnaryOperatorExpression(UnaryOperator.ABS, visit(ctx.expression()));
    }

    @Override
    public DiceExpression visitMinusExpression(DiceExpressionParser.MinusExpressionContext ctx) {
        return new UnaryOperatorExpression(UnaryOperator.MINUS, visit(ctx.expression()));
    }

    @Override
    public DiceExpression visitNumberAtom(DiceExpressionParser.NumberAtomContext ctx) {
        return new NumberExpression(Integer.parseInt(ctx.getText()));
    }
}
