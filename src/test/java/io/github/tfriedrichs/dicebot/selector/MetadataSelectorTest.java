package io.github.tfriedrichs.dicebot.selector;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import io.github.tfriedrichs.dicebot.result.DiceRoll;
import io.github.tfriedrichs.dicebot.selector.DiceSelector.DropMode;
import org.junit.jupiter.api.Test;

class MetadataSelectorTest {

    @Test
    void selectMatchingAll() {
        DiceRoll roll = new DiceRoll(1, 2, 3, 4);
        roll.addMetaDataToRoll(0, DiceRoll.MetaData.DROPPED);
        roll.addMetaDataToRoll(0, DiceRoll.MetaData.ADDED);
        roll.addMetaDataToRoll(1, DiceRoll.MetaData.DROPPED);
        roll.addMetaDataToRoll(1, DiceRoll.MetaData.ADDED);
        roll.addMetaDataToRoll(1, DiceRoll.MetaData.EXPLODED);
        roll.addMetaDataToRoll(2, DiceRoll.MetaData.EXPLODED);

        MetadataSelector selector = new MetadataSelector(MetadataSelector.Mode.ALL, DropMode.IGNORE,
            DiceRoll.MetaData.ADDED, DiceRoll.MetaData.EXPLODED, DiceRoll.MetaData.DROPPED);
        assertArrayEquals(new int[]{1}, selector.select(roll).toArray());
    }

    @Test
    void selectMatchingSome() {
        DiceRoll roll = new DiceRoll(1, 2, 3, 4);
        roll.addMetaDataToRoll(0, DiceRoll.MetaData.DROPPED);
        roll.addMetaDataToRoll(0, DiceRoll.MetaData.ADDED);
        roll.addMetaDataToRoll(1, DiceRoll.MetaData.DROPPED);
        roll.addMetaDataToRoll(1, DiceRoll.MetaData.ADDED);
        roll.addMetaDataToRoll(1, DiceRoll.MetaData.EXPLODED);
        roll.addMetaDataToRoll(2, DiceRoll.MetaData.EXPLODED);

        MetadataSelector selector = new MetadataSelector(MetadataSelector.Mode.SOME,
            DropMode.IGNORE,
            DiceRoll.MetaData.ADDED, DiceRoll.MetaData.EXPLODED, DiceRoll.MetaData.DROPPED);
        assertArrayEquals(new int[]{0, 1, 2}, selector.select(roll).toArray());
    }

    @Test
    void selectMatchingNone() {
        DiceRoll roll = new DiceRoll(1, 2, 3, 4);
        roll.addMetaDataToRoll(0, DiceRoll.MetaData.DROPPED);
        roll.addMetaDataToRoll(0, DiceRoll.MetaData.ADDED);
        roll.addMetaDataToRoll(1, DiceRoll.MetaData.DROPPED);
        roll.addMetaDataToRoll(1, DiceRoll.MetaData.ADDED);
        roll.addMetaDataToRoll(1, DiceRoll.MetaData.EXPLODED);
        roll.addMetaDataToRoll(2, DiceRoll.MetaData.EXPLODED);

        MetadataSelector selector = new MetadataSelector(MetadataSelector.Mode.NONE, DropMode.SKIP,
            DiceRoll.MetaData.ADDED, DiceRoll.MetaData.EXPLODED, DiceRoll.MetaData.DROPPED);
        assertArrayEquals(new int[]{3}, selector.select(roll).toArray());
    }

}