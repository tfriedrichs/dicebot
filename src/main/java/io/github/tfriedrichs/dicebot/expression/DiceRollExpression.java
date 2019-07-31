package io.github.tfriedrichs.dicebot.expression;

import io.github.tfriedrichs.dicebot.evaluator.DiceRollEvaluator;
import io.github.tfriedrichs.dicebot.evaluator.SumEvaluator;
import io.github.tfriedrichs.dicebot.modifier.DiceRollModifier;
import io.github.tfriedrichs.dicebot.result.DiceResult;
import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.result.DiceRollResult;
import io.github.tfriedrichs.dicebot.source.Die;

import java.util.ArrayList;
import java.util.List;

public class DiceRollExpression implements DiceExpression {

    private final DiceExpression numberOfDice;
    private final Die die;
    private final List<DiceRollModifier> modifiers;
    private final DiceRollEvaluator evaluator;

    public DiceRollExpression(DiceExpression numberOfDice,
        Die die,
        List<DiceRollModifier> modifiers,
        DiceRollEvaluator evaluator) {
        this.numberOfDice = numberOfDice;
        this.die = die;
        this.modifiers = modifiers;
        this.evaluator = evaluator;
    }

    @Override
    public DiceResult roll() {
        int numberOfDice = this.numberOfDice.roll().getValue();
        if (numberOfDice < 0) {
            throw new IllegalArgumentException("Number of dice must not be negative");
        }
        int[] rolls = die.roll(numberOfDice).toArray();
        DiceRoll result = new DiceRoll(rolls);
        for (DiceRollModifier modifier : modifiers) {
            result = modifier.modifyRoll(result, die);
        }
        return new DiceRollResult(evaluator.evaluate(result), result);
    }

    public static class Builder {

        private DiceExpression numberOfDice = new NumberExpression(1);
        private Die die = Die.D6;
        private final List<DiceRollModifier> modifiers = new ArrayList<>();
        private DiceRollEvaluator evaluator = new SumEvaluator();


        public Builder withNumberOfDice(DiceExpression numberOfDice) {
            this.numberOfDice = numberOfDice;
            return this;
        }

        public Builder withNumberOfDice(int numberOfDice) {
            this.numberOfDice = new NumberExpression(numberOfDice);
            return this;
        }

        public Builder withDie(Die die) {
            this.die = die;
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
            return new DiceRollExpression(numberOfDice, die, modifiers,
                evaluator);
        }

    }
}
