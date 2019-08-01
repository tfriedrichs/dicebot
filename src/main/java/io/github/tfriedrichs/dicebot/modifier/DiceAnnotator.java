package io.github.tfriedrichs.dicebot.modifier;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.selector.DiceSelector;
import io.github.tfriedrichs.dicebot.source.Die;

public class DiceAnnotator implements DiceRollModifier {

    private final DiceRoll.MetaData metaData;
    private final DiceSelector selector;

    public DiceAnnotator(DiceRoll.MetaData metaData,
                         DiceSelector selector) {
        this.metaData = metaData;
        this.selector = selector;
    }

    @Override
    public DiceRoll modifyRoll(DiceRoll roll, Die die) {
        DiceRoll result = new DiceRoll(roll);
        selector.select(roll).forEach(index -> result.addMetaDataToRoll(index, metaData));
        return result;
    }
}
