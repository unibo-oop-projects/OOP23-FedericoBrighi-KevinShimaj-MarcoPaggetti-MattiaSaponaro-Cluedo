package it.unibo.model.gamemodelbuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.cluedo.model.GameModel;
import it.unibo.cluedo.model.GameModelBuilder;
import it.unibo.cluedo.model.GameModelBuilderImpl;
import it.unibo.cluedo.model.board.api.Board;
import it.unibo.cluedo.model.board.impl.BoardImpl;
import it.unibo.cluedo.model.card.api.Card;
import it.unibo.cluedo.model.deck.api.Deck;
import it.unibo.cluedo.model.deck.impl.DeckImpl;
import it.unibo.cluedo.model.player.api.Player;
import it.unibo.cluedo.model.player.api.SimplePlayerFactory;
import it.unibo.cluedo.model.player.impl.SimplePlayerFactoryImpl;
import it.unibo.cluedo.model.statistics.api.Statistics;
import it.unibo.cluedo.model.statistics.impl.StatisticsImpl;
import it.unibo.cluedo.model.turnmanager.api.TurnManager;
import it.unibo.cluedo.model.turnmanager.impl.TurnManagerImpl;
import it.unibo.cluedo.utilities.TurnFase;
/**
 * Test class for a {@link GameModelBuilderImpl} class.
 */
final class GameModelBuilderImplTest {

    private GameModelBuilder builder;
    private static final String PLAYER_1 = "Player1";
    private static final String PLAYER_2 = "Player2";
    private static final String PLAYER_3 = "Player3";
    private static final String COLOR_RED = "Red";
    private static final String COLOR_GREEN = "Green";
    private static final String COLOR_BLACK = "Black";
    private Set<Card> solution;
    private Set<Card> allCards;
    private Board map;
    private Statistics statistics;
    private TurnManager turnManager;
    private TurnFase fase;
    private final SimplePlayerFactory playerFactory = new SimplePlayerFactoryImpl();
    /**
     * This is done before each test.
     */
    @BeforeEach
    void setUp() {
        final Deck deck = new DeckImpl();
        this.builder = new GameModelBuilderImpl();
        this.solution = deck.drawSolution();
        this.allCards = deck.getAllCards();
        this.map = new BoardImpl();
        this.fase = TurnFase.END_TURN;
        final List<Player> players = new ArrayList<>();
        players.add(this.playerFactory.createPlayer(PLAYER_1, COLOR_BLACK));
        this.turnManager = new TurnManagerImpl(players);
        this.statistics = new StatisticsImpl(players);
    }

    /*
     * Test that the builder added the correct number of players.
     */
    @Test
    void testCorrectNumberOfPlayers() {
        builder.addPlayer(PLAYER_1, COLOR_RED);
        builder.addPlayer(PLAYER_2, COLOR_GREEN);
        builder.addPlayer(PLAYER_3, COLOR_BLACK);
        builder.withGameSolution();
        final GameModel model = builder.build();
        assertEquals(3, model.getPlayers().size());
    }

    /**
     * Test that the builder throws an exception when the game solution is not set.
     */
    @Test
    void testBuildingWithoutSettingSolution() {
        builder.addPlayer(PLAYER_1, COLOR_RED);
        builder.addPlayer(PLAYER_2, COLOR_GREEN);
        builder.addPlayer(PLAYER_3, COLOR_BLACK);
        assertThrows(IllegalStateException.class, 
        () -> { 
            builder.build(); 
        }, "Building the model without setting the solution should throw an exception");
    }

    /**
     * Test the game model initialization.
     */
    @Test
    void testGameModelInitialization() {
        builder.addPlayer(PLAYER_1, COLOR_RED);
        builder.addPlayer(PLAYER_2, COLOR_GREEN);
        builder.addPlayer(PLAYER_3, COLOR_BLACK);
        builder.withGameSolution();
        final GameModel model = builder.build();
        assertEquals(3, model.getPlayers().size());
        assertEquals(3, model.getSolution().size());
    }

    /**
     * Test building a saved game model.
     */
    @Test
    void testBuildSavedGameModel() {
        builder.addPlayer(PLAYER_1, COLOR_RED)
               .addPlayer(PLAYER_2, COLOR_BLACK)
               .addPlayer(PLAYER_3, COLOR_GREEN)
               .withSavedSolution(solution)
               .withTurnManager(turnManager)
               .withStatistics(statistics)
               .withMap(map)
               .withAllCards(allCards)
               .withTurnFase(fase);
        final GameModel model = builder.buildsaved();
        assertEquals(3, model.getPlayers().size());
        assertEquals(solution, model.getSolution());
        assertEquals(allCards, model.getAllCards());
        assertEquals(map, model.getMap());
        assertEquals(statistics, model.getStatistics());
        assertEquals(turnManager.getPlayers(), model.getTurnManager().getPlayers());
        assertEquals(TurnFase.END_TURN, model.getTurnFase());
    }
}
