package io.github.tfriedrichs.dicebot.expression;

import io.github.tfriedrichs.dicebot.operator.RoundingStrategy;
import io.github.tfriedrichs.dicebot.parser.DiceExpressionConverterVisitor;
import io.github.tfriedrichs.dicebot.parser.DiceExpressionLexer;
import io.github.tfriedrichs.dicebot.parser.DiceExpressionParser;
import io.github.tfriedrichs.dicebot.result.DiceResult;
import io.github.tfriedrichs.dicebot.source.RandomSource;
import io.github.tfriedrichs.dicebot.source.ThreadLocalRandomSource;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 * Interface for parts of a dice expression.
 */
public interface DiceExpression {

    /**
     * Parse a string into a dice expression using the default rounding strategy and random source.
     *
     * @param input the input string
     * @return a dice expression representing the input
     */
    static DiceExpression parse(String input) {
        return parse(RoundingStrategy.DOWN, new ThreadLocalRandomSource(), input);
    }

    /**
     * Parse a string into a dice expression using the default rounding strategy and a given random source.
     *
     * @param randomSource the random source to use
     * @param input        the input string
     * @return a dice expression representing the input
     */
    static DiceExpression parse(RandomSource randomSource, String input) {
        return parse(RoundingStrategy.DOWN, randomSource, input);
    }

    /**
     * Parse a string into a dice expression using a given rounding strategy and the default random source.
     *
     * @param roundingStrategy the rounding strategy
     * @param input            the input string
     * @return a dice expression representing the input
     */
    static DiceExpression parse(RoundingStrategy roundingStrategy, String input) {
        return parse(roundingStrategy, new ThreadLocalRandomSource(), input);
    }

    /**
     * Parse a string into a dice expression using given rounding strategy and random source.
     *
     * @param roundingStrategy the rounding strategy
     * @param randomSource the random source
     * @param input the input string
     * @return a dice expression representing the input
     */
    static DiceExpression parse(RoundingStrategy roundingStrategy, RandomSource randomSource, String input) {
        DiceExpressionLexer lexer = new DiceExpressionLexer(CharStreams.fromString(input));
        DiceExpressionParser parser = new DiceExpressionParser(new CommonTokenStream(lexer));
        DiceExpressionConverterVisitor visitor = new DiceExpressionConverterVisitor(roundingStrategy, randomSource);
        DiceExpressionParser.ParseContext tree = parser.parse();
        return visitor.visit(tree);
    }

    /**
     * Rolls the expression und gets the result.
     *
     * @return the result
     */
    DiceResult roll();

}
