package it.unibo.cluedo.model.player.api;

import it.unibo.cluedo.model.card.api.Card;
import it.unibo.cluedo.model.notebook.api.Notebook;
import it.unibo.cluedo.utilities.Position;
import java.io.Serializable;
import java.util.List;


/**
 * Interface wich models a player. Each player has a username, position
 * and a character.
 * The interface provides the methods to get and set information about
 * the player.
 */
public interface Player extends Serializable {

    /**
     * Get the username of a certain player.
     * @return a string which is the username of the player
     */
    String getUsername();

    /**
     * Get the current position of the player.
     * @return the position.
     */
    Position getCurrentPosition();

    /**
     * Cheks if is current player turn.
     * @return a boolean whick indicates if it is current player turn.
     */
    boolean isPlayerTurn();

    /**
     * Get the color of the player.
     * @return a string which is the color of the player
     */
    String getColor();

    /**
     * Checks if the player is in a room.
     * @return a boolean which indicates if a player is in a room.
     */
    boolean isInRoom();

    /**
     * Checks if the player has won.
     * @return a boolean which indicates if a player has won or not.
     */
    boolean hasWon();

    /**
     * Returns the list of cards owned by the player.
     * @return a list of {@link Card} objects representing the cards held by the player
     */
    List<Card> getPlayerCards();

    /**
     * Checks if the player can double roll the dice.
     * @return a boolean which indicates if the player can double roll the dice.
     */
    boolean canDoubleRollDice();

    /**
     * Checks if the player can take the next turn.
     * @return a boolean which indicates if the player can take the next turn.
     */
    boolean canNextTurn();

    /**
     * Get the notebook of the player.
     * @return the notebook
     */
    Notebook getPlayerNotebook();

    /**
     * Get the current steps of the player.
     * @return the number of steps
     */
    int getCurrentSteps();

    /**
     * Checks if the player has lost the game.
     * @return a boolean which indicates the end game for the player condition
     */
    boolean hasLost();

}
