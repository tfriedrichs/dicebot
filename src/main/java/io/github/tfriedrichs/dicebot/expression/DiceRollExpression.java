package io.github.tfriedrichs.dicebot.expression;

import io.github.tfriedrichs.dicebot.DiceRoll;
import io.github.tfriedrichs.dicebot.evaluator.DiceRollEvaluator;
import io.github.tfriedrichs.dicebot.evaluator.SumEvaluator;
import io.github.tfriedrichs.dicebot.modifier.DiceRollModifier;
import io.github.tfriedrichs.dicebot.source.RandomSource;
import io.github.tfriedrichs.dicebot.source.ThreadLocalRandomSource;
import java.util.ArrayList;
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
        if (numberOfDice < 0) {
            throw new IllegalArgumentException("Number of dice must not be negative");
        }
        int numberOfSides = this.numberOfSides.roll().evaluate();
        if (numberOfSides < 0) {
            throw new IllegalArgumentException("Number of sides must not be negative");
        }
        int[] rolls = randomSource.get(numberOfDice, 1,
            numberOfSides + 1).toArray();
        for (DiceRollModifier modifier : modifiers) {
            rolls = modifier.modifyRoll(rolls, 1, numberOfSides + 1);
        }
        return new DiceRoll(evaluator, rolls);
    }

    public static class Builder {

        private final List<DiceRollModifier> modifiers = new ArrayList<>();
        private RandomSource randomSource = new ThreadLocalRandomSource();
        private DiceExpression numberOfDice = new NumberExpression(1);
        private DiceExpression numberOfSides = new NumberExpression(6);
        private DiceRollEvaluator evaluator = new SumEvaluator();

        public Builder withRandomSource(RandomSource randomSource) {
            this.randomSource = randomSource;
            return this;
        }

        public Builder withNumberOfDice(DiceExpression numberOfDice) {
            this.numberOfDice = numberOfDice;
            return this;
        }

        public Builder withNumberOfSides(DiceExpression numberOfSides) {
            this.numberOfSides = numberOfSides;
            return this;
        }

        public Builder withNumberOfDice(int numberOfDice) {
            this.numberOfDice = new NumberExpression(numberOfDice);
            return this;
        }

        public Builder withNumberOfSides(int numberOfSides) {
            this.numberOfSides = new NumberExpression(numberOfSides);
            return this;
        }

        public Builder withModifier(DiceRollModifier modifier) {
            this.modifiers.add(modifier);
            return this;
        }

        public Builder withEvaluator(DiceRollEvaluator evaluator) {
            this.evaluator = evaluator;
            return this;
        }

        public DiceRollExpression build() {
            return new DiceRollExpression(randomSource, numberOfDice, numberOfSides, modifiers,
                evaluator);
        }

    }
}
