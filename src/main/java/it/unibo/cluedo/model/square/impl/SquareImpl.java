package it.unibo.cluedo.model.square.impl;

import it.unibo.cluedo.model.player.api.Player;
import it.unibo.cluedo.model.square.api.Square;
import it.unibo.cluedo.model.square.api.Effect;
import it.unibo.cluedo.utilities.Position;

import java.util.Optional;

/**
 * Implementation of the Square interface.
 * Represents a square on the Cluedo map, with a specific position and effect.
 */
public class SquareImpl implements Square {
    private static final long serialVersionUID = 1L;
    private final Position position;
    private final Effect effect;
    private Player effectedPlayer;
    private Player player;
    private boolean isForEntrance;

    /**
     * Constructor for the square implementation.
     * 
     * @param position the position of the square
     * @param effect the effect of the square
     */
    public SquareImpl(final Position position, final Effect effect) {
        this.position = position; 
        this.effect = effect;
        this.player = null;
        this.effectedPlayer = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getPosition() {
        return this.position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void landOn(final Player player) {
        if (this.effectedPlayer == null || !this.effectedPlayer.equals(player)) {
            this.effectedPlayer = player;
            this.effect.apply(player);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Effect getEffect() {
        return this.effect;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePlayer() {
        this.player = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlreadyOccupied() {
        return this.player != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlayer(final Player player) {
        this.player = player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIsForEntrance() {
        this.isForEntrance = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Player> getPlayer() {
        return Optional.ofNullable(this.player); 
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isForEntrance() {
        return this.isForEntrance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Player> getEffectedPlayer() {
        return Optional.ofNullable(this.effectedPlayer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetEffectedPlayer() {
        this.effectedPlayer = null;
    }
}
