package io.github.tfriedrichs.dicebot.source;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DieTest {

    @Test
    void normalDieShouldBeCorrectlyInitialized() {
        Die die = new Die(new ThreadLocalRandomSource(), 6);
        assertEquals(6, die.getNumberOfSides());
        assertEquals(1, die.getMin());
        assertEquals(6, die.getMax());
    }

    @Test
    void mappedDieShouldBeCorrectlyInitialized() {
        Die die = new Die(new ThreadLocalRandomSource(), List.of(5, 9, 2, 1000, 21));
        assertEquals(5, die.getNumberOfSides());
        assertEquals(2, die.getMin());
        assertEquals(1000, die.getMax());
    }

}