package it.unibo.model.dice;

import it.unibo.cluedo.model.dice.impl.DiceImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the Dice class.
 */
final class DiceImplTest {

    private static final int MIN_SIDES = 1;
    private static final int MAX_SIDES = 6;
    private static final int MAX_ROLLS = 10_000;
    private static final double MIN_PERCENT = 0.8;
    private static final double MAX_PERCENT = 1.2;

    /**
     * Tests the rollDice method by rolling the dice and checking if the result is within the expected bounds.
     */
    @Test
    void testRollDice() {
        final DiceImpl dice = new DiceImpl();
        final int result = dice.rollDice();
        assertTrue(result >= MIN_SIDES && result <= MAX_SIDES, "Result is out of bounds");
    }

    /**
     * Tests the rollDice method by rolling the dice multiple times and checking if the distribution is uniform.
     */
    @Test
    void testRollDistribution() {
        final DiceImpl dice = new DiceImpl();
        int[] counts = new int[MAX_SIDES];
        final int totalRolls = MAX_ROLLS;
        for (int i = 0; i < totalRolls; i++) {
            final int result = dice.rollDice();
            counts[result - 1]++;
        }
        for (final int count : counts) {
            final double expected = (double) totalRolls / MAX_SIDES;
            assertTrue(count > expected * MIN_PERCENT && count < expected * MAX_PERCENT,
                    "Distribution is off for one or more sides");
        }
    }

    /**
     * Tests the rollDice method by rolling the dice multiple times and checking if the result is within the expected bounds.
     */
    @Test
    void testStressRoll() {
        final DiceImpl dice = new DiceImpl();
        for (int i = 0; i < MAX_ROLLS; i++) {
            final int result = dice.rollDice();
            assertTrue(result >= MIN_SIDES && result <= MAX_SIDES, "Stress test failed with out of bounds value");
        }
    }
}
