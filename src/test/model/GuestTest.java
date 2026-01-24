package model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class GuestTest {
    private Guest guest;

    @BeforeEach
    public void setUp() {
        // Initialize a new Guest object before each test
        guest = new Guest("John Hancock", 123456789, "Suite",
                "04-04-2024", "05-05-2024");
    }

    @Test
    public void testGetName() {
        assertEquals("John Hancock", guest.getName());
    }

    @Test
    public void testGetNumber() {
        assertEquals(123456789, guest.getNumber());
    }

    @Test
    public void testGetRoomType() {
        assertEquals("Suite", guest.getRoomType());
    }

    @Test
    public void testGetCheckIn() {
        assertNotNull(guest.getCheckIn());
    }

    @Test
    public void testGetCheckOut() {
        assertNotNull(guest.getCheckOut());
    }

    @Test
    public void testGetOutstandingBill() {
        assertEquals(0, guest.getOutstandingBill());
    }

    @Test
    public void testGetOverstayStatus() {
        assertFalse(guest.getOverstayStatus());
    }

    @Test
    public void testGetMinibarUse() {
        assertFalse(guest.getMinibarUse());
    }

    @Test
    public void testSetName() {
        guest.setName("Jackson Hancock");
        assertEquals("Jackson Hancock", guest.getName());
    }

    @Test
    public void testSetNumber() {
        guest.setNumber(987654321);
        assertEquals(987654321, guest.getNumber());
    }

    @Test
    public void testSetRoomType() {
        guest.setRoomType("Suite");
        assertEquals("Suite", guest.getRoomType());
    }

    @Test
    public void testSetCheckIn() {
        String newCheckIn = "05-04-2024";
        guest.setCheckIn(newCheckIn);
        assertEquals(newCheckIn, guest.getCheckIn());
    }

    @Test
    public void testSetCheckOut() {
        assertEquals("05-05-2024", guest.getCheckOut());
        String newCheckOut = "06-06-2024";
        guest.setCheckOut(newCheckOut);
        assertEquals(newCheckOut, guest.getCheckOut());
    }

    @Test
    public void testSetOutstandingBill() {
        assertEquals(0, guest.getOutstandingBill());
        guest.setOutstandingBill(100);
        assertEquals(100, guest.getOutstandingBill());
    }

    @Test
    public void testSetOverstayStatus() {
        assertFalse(guest.getOverstayStatus());
        guest.setOverstayStatus(true);
        assertTrue(guest.getOverstayStatus());
    }

    @Test
    public void testSetMinibarUse() {
        assertFalse(guest.getMinibarUse());
        guest.setMinibarUse(true);
        assertTrue(guest.getMinibarUse());
    }

    @Test
    public void testOverstayCheck() {
        assertFalse(guest.getOverstayStatus());
        guest.setOverstayStatus(true);
        assertTrue(guest.getOverstayStatus());
    }

    @Test
    public void testGenerateBill() {
        assertEquals(0, guest.generateBill(guest));
        guest.setOutstandingBill(100);
        assertEquals(100, guest.generateBill(guest));
    }

    @Test
    public void testToString() {
        String expected = guest.toString();
        assertEquals(expected, "Guest{name=John Hancock, phoneNumber=123456789, roomType=Suite, " +
                "checkIn=04-04-2024, checkOut=05-05-2024, outstandingBill=0, overstayStatus=false, minibarUse=false}");
    }

}



