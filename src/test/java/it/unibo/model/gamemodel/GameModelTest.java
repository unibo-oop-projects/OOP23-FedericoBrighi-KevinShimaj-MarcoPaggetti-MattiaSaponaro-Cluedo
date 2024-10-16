package it.unibo.model.gamemodel;

import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.cluedo.model.GameModel;
import it.unibo.cluedo.model.GameModelBuilder;
import it.unibo.cluedo.model.GameModelBuilderImpl;
import it.unibo.cluedo.model.movement.api.MovementStrategy;
import it.unibo.cluedo.model.player.api.MutablePlayer;
import it.unibo.cluedo.utilities.TurnFase;

/**
 * Test class for the GameModelImpl class.
 */
final class GameModelTest {

    private static final int MAX_DICE_RESULT = 6;

    private GameModel gameModel;

    /**
     * Sets up the game model.
     */
    @BeforeEach
    void setUp() {
        final GameModelBuilder builder = new GameModelBuilderImpl();
        builder.addPlayer("player1", "red");
        builder.addPlayer("player2", "blue");
        builder.addPlayer("player3", "green");
        builder.withGameSolution();
        gameModel = builder.build();
    }

    /**
     * Tests the rollDice method and its exceptions.
     */
    @Test
    void testRollDice() {
        assertEquals(gameModel.getTurnFase(), TurnFase.ROLL_DICE);
        gameModel.rollDice();
        assertTrue(gameModel.getDiceResult() > 0
            && gameModel.getDiceResult() <= MAX_DICE_RESULT);
        assertEquals(gameModel.getTurnFase(), TurnFase.DRAW_UNFORESEEN);
        gameModel.endTurn();
        if (gameModel.getCurrentPlayer() instanceof MutablePlayer) {
            ((MutablePlayer) gameModel.getCurrentPlayer()).setDoubleRollDice(true);
        }
        gameModel.rollDice();
        assertTrue(gameModel.getDiceResult() > 0
            && gameModel.getDiceResult() <= MAX_DICE_RESULT);
        assertEquals(gameModel.getTurnFase(), TurnFase.MOVE_PLAYER);
        assertThrows(IllegalStateException.class, () -> gameModel.rollDice());
    }

    /**
     * Tests the currect succession of the players.
     */
    @Test
    void testEndTurn() {
        assertEquals(gameModel.getTurnFase(), TurnFase.ROLL_DICE);
        assertEquals(gameModel.getCurrentPlayer().getUsername(), "player1");
        gameModel.endTurn();
        assertEquals(gameModel.getTurnFase(), TurnFase.ROLL_DICE);
        assertEquals(gameModel.getCurrentPlayer().getUsername(), "player2");
        gameModel.endTurn();
        gameModel.endTurn();
        assertEquals(gameModel.getTurnFase(), TurnFase.ROLL_DICE);
        assertEquals(gameModel.getCurrentPlayer().getUsername(), "player1");
    }

    /**
     * Tests the drawUnforeseen method.
     */
    @Test
    void testDrawUnforeseen() {
        assertThrows(IllegalStateException.class, () -> gameModel.drawUnforeseen());
        gameModel.rollDice();
        gameModel.drawUnforeseen();
        assertTrue(gameModel.getTurnFase().equals(TurnFase.MOVE_PLAYER)
            || gameModel.getTurnFase().equals(TurnFase.ROLL_DICE));
    }

    /**
     * Tests the isOver method.
     */
    @Test
    void testIsOver() {
        assertFalse(gameModel.isOver());
        if (gameModel.getCurrentPlayer() instanceof MutablePlayer) {
            ((MutablePlayer) gameModel.getCurrentPlayer()).setHasWon(true);
        }
        assertTrue(gameModel.isOver());
        if (gameModel.getCurrentPlayer() instanceof MutablePlayer) {
            ((MutablePlayer) gameModel.getCurrentPlayer()).setHasWon(false);
        }
        gameModel.getPlayers().forEach(player -> {
            if (player instanceof MutablePlayer) {
                ((MutablePlayer) player).setHasLost(true);
            }
        });
        assertTrue(gameModel.isOver());
    }

    /**
     * Tests the exception thrown by the useTrapdoor method.
     */
    @Test
    void testuseTrapdoor() {
        assertThrows(NoSuchElementException.class, () -> gameModel
            .useTrapdoor(gameModel
                .getMap()
                .getRoomBySquare(gameModel
                    .getSquare())
                .get()
            )
        );
    }

    /**
     * Tests the exception thrown by the movePlayer method.
     */
    @Test
    void testMovePlayer() {
        assertThrows(IllegalStateException.class, () -> gameModel.movePlayer(MovementStrategy.Direction.UP));
        gameModel.rollDice();
        assertThrows(IllegalStateException.class, () -> gameModel.movePlayer(MovementStrategy.Direction.UP));
    }
}
