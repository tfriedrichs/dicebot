package io.github.tfriedrichs.dicebot.expression;

import io.github.tfriedrichs.dicebot.DiceRoll;
import io.github.tfriedrichs.dicebot.evaluator.DiceRollEvaluator;
import io.github.tfriedrichs.dicebot.modifier.DiceRollModifier;
import io.github.tfriedrichs.dicebot.source.RandomSource;
import java.util.List;

public class DiceRollExpression implements DiceExpression {

    private final RandomSource randomSource;
    private final DiceExpression numberOfDice;
    private final DiceExpression numberOfSides;
    private final List<DiceRollModifier> modifiers;
    private final DiceRollEvaluator evaluator;

    public DiceRollExpression(RandomSource randomSource,
        DiceExpression numberOfDice,
        DiceExpression numberOfSides,
        List<DiceRollModifier> modifiers,
        DiceRollEvaluator evaluator) {
        this.randomSource = randomSource;
        this.numberOfDice = numberOfDice;
        this.numberOfSides = numberOfSides;
        this.modifiers = modifiers;
        this.evaluator = evaluator;
    }

    @Override
    public DiceRoll roll() {
        int numberOfDice = this.numberOfDice.roll().evaluate();
        int numberOfSides = this.numberOfSides.roll().evaluate();
        int[] rolls = randomSource.get(numberOfDice, 1,
            numberOfSides + 1).toArray();
        for (DiceRollModifier modifier : modifiers) {
            rolls = modifier.modifyRoll(rolls, 1, numberOfSides + 1);
        }
        return new DiceRoll(evaluator, rolls);
    }
}
