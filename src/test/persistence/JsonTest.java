package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkGuest(String name, int phoneNumber, String roomType,
                              String checkIn, String checkOut, Guest guest) {
        assertEquals(name, guest.getName());
        assertEquals(phoneNumber, guest.getNumber());
        assertEquals(roomType, guest.getRoomType());
        assertEquals(checkIn, guest.getCheckIn());
        assertEquals(checkOut, guest.getCheckOut());
    }
}