package it.unibo.cluedo.model.trapdoor.api;

import it.unibo.cluedo.utilities.Position;

/**
 * Interface representing a secret passage in the game of Cluedo.
 */
public interface TrapDoor {
    /**
     * Gets the room connected by the secret passage.
     * 
     * @return the name of the room connected by the secret passage
     */
    String getConnectedRoom();

    /**
     * Gets the position of the secret passage in the map.
     * 
     * @return the position of the secret passage in the map
     */
    Position getPosition();
}
