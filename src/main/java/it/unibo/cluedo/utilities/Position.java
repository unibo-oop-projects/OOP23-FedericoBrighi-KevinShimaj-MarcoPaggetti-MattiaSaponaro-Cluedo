package it.unibo.cluedo.utilities;

import java.util.List;
import java.util.Random;
import java.io.Serializable;

/**
 * This record model a 2-D position.
 *
 * @param x - x axis coordinate
 * @param y - y axis coordinate
 */
public record Position(int x, int y) implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Random RANDOM = new Random();

    /**
     * Default position 1 (0,9).
     */
    public static final Position DEFAULT_POSITION_1 = new Position(0, 9);

    /**
     * Default position 2 (0,14).
     */
    public static final Position DEFAULT_POSITION_2 = new Position(0, 14);

    /**
     * Default position 3 (6,23).
     */
    public static final Position DEFAULT_POSITION_3 = new Position(6, 23);

    /**
     * Default position 4 (19,23).
     */
    public static final Position DEFAULT_POSITION_4 = new Position(19, 23);

    /**
     * Default position 5 (24,7).
     */
    public static final Position DEFAULT_POSITION_5 = new Position(24, 7);

    /**
     * Default position 6 (17,0).
     */
    public static final Position DEAFULT_POSITION_6 = new Position(17, 0);

    /**
     * Method to get the x coordinate.
     * @return - x coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * Method to get te y coordinate.
     * @return - y coordinate
     */
    public int getY() {
        return this.y;
    }

    /**
     * Method to get the default position of the players.
     * @return a list of Position
     */
    public static List<Position> getDefaultPositions() {
        return List.of(
                DEFAULT_POSITION_1,
                DEFAULT_POSITION_2,
                DEFAULT_POSITION_3,
                DEFAULT_POSITION_4,
                DEFAULT_POSITION_5,
                DEAFULT_POSITION_6);
    }

    /**
     * Method to get a random default position of the player.
     * @return a random default Position
     */
    public static Position getRandomDefaultPosition() {
        final List<Position> defaultPositions = getDefaultPositions();
        return defaultPositions.get(RANDOM.nextInt(defaultPositions.size()));
    }

}
