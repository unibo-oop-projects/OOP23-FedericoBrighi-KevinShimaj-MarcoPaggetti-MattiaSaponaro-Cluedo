package it.unibo.cluedo.model.deck.impl;

import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.Iterator;
import java.nio.file.Paths;
import java.util.Collections;

import it.unibo.cluedo.model.card.api.Card;
import it.unibo.cluedo.model.card.impl.CardFactory;
import it.unibo.cluedo.model.deck.api.Deck;

/**
 * Represents the implementation of the deck of cards in the Cluedo game.
 */
public class DeckImpl implements Deck {
    private static final List<String> CHARACTER_NAMES = List.of(
        "Colonel Mustard", 
        "Miss Scarlet", 
        "Mr Green", 
        "Mrs Peacock", 
        "Mrs White",
        "Professor Plum"
    );
    private static final List<String> WEAPON_NAMES = List.of(
        "Knife", 
        "Lead Pipe", 
        "Revolver", 
        "Rope", 
        "Wrench",
        "Candlestick"
    ); 
    private static final List<String> ROOM_NAMES = List.of(
        "Ballroom", 
        "Billiard room", 
        "Conservatory", 
        "Dining room", 
        "Hall",
        "Kitchen",
        "Library", 
        "Lounge",
        "Study"
    );
    private final Set<Card> cards = new HashSet<>();

    /**
     * Generates the file path for the image corresponding to the given card name.
     * The path is constructed in a way that is compatible with different operating systems.
     * 
     * @param name the name of the card 
     * @return the file path of the image of the card 
     */
    private String getImagePath(final String name) {
        return Paths.get("src", "main", "resources", name.replace(" ", "") + ".PNG").toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeDeck() {
        this.cards.clear();
        CHARACTER_NAMES.forEach(name -> this.cards.add(CardFactory.createCharacterCard(name, getImagePath(name))));
        WEAPON_NAMES.forEach(name -> this.cards.add(CardFactory.createWeaponCard(name, getImagePath(name))));
        ROOM_NAMES.forEach(name -> this.cards.add(CardFactory.createRoomCard(name, getImagePath(name))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Card> drawSolution() {
        final Card solutionCharacter = this.cards.stream()
            .filter(card -> card.getType() == Card.Type.CHARACTER)
            .findAny()
            .orElseThrow(() -> new IllegalStateException("No character card available"));
        final Card solutionWeapon = this.cards.stream()
            .filter(card -> card.getType() == Card.Type.WEAPON)
            .findAny()
            .orElseThrow(() -> new IllegalStateException("No weapon card available"));
        final Card solutionRoom = this.cards.stream()
            .filter(card -> card.getType() == Card.Type.ROOM)
            .findAny()
            .orElseThrow(() -> new IllegalStateException("No room card available"));
        this.cards.removeIf(card -> card.equals(solutionCharacter) || card.equals(solutionWeapon) || card.equals(solutionRoom));
        return Set.of(solutionCharacter, solutionWeapon, solutionRoom);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Set<Card>> distributeCards(final int numberOfPlayers) {
        final Set<Set<Card>> distributedSet = new HashSet<>();
        final Iterator<Card> cardIterator = this.cards.iterator();
        final int numberOfCardsPerPlayer = this.cards.size() / numberOfPlayers;
        for (int i = 0; i < numberOfPlayers; i++) {
            final Set<Card> playerCards = new HashSet<>();
            for (int j = 0; j < numberOfCardsPerPlayer; j++) {
                if (cardIterator.hasNext()) {
                    playerCards.add(cardIterator.next());
                }
            }
            distributedSet.add(playerCards);
        }
        return distributedSet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Card> getAllCards() {
        return Collections.unmodifiableSet(new HashSet<>(this.cards));
    }
}
