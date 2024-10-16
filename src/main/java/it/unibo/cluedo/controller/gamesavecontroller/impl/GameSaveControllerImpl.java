package it.unibo.cluedo.controller.gamesavecontroller.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import it.unibo.cluedo.controller.gamesavecontroller.api.GameSaveController;
import it.unibo.cluedo.model.player.api.Player;
import it.unibo.cluedo.model.statistics.api.Statistics;
import it.unibo.cluedo.model.turnmanager.api.TurnManager;
import it.unibo.cluedo.model.turnmanager.impl.TurnManagerImpl;
import it.unibo.cluedo.utilities.TurnFase;
import it.unibo.cluedo.model.board.api.Board;
import it.unibo.cluedo.model.card.api.Card;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;


/**
 * Class that implements the GameSaveManager interface.
 */
public final class GameSaveControllerImpl implements GameSaveController {

    private static final String SAVE_FILE_PATH = System.getProperty("user.home") + "/saved_game.ser";

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveGame(final List<Player> players, final Set<Card> solution, final TurnManager turnManager,
        final Statistics statistics, final Board map, final Set<Card> allCards, final TurnFase turnFase, final String path) {
        if (path.isEmpty()) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE_PATH))) {
                final GameState gameState = new GameState(players, solution, turnManager, statistics, map, allCards, turnFase);
                out.writeObject(gameState);
            } catch (IOException e) {
                Logger.getLogger(GameSaveControllerImpl.class.getName()).log(Level.SEVERE, "Error while saving the game", e);
            }
        } else {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
                final GameState gameState = new GameState(players, solution, turnManager, statistics, map, allCards, turnFase);
                out.writeObject(gameState);
            } catch (IOException e) {
                Logger.getLogger(GameSaveControllerImpl.class.getName()).log(Level.SEVERE, "Error while saving the game", e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<GameState> loadGame(final String path) {
        final File file;
        if (path.isEmpty()) {
            file = new File(SAVE_FILE_PATH);
        } else {
            file = new File(path);
        }
        if (!file.exists()) {
            return Optional.empty();
        }
        if (path.isEmpty()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVE_FILE_PATH))) {
                final GameState gameState = (GameState) in.readObject();
                return Optional.of(gameState);
            } catch (ClassNotFoundException | IOException e) {
                Logger.getLogger(GameSaveControllerImpl.class.getName()).
                log(Level.SEVERE, "Problems during the saved game loading", e);
                return Optional.empty();
            }
        } else {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {
                final GameState gameState = (GameState) in.readObject();
                return Optional.of(gameState);
            } catch (ClassNotFoundException | IOException e) {
                Logger.getLogger(GameSaveControllerImpl.class.getName()).
                log(Level.SEVERE, "Problems during the saved game loading", e);
                return Optional.empty();
            }
        }
    }

    /**
     * Class representing the game state.
     */
    public static class GameState implements Serializable {
        private final List<Player> players;
        private final Set<Card> solution;
        private final TurnManager turnManager;
        private final Statistics statistics;
        private final Board map;
        private final Set<Card> allCards;
        private final TurnFase turnFase;
        private static final long serialVersionUID = 1L;

        /**
         * Constructs a new GameState object.
         *
         * @param players the list of players.
         * @param solution the solution of the game.
         * @param turnManager the turn manager.
         * @param statistics the statistics.
         * @param map the board.
         * @param allCards the set of all cards.
         * @param turnFase the turn fase.
         */
        public GameState(final List<Player> players, final Set<Card> solution, final TurnManager turnManager,
            final Statistics statistics, final Board map, final Set<Card> allCards, final TurnFase turnFase) {
            this.players = new ArrayList<>(players);
            this.solution = new HashSet<>(solution);
            this.turnManager = new TurnManagerImpl(turnManager.getPlayers(), turnManager.getCurrentPlayerIndex(),
                turnManager.isGameFinished());
            this.statistics = statistics;
            this.map = map;
            this.allCards = new HashSet<>(allCards);
            this.turnFase = turnFase;
        }

        /**
         * Get the list of players.
         * @return the list of players.
         */
        public List<Player> getPlayers() {
            return new ArrayList<>(players);
        }

        /**
         * Get the solution of the game.
         * @return the solution of the game.
         */
        public Set<Card> getSolution() {
            return new HashSet<>(solution);
        }

        /**
         * Get the turn manager.
         * @return the turn manager.
         */
        public TurnManager getTurnManager() {
            return new TurnManagerImpl(
                this.turnManager.getPlayers(),
                this.turnManager.getCurrentPlayerIndex(),
                this.turnManager.isGameFinished()
            );
        }

        /**
         * Get the statistics.
         * @return the statistics.
         */
        public Statistics getStatistics() {
            return statistics;
        }

        /**
         * Get the map.
         * @return the map.
         */
        public Board getMap() {
            return map;
        }

        /**
         * Get the set of all cards.
         * @return the set of all cards.
         */
        public Set<Card> getAllCards() {
            return new HashSet<>(allCards);
        }

        /**
         * Get the turn fase.
         * @return the turn fase.
         */
        public TurnFase getTurnFase() {
            return turnFase;
        }
    }
}
