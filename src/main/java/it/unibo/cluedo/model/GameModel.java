package it.unibo.cluedo.model;

import it.unibo.cluedo.model.card.api.Card;
import it.unibo.cluedo.model.notebook.api.Notebook;
import it.unibo.cluedo.model.player.api.Player;
import it.unibo.cluedo.model.room.api.Room;
import it.unibo.cluedo.model.square.api.Effect;
import it.unibo.cluedo.model.square.api.Square;
import it.unibo.cluedo.model.statistics.api.Statistics;
import it.unibo.cluedo.model.turnmanager.api.TurnManager;
import it.unibo.cluedo.model.unforeseen.api.UnforeseenEffect;
import it.unibo.cluedo.utilities.TurnFase;
import it.unibo.cluedo.model.movement.api.MovementStrategy;
import it.unibo.cluedo.model.board.api.Board;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Interface to represent the model of the Cluedo game.
 */
public interface GameModel {
    /**
     * Method to use the trapdoor.
     * @param room the room to use the trapdoor in.
     */
    void useTrapdoor(Room room);
    /**
     * Method to draw an unforseen cards.
     * @return the unforseen card drawn.
     */
    UnforeseenEffect drawUnforeseen();
    /**
     * Method to roll the dice.
     * @return the result of the dice roll.
     */
    int rollDice();
    /**
     * Move the player to the given position.
     * @param direction the direction to move the player in.
     */
    void movePlayer(MovementStrategy.Direction direction);
    /**
     * method to make an accusation.
     * @param weapon the weapon of the accusation.
     * @param room the room of the accusation.
     * @param character the character of the accusation.
     * @param roomPosition the room the player is in.
     * @return true if the accusation is correct, false otherwise.
     */
    Optional<Card> makeAccusation(Card weapon, Card room, Card character, Room roomPosition);
    /**
     * Method to make the final accusation.
     * @param weapon the weapon of the accusation.
     * @param room the room of the accusation.
     * @param character the character of the accusation.
     * @param roomPosition the room the player is in.
     * @return true if the accusation is correct, false otherwise.
     */
    boolean makeFinalAccusation(Card weapon, Card room, Card character, Room roomPosition);
    /**
     * Method to get the notebook of the current player.
     * @return the notebook of the current player.
     */
    Notebook getNotebook();
    /**
     * Method to get the statistics of the game.
     * @return the statistics of the game.
     */
    Statistics getStatistics();
    /**
     * Method to get the current turn fase.
     * @return the current turn fase.
     */
    TurnFase getTurnFase();
    /**
     * Method to get the result of the dice roll.
     * @return the result of the dice roll.
     */
    int getDiceResult();
    /**
     * Method to end the turn of the current player.
     * @return the next player.
     */
    Player endTurn();
    /**
     * Method to establish if the game is over.
     * @return true if the game is over, false otherwise.
     */
    boolean isOver();
    /**
     * Method to get the players of the game.
     * @return the list of players of the game.
     */
    List<Player> getPlayers();
    /**
     * Method to get the current player.
     * @return the current player.
     */
    Player getCurrentPlayer();
    /**
     * Method to get the effects of the square at the given position.
     * @param position the position of the square.
     * @return the effects of the square.
     */
    Effect getSquareEffects(Square position);
    /**
     * Method to get the solution of the game.
     * @return the solution of the game.
     */
    Set<Card> getSolution();
    /**
     * Method to get the map of the game.
     * @return the map of the game.
     */
    Board getMap();
    /**
     * Method to get the cards of the game.
     * @return the cards of the game.
     */
    Set<Card> getAllCards();
    /**
     * Method to get the square of the player.
     * @return the square of the player.
     */
    Square getSquare();
    /**
     * Method to get the turn manager of the game.
     * @return the turn manager of the game.
     */
    TurnManager getTurnManager();
}
