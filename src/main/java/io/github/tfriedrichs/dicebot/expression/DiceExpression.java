package io.github.tfriedrichs.dicebot.expression;

import io.github.tfriedrichs.dicebot.operator.RoundingStrategy;
import io.github.tfriedrichs.dicebot.parser.DiceExpressionConverterVisitor;
import io.github.tfriedrichs.dicebot.parser.DiceExpressionLexer;
import io.github.tfriedrichs.dicebot.parser.DiceExpressionParser;
import io.github.tfriedrichs.dicebot.result.DiceResult;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;


public interface DiceExpression {

    DiceResult roll();

    static DiceExpression parse(String input) {
        return parse(RoundingStrategy.DOWN, input);
    }

    static DiceExpression parse(RoundingStrategy roundingStrategy, String input) {
        DiceExpressionLexer lexer = new DiceExpressionLexer(CharStreams.fromString(input));
        DiceExpressionParser parser = new DiceExpressionParser(new CommonTokenStream(lexer));
        DiceExpressionConverterVisitor visitor = new DiceExpressionConverterVisitor(RoundingStrategy.DOWN);
        DiceExpressionParser.ParseContext tree = parser.parse();
        return visitor.visit(tree);
    }

}
