package it.unibo.model.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.cluedo.model.map.api.Map;
import it.unibo.cluedo.model.map.impl.MapImpl;
import it.unibo.cluedo.model.room.api.Room;
import it.unibo.cluedo.model.square.impl.BonusEffectImpl;
import it.unibo.cluedo.model.square.impl.MalusEffectImpl;

final class MapTest {
    private static final int NUM_OF_ROOMS = 10;
    private static final int NUM_ROOMS_WITH_TRAPDOOR = 4;
    private static final int NUM_SQUARES_WITH_EFFECT_PER_TYPE = 3;
    private static final List<String> ROOM_NAMES = List.of(
        "Kitchen", "Ballroom", "Conservatory", "Billiard room",
        "Library", "Study", "Hall", "Lounge", "Dining room",
        "Cluedo"
    );
    private static final List<String> ROOMS_WITH_TRAPDOOR = List.of(
        "Kitchen", "Conservatory", "Study", "Lounge"
    );
    private Map map;

    @BeforeEach
    void setUp() {
        this.map = new MapImpl();
    }

    @Test
    void testMapInitialization() {
        assertNotNull(this.map.getMap(), "Shouldn't be null");
        assertNotNull(this.map.getVisitor(), "Visitor shouldn't be null");
        assertEquals(map.getVisitor().getVisitedRoom().size(), NUM_OF_ROOMS);
    }

    @Test
    void testMapRooms() {
        for (String roomName : ROOM_NAMES) {
            assertTrue(
                map.getVisitor().getVisitedRoom().stream()
                    .anyMatch(room -> room.getName().equals(roomName)),
                    roomName + " should be present"
            );
        }
        for (Room room : map.getVisitor().getVisitedRoom()) {
            assertFalse(room.getEntrances().isEmpty());
            assertFalse(room.getSquares().isEmpty());
        }
        assertEquals(
            this.map.getVisitor().getVisitedRoom().stream()
                .filter(Room::hasTrapDoor)
                .count(),
            NUM_ROOMS_WITH_TRAPDOOR
        );
        for (String roomName : ROOMS_WITH_TRAPDOOR) {
            assertTrue(
                map.getVisitor().getVisitedRoom().stream()
                    .anyMatch(room -> room.getName().equals(roomName) && room.hasTrapDoor()),
                    roomName + " should be present"
            );
        }
    }

    @Test
    void testMapSquares() {
        assertEquals(
            NUM_SQUARES_WITH_EFFECT_PER_TYPE, 
            map.getVisitor().getVisitedSquare().stream()
                .filter(square -> square.getEffect() instanceof BonusEffectImpl)
                .count()
        );
        assertEquals(
            NUM_SQUARES_WITH_EFFECT_PER_TYPE, 
            map.getVisitor().getVisitedSquare().stream()
                .filter(square -> square.getEffect() instanceof MalusEffectImpl)
                .count()
        );
    }
}
