package io.github.tfriedrichs.dicebot.result;

public interface DiceResult {

    int getPrecedence();

    int getValue();

    <T> T accept(DiceResultVisitor<T> visitor);



}
