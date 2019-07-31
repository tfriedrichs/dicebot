package io.github.tfriedrichs.dicebot.selector;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import java.util.stream.IntStream;

public interface DiceSelector {

    IntStream select(DiceRoll roll);

}
