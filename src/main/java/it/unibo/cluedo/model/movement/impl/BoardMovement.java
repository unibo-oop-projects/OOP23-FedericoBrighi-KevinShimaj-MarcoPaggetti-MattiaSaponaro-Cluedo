package it.unibo.cluedo.model.movement.impl;

import it.unibo.cluedo.model.board.api.Board;
import it.unibo.cluedo.model.board.impl.BoardImpl;
import it.unibo.cluedo.model.movement.api.MovementStrategy;
import it.unibo.cluedo.model.player.api.Player;
import it.unibo.cluedo.model.room.api.Room;
import it.unibo.cluedo.utilities.Position;
/**
 * Implementation of the {@link MovementStrategy} interface.
 * This class defines movement rules for players on the game board,
 * including movement calculation, move validation, trapdoor usable and
 * room entrance.
 */
public class BoardMovement implements MovementStrategy {
    private final Board map;
    private final int width;
    private final int heigth;

    /**
     * Constructor for BoardMovement.
     * @param map the map of Cluedo game
     */
    public BoardMovement(final Board map) {
        this.map = map;
        this.width = BoardImpl.getMapWidth();
        this.heigth = BoardImpl.getMapHeight();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position calculatePosition(final Position currentPosition, final int steps, final Direction direction) {
        switch (direction) {
            case UP:
                return new Position(currentPosition.getX() - steps, currentPosition.getY());
            case DOWN:
                return new Position(currentPosition.getX() + steps, currentPosition.getY());
            case RIGHT:
                return new Position(currentPosition.getX(), currentPosition.getY() + steps);
            case LEFT:
                return new Position(currentPosition.getX(), currentPosition.getY() - steps);
            default:
                throw new IllegalStateException("Invalid direction: " + direction);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValidMove(final Player player, final Position newPosition) {
        return newPosition.getX() >= 0 && newPosition.getX() < this.heigth
        && newPosition.getY() >= 0 && newPosition.getY() < this.width
        && !map.getSquareByPosition(newPosition).isAlreadyOccupied();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTrapDoorUsable(final Player player) {
        return map.getRooms().stream()
            .filter(r -> r.isPlayerInRoom(player))
            .filter(Room::hasTrapDoor)
            .map(room -> room.getTrapDoor().get().getConnectedRoom())
            .findAny()
            .isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPlayerEnteredInRoom(final Player player, final Position newPosition) {
        return map.getRooms().stream()
            .anyMatch(room -> room.isEntrance(this.map.getSquareByPosition(newPosition)));
    }
}
