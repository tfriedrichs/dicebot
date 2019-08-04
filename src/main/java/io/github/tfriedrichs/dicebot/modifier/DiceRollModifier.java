package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.source.Die;

/**
 * Interface for modifiers of a {@link DiceRoll}.
 */
public interface DiceRollModifier {

    /**
     * Modify a dice roll.
     * <p>
     * Contrary to the name this should not actually modify the given roll, but rather create a new modified instance.
     *
     * @param roll the roll to modify
     * @param die  the die type which should be used for any successive dice rolls.
     * @return a new instance with the applied modifications
     */
    DiceRoll modifyRoll(DiceRoll roll, Die die);

}
