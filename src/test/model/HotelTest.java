package model;

import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class HotelTest {
    private Hotel hotel;
    private Guest guest1;
    private Guest guest2;
    private Guest guest3;
    private List<Guest> testList;

    @BeforeEach
    public void setUp() {
        // Initialize a new Hotel object before each test
        hotel = new Hotel("Hotel1",5, 40, 20);
        // Initialize some Guest objects before each test
        guest1 = new Guest("Thomas Shelby", 123456789, "Suite",
                 "01-01-2024", "02-01-2024");
        guest2 = new Guest("Polly Grey", 987654321, "Single Room",
                "01-01-2024", "02-01-2024");
        guest3 = new Guest("Arthur Shelby", 456123789, "Double Room",
                "01-01-2024", "02-01-2024");
        testList = hotel.getGuestList();
    }

    @Test
    public void testGetName() {
        assertEquals("Hotel1", hotel.getName());
    }

    @Test
    public void testGetCurrentNumberOfSuites() {
        assertEquals(5, hotel.getCurrentNumberOfSuites());
        hotel.completeCheckIn(guest1);
        assertEquals(4, hotel.getCurrentNumberOfSuites());
    }

    @Test
    public void testGetCurrentNumberOfSingleRooms() {
        assertEquals(40, hotel.getCurrentNumberOfSingleRooms());
        hotel.completeCheckIn(guest2);
        assertEquals(39, hotel.getCurrentNumberOfSingleRooms());
    }

    @Test
    public void testGetCurrentNumberOfDoubleRooms() {
        assertEquals(20, hotel.getCurrentNumberOfDoubleRooms());
        hotel.completeCheckIn(guest3);
        assertEquals(19, hotel.getCurrentNumberOfDoubleRooms());
    }

    @Test
    public void testGetGuestList() {
        hotel.getGuestList().add(guest1);
        hotel.getGuestList().add(guest2);
        assertTrue(hotel.getGuestList().contains(guest1));
        assertTrue(hotel.getGuestList().contains(guest2));
        assertFalse(hotel.getGuestList().contains(guest3));
    }

    @Test
    public void testSetList() {
        testList.add(guest1);
        testList.add(guest2);
        testList.add(guest3);
        hotel.setGuestList(testList);
        assertEquals(3, hotel.getGuestList().size());
    }

    @Test
    public void testCompleteCheckInSuite() {
        hotel.completeCheckIn(guest1);
        //assertEquals(Hotel.priceSuite, guest1.getOutstandingBill());
        assertEquals(4, hotel.getCurrentNumberOfSuites());
    }

    @Test
    public void testCompleteCheckInSingleRoom() {
        hotel.completeCheckIn(guest2);
        assertEquals(Hotel.priceSingleRoom, guest2.getOutstandingBill());
        assertEquals(39, hotel.getCurrentNumberOfSingleRooms());
    }

    @Test
    public void testCompleteCheckInDoubleRoom() {
        hotel.completeCheckIn(guest3);
        assertEquals(Hotel.priceDoubleRoom, guest3.getOutstandingBill());
        assertEquals(19, hotel.getCurrentNumberOfDoubleRooms());
    }

    @Test
    public void testCompleteCheckOutWithMiniBarUse() {
        hotel.completeCheckIn(guest3);
        guest3.setOutstandingBill(400);
        guest3.setMinibarUse(true);
        int bill = hotel.completeCheckOut(guest3);
        assertTrue(guest3.getMinibarUse());
        assertEquals(Hotel.priceDoubleRoom + Hotel.priceMiniBarUse + 400, bill);
    }

    @Test
    public void testCompleteCheckOutWithOutMiniBarUse() {
        hotel.completeCheckIn(guest2);
        guest2.setOutstandingBill(450);
        int bill = hotel.completeCheckOut(guest2);
        assertFalse(guest2.getMinibarUse());
        assertEquals(Hotel.priceSingleRoom + 450, bill);
    }

    @Test
    public void testFindGuest() {
        testList.add(guest1);
        testList.add(guest2);
        testList.add(guest3);
        Guest current = hotel.findGuest(testList,123456789);
        assertEquals(current, guest1);
        assertNull(hotel.findGuest(testList,12365478));
    }

    @Test
    public void testMiniBarUse() {
        assertFalse(guest1.getMinibarUse());
        assertTrue(hotel.miniBarUse(guest1));
        guest2.setMinibarUse(true);
        assertFalse(hotel.miniBarUse(guest2));
    }

    @Test
    public void testGetListOfOverStayGuests_NoOverstays() {
        testList.add(guest1);
        testList.add(guest2);
        testList.add(guest3);
        List<Guest> overstayList = hotel.getListOfOverStayGuests();
        assertEquals(0, overstayList.size());
    }

    @Test
    public void testGetListOfOverStayGuests_OneOverstay() {
        testList.add(guest1);
        testList.add(guest2);
        testList.add(guest3);
        guest1.setOverstayStatus(true);
        List<Guest> overstayList = hotel.getListOfOverStayGuests();
        assertEquals(1, overstayList.size());
        assertTrue(overstayList.contains(guest1));
    }

    @Test
    public void testGetListOfOverStayGuests_MultipleOverstays() {
        testList.add(guest1);
        testList.add(guest2);
        testList.add(guest3);
        guest1.setOverstayStatus(true);
        guest2.setOverstayStatus(true);
        List<Guest> overstayList = hotel.getListOfOverStayGuests();
        assertEquals(2, overstayList.size());
        assertTrue(overstayList.contains(guest1));
        assertTrue(overstayList.contains(guest2));
    }
}
