package it.unibo.cluedo.model.turnmanager.impl;

import java.util.ArrayList;
import java.util.List;
import it.unibo.cluedo.model.notebook.api.Notebook;
import it.unibo.cluedo.model.player.api.MutablePlayer;
import it.unibo.cluedo.model.player.api.Player;
import it.unibo.cluedo.model.turnmanager.api.TurnManager;

/**
 * Class that implements the TurnManager interface,
 * it has a constructor that takes the list of players as parameter.
 */
public class TurnManagerImpl implements TurnManager {

    private final List<Player> players;
    private int currentPlayerIndex;
    private boolean gameFinished;
    private static final long serialVersionUID = 1L;

    /**
     * Constructor of the class.
     * @param players the list of the players.
     */
    public TurnManagerImpl(final List<Player> players) {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("The list of players cannot be null or empty");
        }
        this.players = new ArrayList<>(players);
        this.currentPlayerIndex = 0;
        if (this.players.get(currentPlayerIndex) instanceof MutablePlayer) {
            ((MutablePlayer) this.players.get(currentPlayerIndex)).setPlayerTurn(true);
        }
    }

    /**
     * Second Constructor of the class.
     * @param players the list of the players.
     * @param currentPlayerIndex the index of the current player.
     * @param gameFinished a boolean indicating if the game is finished.
     */
    public TurnManagerImpl(final List<Player> players, final int currentPlayerIndex, final boolean gameFinished) {
        this.players = new ArrayList<>(players);
        this.currentPlayerIndex = currentPlayerIndex;
        this.gameFinished = gameFinished;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePlayer(final Player player) {
        if (player == null || !players.contains(player)) {
            throw new IllegalArgumentException("The player cannot be null or not present in the game");
        }
        players.remove(player);
        if (players.isEmpty()) {
            gameFinished = true;
            return;
        }
        this.currentPlayerIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
        if (player instanceof MutablePlayer) {
            ((MutablePlayer) player).setPlayerTurn(false);
        }
        if (players.isEmpty()) {
            gameFinished = true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void switchTurn() {
        if (gameFinished || players.isEmpty()) {
            gameFinished = true;
            return;
        }

        do {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        } while (players.get(currentPlayerIndex).hasWon());

        final MutablePlayer currentPlayer = (MutablePlayer) players.get(currentPlayerIndex);
        currentPlayer.setPlayerTurn(true);
        gameFinished = checkGameEndCondition();
    }

    /**
     * Check if the game has ended.
     * @return a boolean indicating if the game has ended or not.
     */
    private boolean checkGameEndCondition() {
        int remainingPlayers = 0;
        for (final Player player : players) {
            if (!player.hasWon()) {
                remainingPlayers++;
                if (remainingPlayers > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGameFinished() {
        return gameFinished;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Notebook getCurrentNotebook() {
        return getCurrentPlayer().getPlayerNotebook();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }
}
