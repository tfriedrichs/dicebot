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
import io.github.tfriedrichs.dicebot.selector.ComparisonSelector.Mode;
import io.github.tfriedrichs.dicebot.selector.DiceSelector.DropMode;
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
            buildBraceModifier(builder, die, (DiceExpressionParser.BraceModifierContext) modifier);
        }
        if (modifier instanceof DiceExpressionParser.KeepModifierContext) {
            buildKeepHighModifier(builder, (DiceExpressionParser.KeepModifierContext) modifier);
        }
        if (modifier instanceof DiceExpressionParser.DropHighModifierContext) {
            buildDropHighModifier(builder, (DiceExpressionParser.DropHighModifierContext) modifier);
        }
        if (modifier instanceof DiceExpressionParser.DropLowModifierContext) {
            buildDropLowModifier(builder, (DiceExpressionParser.DropLowModifierContext) modifier);
        }
        if (modifier instanceof DiceExpressionParser.SuccessModifierContext) {
            buildSuccessModifier(builder, (DiceExpressionParser.SuccessModifierContext) modifier);
        }
        if (modifier instanceof DiceExpressionParser.FailureModifierContext) {
            buildFailureModifier(builder, (DiceExpressionParser.FailureModifierContext) modifier);
        }
        if (modifier instanceof DiceExpressionParser.ExplodeModifierContext) {
            buildExplodeModifier(builder, die, (DiceExpressionParser.ExplodeModifierContext) modifier);
        }
        if (modifier instanceof DiceExpressionParser.CompoundModifierContext) {
            buildCompoundModifier(builder, die, (DiceExpressionParser.CompoundModifierContext) modifier);
        }
        if (modifier instanceof DiceExpressionParser.PenetrateModifierContext) {
            buildPenetrateModifier(builder, die, (DiceExpressionParser.PenetrateModifierContext) modifier);
        }
        if (modifier instanceof DiceExpressionParser.CriticalSuccessModifierContext) {
            buildCriticalSuccess(builder, (DiceExpressionParser.CriticalSuccessModifierContext) modifier);
        }
        if (modifier instanceof DiceExpressionParser.CriticalFailureModifierContext) {
            buildCriticalFailure(builder, (DiceExpressionParser.CriticalFailureModifierContext) modifier);
        }

    }

    private void buildBraceModifier(DiceRollExpression.Builder builder, Die die, DiceExpressionParser.BraceModifierContext modifier) {
        addModifier(builder, die, modifier.modifier());
    }

    private void buildKeepHighModifier(DiceRollExpression.Builder builder, DiceExpressionParser.KeepModifierContext modifier) {
        builder.withModifier(new KeepModifier(getSelector(modifier.directionSelector())));
    }

    private void buildDropHighModifier(DiceRollExpression.Builder builder, DiceExpressionParser.DropHighModifierContext modifier) {
        builder.withModifier(
                new DropModifier(new DirectionSelector(DirectionSelector.Direction.HIGH,
                        DropMode.SKIP,
                        getNumber(modifier.NUMBER())
                )));
    }

    private void buildDropLowModifier(DiceRollExpression.Builder builder, DiceExpressionParser.DropLowModifierContext modifier) {
        builder.withModifier(
                new DropModifier(new DirectionSelector(DirectionSelector.Direction.LOW,
                        DropMode.SKIP,
                        getNumber(modifier.NUMBER())
                )));
    }

    private void buildSuccessModifier(DiceRollExpression.Builder builder, DiceExpressionParser.SuccessModifierContext modifier) {
        builder.withModifier(new SuccessModifier(getSelector(
                modifier.comparisonSelector(),
                Mode.GREATER_EQUALS)));
        builder.withEvaluator(new SuccessEvaluator());
    }

    private void buildFailureModifier(DiceRollExpression.Builder builder, DiceExpressionParser.FailureModifierContext modifier) {
        builder.withModifier(new FailureModifier(getSelector(
                modifier.comparisonSelector(),
                Mode.LESSER_EQUALS)));
        builder.withEvaluator(new SuccessFailureEvaluator());
    }

    private void buildExplodeModifier(DiceRollExpression.Builder builder, Die die, DiceExpressionParser.ExplodeModifierContext modifier) {
        ComparisonSelector selector =
                modifier.comparisonSelector()
                        == null ? new ComparisonSelector(Mode.EQUALS,
                        DropMode.SKIP, die.getMax()
                ) :
                        getSelector(modifier
                                .comparisonSelector(), Mode.GREATER_EQUALS);
        builder.withModifier(new ExplodeModifier(MAX_DEPTH, selector));
    }

    private void buildCompoundModifier(DiceRollExpression.Builder builder, Die die, DiceExpressionParser.CompoundModifierContext modifier) {
        ComparisonSelector selector =
                modifier.comparisonSelector()
                        == null ? new ComparisonSelector(Mode.EQUALS,
                        DropMode.SKIP, die.getMax()
                ) :
                        getSelector(modifier
                                .comparisonSelector(), Mode.GREATER_EQUALS);
        builder.withModifier(new CompoundModifier(MAX_DEPTH, selector));
    }

    private void buildPenetrateModifier(DiceRollExpression.Builder builder, Die die, DiceExpressionParser.PenetrateModifierContext modifier) {
        ComparisonSelector selector =
                modifier.comparisonSelector()
                        == null ? new ComparisonSelector(Mode.EQUALS,
                        DropMode.SKIP, die.getMax()
                ) :
                        getSelector(modifier
                                .comparisonSelector(), Mode.GREATER_EQUALS);
        builder.withModifier(new PenetrateModifier(MAX_DEPTH, selector));
    }

    private void buildCriticalSuccess(DiceRollExpression.Builder builder, DiceExpressionParser.CriticalSuccessModifierContext modifier) {
        builder.withModifier(new DiceAnnotator(DiceRoll.MetaData.CRITICAL_SUCCESS, getSelector(
                modifier
                        .comparisonSelector(), Mode.GREATER_EQUALS)));
    }

    private void buildCriticalFailure(DiceRollExpression.Builder builder, DiceExpressionParser.CriticalFailureModifierContext modifier) {
        builder.withModifier(new DiceAnnotator(DiceRoll.MetaData.CRITICAL_FAILURE, getSelector(
                modifier
                        .comparisonSelector(), Mode.LESSER_EQUALS)));
    }

    private ComparisonSelector getSelector(
        DiceExpressionParser.ComparisonSelectorContext comparisonSelector,
        ComparisonSelector.Mode defaultMode) {
        ComparisonSelector.Mode mode;
        if (comparisonSelector.op == null) {
            mode = defaultMode;
        } else if (comparisonSelector.op.getType() == DiceExpressionParser.LESSER) {
            mode = Mode.LESSER;
        } else if (comparisonSelector.op.getType() == DiceExpressionParser.LESSER_EQUALS) {
            mode = Mode.LESSER_EQUALS;
        } else if (comparisonSelector.op.getType() == DiceExpressionParser.EQUALS) {
            mode = Mode.EQUALS;
        } else if (comparisonSelector.op.getType() == DiceExpressionParser.GREATER) {
            mode = Mode.GREATER;
        } else {
            mode = Mode.GREATER_EQUALS;
        }
        return new ComparisonSelector(mode, DropMode.SKIP, getNumber(comparisonSelector.NUMBER()));
    }

    private DirectionSelector getSelector(DiceExpressionParser.DirectionSelectorContext directionSelector) {
        DirectionSelector.Direction direction = directionSelector.direction.getType() == DiceExpressionParser.HIGH ?
                DirectionSelector.Direction.HIGH : DirectionSelector.Direction.LOW;

        return new DirectionSelector(direction, DropMode.SKIP,
            getNumber(directionSelector.NUMBER()));
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
        } else {
            return new Die(randomSource, 100);
        }
    }
}
