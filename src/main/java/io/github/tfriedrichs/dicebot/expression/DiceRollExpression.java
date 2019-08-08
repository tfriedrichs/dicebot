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

/**
 * {@link DiceExpression} for representing the roll of a die or dice pool. It consists of a number of dice to roll,
 * the type of die to roll, a number of modifiers and an evaluator.
 * <p>
 * To calculate the result of a {@link #roll()} the expression uses the given die instance to assign values to each rolled die.
 * Then the modifiers are applied in order.
 */
public class DiceRollExpression implements DiceExpression {

    private final DiceExpression numberOfDice;
    private final Die die;
    private final List<DiceRollModifier> modifiers;
    private final DiceRollEvaluator evaluator;

    /**
     * Constructor.
     *
     * @param numberOfDice a dice expression which evaluates to the number of dice to roll.
     * @param die          the type of die to roll
     * @param modifiers    a list of modifiers
     * @param evaluator    the evaluator
     */
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
        int numberOfDiceToRoll = this.numberOfDice.roll().getValue();
        if (numberOfDiceToRoll < 0) {
            throw new IllegalArgumentException("Number of dice must not be negative");
        }
        int[] rolls = die.roll(numberOfDiceToRoll).toArray();
        DiceRoll result = new DiceRoll(rolls);
        for (DiceRollModifier modifier : modifiers) {
            result = modifier.modifyRoll(result, die);
        }
        return new DiceRollResult(evaluator.evaluate(result), result);
    }

    /**
     * Fluent interface builder for easier construction of {@link DiceRollExpression} instances.
     * <p>
     * It provides sensible default values for each unset attribute:
     * <ul>
     * <li>Number of dice: 1</li>
     * <li>Die type: D6</li>
     * <li>No modifiers</li>
     * <li>Sum evaluator</li>
     * </ul>
     */
    public static class Builder {

        private DiceExpression numberOfDice = new NumberExpression(1);
        private Die die = Die.D6;
        private final List<DiceRollModifier> modifiers = new ArrayList<>();
        private DiceRollEvaluator evaluator = new SumEvaluator();


        /**
         * Sets the number of dice to roll to a {@link DiceExpression}.
         *
         * @param numberOfDice the expression representing the number of dice
         * @return the builder instance for chaining
         */
        public Builder withNumberOfDice(DiceExpression numberOfDice) {
            this.numberOfDice = numberOfDice;
            return this;
        }

        /**
         * Sets the number of dice to a given number.
         *
         * @param numberOfDice the number of dice
         * @return the builder instance for chaining
         */
        public Builder withNumberOfDice(int numberOfDice) {
            this.numberOfDice = new NumberExpression(numberOfDice);
            return this;
        }

        /**
         * Sets the die type.
         *
         * @param die the die type
         * @return the builder instance for chaining
         */
        public Builder withDie(Die die) {
            this.die = die;
            return this;
        }

        /**
         * Adds a single modifier to the modifiers.
         * Successive calls to this method append the modifier to the end of the modifier list.
         *
         * @param modifier the modifier to add
         * @return the builder instance for chaining
         */
        public Builder withModifier(DiceRollModifier modifier) {
            this.modifiers.add(modifier);
            return this;
        }

        /**
         * Sets the evaluator.
         *
         * @param evaluator the evaluator
         * @return the builder instance for chaining
         */
        public Builder withEvaluator(DiceRollEvaluator evaluator) {
            this.evaluator = evaluator;
            return this;
        }

        /**
         * Constructs the expression.
         *
         * @return the constructed expression
         */
        public DiceRollExpression build() {
            return new DiceRollExpression(numberOfDice, die, modifiers,
                    evaluator);
        }

    }
}
