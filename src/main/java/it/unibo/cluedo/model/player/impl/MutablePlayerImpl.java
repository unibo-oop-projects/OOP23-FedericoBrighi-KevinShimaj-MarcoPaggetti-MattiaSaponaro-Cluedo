package it.unibo.cluedo.model.player.impl;

import it.unibo.cluedo.model.player.api.MutablePlayer;
import it.unibo.cluedo.utilities.Position;
import java.util.List;
import it.unibo.cluedo.model.card.api.Card;

/**
 * Implementation of the {@link MutablePlayer} interface.
 * Represents a mutable player in the game with a username and a color,
 * and allows modification of the player's state.
 */
public class MutablePlayerImpl extends PlayerImpl implements MutablePlayer {
    private static final long serialVersionUID = 1L;
    /**
     * Construct a new mutable player with the given username and color.
     *
     * @param username
     * @param color
     */
    public MutablePlayerImpl(final String username, final String color) {
        super(username, color);
        this.setCurrentPosition(Position.getRandomDefaultPosition());
    }

    /**
     * Moves the player to the given position.
     * @param coords the new position of the player
     */
    @Override
    public void setPosition(final Position coords) {
       super.setCurrentPosition(coords);
    }

    /**
     * Sets the player's turn.
     * @param isTurn true if it's the player's turn, false otherwise
     */
    @Override
    public void setPlayerTurn(final boolean isTurn) {
        super.setPlayerTurn(isTurn);
    }

    /**
     * Sets if the player has won the game.
     * @param hasWon true if the player has won, false otherwise
     */
    @Override
    public void setHasWon(final boolean hasWon) {
        super.setHasWon(hasWon);
    }

    /**
     * Sets if the player is in a room.
     * @param inRoom true if the player is in a room, false otherwise
     */
    @Override
    public void setInRoom(final boolean inRoom) {
        super.setInRoom(inRoom);
    }

    /**
     * Sets if the player can roll the dice again.
     * @param doubleRoll true if the player can roll the dice again, false otherwise
     */
    @Override
    public void setDoubleRollDice(final boolean doubleRoll) {
        super.setDoubleRollDice(doubleRoll);
    }

    /**
     * Sets if the player can take next turn.
     * @param nextTurn true if the player can take next turn, false otherwise
     */
    @Override
    public void setNextTurn(final boolean nextTurn) {
        super.setNextTurn(nextTurn);
    }

    /**
     * Sets the player's cards and logs each card in the notebook.
     * @param cards the list of cards to assign to the player
     */
    @Override
    public void setPlayerCards(final List<Card> cards) {
       super.setPlayerCards(cards);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MutablePlayerImpl clone() {
        try {
            return (MutablePlayerImpl) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Sets the current steps of the player.
     * @param steps the number of steps to set
     */
    @Override
    public void setCurrentSteps(final int steps) {
        super.setCurrentSteps(steps);
    }

    /**
     * Sets wheter the player has lost.
     * @param hasLost true if the player lost the game, false otherwise
     */
    @Override
    public void setHasLost(final boolean hasLost) {
        super.setHasLost(hasLost);
    }
}
