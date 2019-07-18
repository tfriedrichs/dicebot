package io.github.tfriedrichs.dicebot.modifier;

public interface DiceRollModifier {

    int[] modifyRoll(int[] roll, int min, int max);

}
