package io.github.tfriedrichs.dicebot.parser;

import io.github.tfriedrichs.dicebot.evaluator.SuccessEvaluator;
import io.github.tfriedrichs.dicebot.evaluator.SuccessFailureEvaluator;
import io.github.tfriedrichs.dicebot.expression.*;
import io.github.tfriedrichs.dicebot.modifier.*;
import io.github.tfriedrichs.dicebot.operator.BinaryOperator;
import io.github.tfriedrichs.dicebot.operator.RoundingStrategy;
import io.github.tfriedrichs.dicebot.operator.UnaryOperator;
import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.selector.ComparisonSelector;
import io.github.tfriedrichs.dicebot.selector.DirectionSelector;
import io.github.tfriedrichs.dicebot.source.Die;
import io.github.tfriedrichs.dicebot.source.RandomSource;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

/**
 * Visitor which transforms an antlr {@link org.antlr.v4.runtime.tree.ParseTree} to a {@link DiceExpression}.
 */
public class DiceExpressionConverterVisitor extends DiceExpressionBaseVisitor<DiceExpression> {

    private static final int MAX_DEPTH = 100;
    private final RoundingStrategy roundingStrategy;
    private final RandomSource randomSource;

    /**
     * Constructor.
     *
     * @param roundingStrategy the rounding strategy to use
     * @param randomSource     the random source to use
     */
    public DiceExpressionConverterVisitor(RoundingStrategy roundingStrategy, RandomSource randomSource) {
        this.roundingStrategy = roundingStrategy;
        this.randomSource = randomSource;
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

        return new BinaryOperatorExpression(roundingStrategy, op,
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

    @Override
    public DiceExpression visitDiceWithExpressionAtom(DiceExpressionParser.DiceWithExpressionAtomContext ctx) {
        Die die = getDie(ctx.sides());
        DiceRollExpression.Builder builder = new DiceRollExpression.Builder()
                .withNumberOfDice(visit(ctx.expression()))
                .withDie(die);

        for (DiceExpressionParser.ModifierContext modifierContext : ctx.modifier()) {
            addModifier(builder, die, modifierContext);
        }
        return builder.build();
    }

    @Override
    public DiceExpression visitDiceWithoutExpressionAtom(DiceExpressionParser.DiceWithoutExpressionAtomContext ctx) {
        Die die = getDie(ctx.sides());
        DiceRollExpression.Builder builder = new DiceRollExpression.Builder()
                .withNumberOfDice(1)
                .withDie(die);

        for (DiceExpressionParser.ModifierContext modifierContext : ctx.modifier()) {
            addModifier(builder, die, modifierContext);
        }
        return builder.build();
    }

    private void addModifier(DiceRollExpression.Builder builder, Die die, DiceExpressionParser.ModifierContext modifier) {
        if (modifier instanceof DiceExpressionParser.BraceModifierContext) {
            addModifier(builder, die, ((DiceExpressionParser.BraceModifierContext) modifier).modifier());
        }
        if (modifier instanceof DiceExpressionParser.KeepModifierContext) {
            builder.withModifier(new KeepModifier(getSelector(((DiceExpressionParser.KeepModifierContext) modifier).directionSelector())));
        }
        if (modifier instanceof DiceExpressionParser.DropHighModifierContext) {
            builder.withModifier(new DropModifier(new DirectionSelector(DirectionSelector.Direction.HIGH, getNumber(((DiceExpressionParser.DropHighModifierContext) modifier).NUMBER()))));
        }
        if (modifier instanceof DiceExpressionParser.DropLowModifierContext) {
            builder.withModifier(new DropModifier(new DirectionSelector(DirectionSelector.Direction.LOW, getNumber(((DiceExpressionParser.DropLowModifierContext) modifier).NUMBER()))));
        }
        if (modifier instanceof DiceExpressionParser.SuccessModifierContext) {
            builder.withModifier(new SuccessModifier(getSelector(((DiceExpressionParser.SuccessModifierContext) modifier).comparisonSelector())));
            builder.withEvaluator(new SuccessEvaluator());
        }
        if (modifier instanceof DiceExpressionParser.FailureModifierContext) {
            builder.withModifier(new FailureModifier(getSelector(((DiceExpressionParser.FailureModifierContext) modifier).comparisonSelector())));
            builder.withEvaluator(new SuccessFailureEvaluator());
        }
        if (modifier instanceof DiceExpressionParser.ExplodeModifierContext) {
            ComparisonSelector selector = ((DiceExpressionParser.ExplodeModifierContext) modifier).comparisonSelector() == null ? new ComparisonSelector(ComparisonSelector.Mode.EQUALS, die.getMax()) :
                    getSelector(((DiceExpressionParser.ExplodeModifierContext) modifier).comparisonSelector());
            builder.withModifier(new ExplodeModifier(MAX_DEPTH, selector));
        }
        if (modifier instanceof DiceExpressionParser.CompoundModifierContext) {
            ComparisonSelector selector = ((DiceExpressionParser.CompoundModifierContext) modifier).comparisonSelector() == null ? new ComparisonSelector(ComparisonSelector.Mode.EQUALS, die.getMax()) :
                    getSelector(((DiceExpressionParser.CompoundModifierContext) modifier).comparisonSelector());
            builder.withModifier(new CompoundModifier(MAX_DEPTH, selector));
        }
        if (modifier instanceof DiceExpressionParser.PenetrateModifierContext) {
            ComparisonSelector selector = ((DiceExpressionParser.PenetrateModifierContext) modifier).comparisonSelector() == null ? new ComparisonSelector(ComparisonSelector.Mode.EQUALS, die.getMax()) :
                    getSelector(((DiceExpressionParser.PenetrateModifierContext) modifier).comparisonSelector());
            builder.withModifier(new PenetrateModifier(MAX_DEPTH, selector));
        }
        if (modifier instanceof DiceExpressionParser.CriticalSuccessModifierContext) {
            builder.withModifier(new DiceAnnotator(DiceAnnotator.Mode.USE_DROPPED, DiceRoll.MetaData.CRITICAL_SUCCESS, getSelector(((DiceExpressionParser.CriticalSuccessModifierContext) modifier).comparisonSelector())));
        }
        if (modifier instanceof DiceExpressionParser.CriticalFailureModifierContext) {
            builder.withModifier(new DiceAnnotator(DiceAnnotator.Mode.USE_DROPPED, DiceRoll.MetaData.CRITICAL_FAILURE, getSelector(((DiceExpressionParser.CriticalFailureModifierContext) modifier).comparisonSelector())));
        }

    }

    private ComparisonSelector getSelector(DiceExpressionParser.ComparisonSelectorContext comparisonSelector) {
        ComparisonSelector.Mode mode =
                comparisonSelector.op == null ? ComparisonSelector.Mode.EQUALS :
                        comparisonSelector.op.getType() == DiceExpressionParser.LESSER ? ComparisonSelector.Mode.LESSER :
                                comparisonSelector.op.getType() == DiceExpressionParser.LESSER_EQUALS ? ComparisonSelector.Mode.LESSER_EQUALS :
                                        comparisonSelector.op.getType() == DiceExpressionParser.EQUALS ? ComparisonSelector.Mode.EQUALS :
                                                comparisonSelector.op.getType() == DiceExpressionParser.GREATER ? ComparisonSelector.Mode.GREATER :
                                                        ComparisonSelector.Mode.GREATER_EQUALS;
        return new ComparisonSelector(mode, getNumber(comparisonSelector.NUMBER()));
    }

    private DirectionSelector getSelector(DiceExpressionParser.DirectionSelectorContext directionSelector) {
        DirectionSelector.Direction direction = directionSelector.direction.getType() == DiceExpressionParser.HIGH ?
                DirectionSelector.Direction.HIGH : DirectionSelector.Direction.LOW;

        return new DirectionSelector(direction, getNumber(directionSelector.NUMBER()));
    }

    private int getNumber(TerminalNode number) {
        if (number == null) {
            return 1;
        } else {
            return Integer.parseInt(number.getText());
        }
    }


    private Die getDie(DiceExpressionParser.SidesContext sides) {
        if (sides.NUMBER() != null) {
            return new Die(randomSource, Integer.parseInt(sides.NUMBER().getText()));
        }
        if (sides.FATE() != null) {
            return new Die(randomSource, List.of(-1, 0, 1));
        }
        if (sides.PERCENT() != null) {
            return new Die(randomSource, 100);
        }
        return null;
    }
}
