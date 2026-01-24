package persistence;

import model.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


// Citation: Used code blocks from WorkRoom sample provided
public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Hotel hotel = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyHotel() {
        JsonReader reader = new JsonReader("./data/emptyHotel.json");
        try {
            Hotel hotel = reader.read();
            assertEquals("Hotel Empty", hotel.getName());
            assertEquals(0, hotel.getGuestList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderNonEmptyHotel() {
        JsonReader reader = new JsonReader("./data/nonEmptyHotel.json");
        try {
            Hotel hotel = reader.read();
            assertEquals("Hotel Not Empty", hotel.getName());
            List<Guest> guestList = hotel.getGuestList();
            assertEquals(2, guestList.size());
            checkGuest("Grace Shelby", 6662379, "Suite", "2024-03-05",
                    "2024-03-12", guestList.get(0));
            checkGuest("Alfie Simmons", 5552379, "Suite", "2024-03-05",
                    "2024-03-12", guestList.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
