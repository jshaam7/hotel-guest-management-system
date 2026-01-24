package persistence;

import model.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Citation: Used code blocks from WorkRoom sample provided
public class JsonWriterTest extends JsonTest {

    private static final String TEST_FILE = "./data/writerTest.json"; // change file path name if running
    // tests on your local machine

    @Test
    void testWriterInvalidFile() {
        try {
            Hotel hotel = new Hotel("Nonexistent", 5,10, 25);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyHotel() {
        try {
            Hotel hotel = new Hotel("Hotel Grand", 5, 10, 25);
            JsonWriter writer = new JsonWriter(TEST_FILE);
            writer.open();
            writer.write(hotel);
            writer.close();

            JsonReader reader = new JsonReader(TEST_FILE);
            hotel = reader.read();
            assertEquals("Hotel Grand", hotel.getName());
            assertEquals(0, hotel.getGuestList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterHotel() {
        try {
            Hotel hotel = new Hotel("Hotel Grand", 5, 10, 25);
            Guest g1 = new Guest("Grace Shelby", 6662379, "Suite", "2024-03-05", "2024-03-12");
            g1.setOutstandingBill(100);
            g1.setOverstayStatus(true);
            g1.setMinibarUse(true);
            hotel.addGuest(g1);
            Guest g2 = new Guest("Alfie Simmons", 5552379, "Suite", "2024-03-05", "2024-03-12");
            g2.setOutstandingBill(200);
            g2.setOverstayStatus(false);
            g2.setMinibarUse(true);
            hotel.addGuest(g2);
            JsonWriter writer = new JsonWriter(TEST_FILE);
            writer.open();
            writer.write(hotel);
            writer.close();

            JsonReader reader = new JsonReader(TEST_FILE);
            hotel = reader.read();
            assertEquals("Hotel Grand", hotel.getName());
            List<Guest> guests = hotel.getGuestList();
            assertEquals(2, guests.size());
            checkGuest("Grace Shelby", 6662379, "Suite", "2024-03-05",
                    "2024-03-12", guests.get(0));
            checkGuest("Alfie Simmons", 5552379, "Suite", "2024-03-05",
                    "2024-03-12", guests.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
