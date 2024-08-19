package it.unibo.cluedo.model.square.impl;

import it.unibo.cluedo.model.player.api.MutablePlayer;
import it.unibo.cluedo.model.player.api.Player;
import it.unibo.cluedo.model.square.api.Effect;

/**
 * Implementation of Effect interface with pattern Strategy.
 * Representing the Bonus Effect effect.
 */
public class BonusEffectImpl implements Effect {
    private final EffectType type = EffectType.BONUS;

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply(final Player player) {
        final MutablePlayer currentPlayer = (MutablePlayer) player;
        currentPlayer.setDoubleRollDice(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EffectType getType() {
        return this.type;
    }
}
