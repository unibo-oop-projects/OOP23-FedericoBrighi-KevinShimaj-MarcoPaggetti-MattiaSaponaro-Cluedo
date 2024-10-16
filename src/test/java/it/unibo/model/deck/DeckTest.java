package it.unibo.model.deck;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import it.unibo.cluedo.model.card.api.Card;
import it.unibo.cluedo.model.deck.impl.DeckImpl;

final class DeckTest {
    private static final int TOT_NUM_CARDS = 21;
    private static final int TOT_NUM_CARDS_SOLUTION = 3;
    private static final int TOT_NUM_CHARACTERS = 6;
    private static final int TOT_NUM_WEAPONS = 6;
    private static final int TOT_NUM_ROOMS = 9; 
    private static final int NUM_CARDS_SOLUTION_PER_TYPE = 1;
    private static final int NUM_OF_PLAYERS = 3;
    private DeckImpl deck;

    /**
     * Initialization of the deck before every test.
     */
    @BeforeEach
    void setUp() {
        this.deck = new DeckImpl();
    }

    /**
     * Test that the initialization method creates all the right cards and that
     * assignes the rigth paths.
     */
    @Test
    void testInitialization() {
        assertEquals(
            TOT_NUM_CARDS, 
            deck.getAllCards().size(), 
            "The deck should contain 21 cards after initialization"
        );
        assertEquals(
            TOT_NUM_CHARACTERS, 
            deck.getAllCards().stream()
                .filter(card -> card.getType().equals(Card.Type.CHARACTER))
                .count(),
            "Characters card should be 6 after initialization"
        );
        assertEquals(
            TOT_NUM_WEAPONS, 
            deck.getAllCards().stream()
                .filter(card -> card.getType().equals(Card.Type.WEAPON))
                .count(),
            "Weapons card should be 6 after initialization"
        );
        assertEquals(
            TOT_NUM_ROOMS, 
            deck.getAllCards().stream()
                .filter(card -> card.getType().equals(Card.Type.ROOM))
                .count(),
            "Rooms card should be 6 after initialization"
        );
        deck.getAllCards().forEach(card -> {
            assertNotNull(
                ClassLoader.getSystemResourceAsStream(card.getImagePath())
            );
        });
    } 

    /**
     * Test that all cards name are different.
     */
    @Test
    void testUniqueCardNames() {
        final Set<String> cardNames = deck.getAllCards().stream()
            .map(Card :: getName)
            .collect(Collectors.toSet());
        assertEquals(
            TOT_NUM_CARDS, 
            cardNames.size(), 
            "All cards should be unique"
        );
    }

    /**
     * Test that the solution is created correctly.
     */
    @Test
    void testDrawSolution() {
        final Set<Card> solution = this.deck.drawSolution();
        assertEquals(
            TOT_NUM_CARDS_SOLUTION, 
            solution.size(), 
            "Solution should contain 3 cards"
        );
        assertEquals(
            NUM_CARDS_SOLUTION_PER_TYPE, 
            solution.stream()
                .filter(card -> card.getType().equals(Card.Type.CHARACTER))
                .count(),
            "Solution should contain only 1 character card"
        );
        assertEquals(
            NUM_CARDS_SOLUTION_PER_TYPE, 
            solution.stream()
                .filter(card -> card.getType().equals(Card.Type.WEAPON))
                .count(),
            "Solution should contain only 1 weapon card"
        );
        assertEquals(
            NUM_CARDS_SOLUTION_PER_TYPE, 
            solution.stream()
                .filter(card -> card.getType().equals(Card.Type.ROOM))
                .count(),
            "Solution should contain only 1 room card"
        );
        assertEquals(
            TOT_NUM_CARDS - TOT_NUM_CARDS_SOLUTION, 
            this.deck.getRemainingCards().size(), 
            "After the solution is taken, there should be 18 cards in the deck"
        );
        assertEquals(
            TOT_NUM_CARDS,
            this.deck.getAllCards().size(),
            "After the solution is taken, all cards should remaining in the all card list"
        );
    }

    /**
     * Test that the cards are distributed correctly after creating the solution.
     */
    @Test
    void testDistributeCards() {
        this.deck.drawSolution();
        final Set<Set<Card>> distributedCards = this.deck.distributeCards(NUM_OF_PLAYERS);
        assertEquals(NUM_OF_PLAYERS, distributedCards.size());
        for (final Set<Card> cardSet : distributedCards) {
            assertEquals(
                (TOT_NUM_CARDS - TOT_NUM_CARDS_SOLUTION) / NUM_OF_PLAYERS,
                cardSet.size(), 
                "Every set of cards should have the same number of cards"
            );
        }
    }
}
