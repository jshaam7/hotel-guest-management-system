package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// constructs a hotel
// contains methods that modify hotel details based on different ui scenarios
public class Hotel implements Writable {
    // all prices are in price(dollars) per night
    public static final int priceSuite = 400;
    public static final int priceSingleRoom = 150;
    public static final int priceDoubleRoom = 250;
    public static final int priceMiniBarUse = 50;


    private int currentNumberOfSuites;
    private int currentNumberOfSingleRooms;
    private int currentNumberOfDoubleRooms;

    private String name;

    private List<Guest> guestList;


    // EFFECTS: instantiates a hotel; with currentNumberOfSuites, currentNumberOfSingleRooms,
    //          currentNumberOfDoubleRooms, and empty guestList
    public Hotel(String name,
                 int currentNumberOfSuites, int currentNumberOfSingleRooms, int currentNumberOfDoubleRooms) {
        this.name = name;
        this.currentNumberOfSuites = currentNumberOfSuites;
        this.currentNumberOfSingleRooms = currentNumberOfSingleRooms;
        this.currentNumberOfDoubleRooms = currentNumberOfDoubleRooms;
        this.guestList = new ArrayList<>();

    }

    public String getName() {
        return this.name;
    }

    public int getCurrentNumberOfSuites() {
        return this.currentNumberOfSuites;
    }

    public int getCurrentNumberOfSingleRooms() {
        return this.currentNumberOfSingleRooms;
    }

    public int getCurrentNumberOfDoubleRooms() {
        return this.currentNumberOfDoubleRooms;
    }

    public List<Guest> getGuestList() {
        return this.guestList;
    }

    public void setGuestList(List<Guest> guestList) {
        this.guestList = guestList;
    }


    // MODIFIES: this
    // EFFECTS: sets outstanding bill of guest per room type and
    //          decreases number of rooms available of said room type by 1
    public void completeCheckIn(Guest g) {
        if (g.getRoomType().equals("Suite")) {
            g.setOutstandingBill(priceSuite);
            currentNumberOfSuites--;
        }
        if (g.getRoomType().equals("Single Room")) {
            g.setOutstandingBill(priceSingleRoom);
            currentNumberOfSingleRooms--;
        } else {
            g.setOutstandingBill(priceDoubleRoom);
            currentNumberOfDoubleRooms--;
        }

        EventLog.getInstance().logEvent(new Event("Guest " + g.getName() + " checked in and added to"
                + " guestList"));

        addGuest(g);

    }

    public void addGuest(Guest g) {

        guestList.add(g);
    }

    // REQUIRES: guest exists in guestList
    // MODIFIES: this
    // EFFECTS: checks if guest has used mini bar, sets outstanding bill accordingly
    //          and returns bill owed to hotel by guest
    public int completeCheckOut(Guest g) {
        if (g.getMinibarUse()) {
            g.setOutstandingBill(Hotel.priceMiniBarUse);
        }
        EventLog.getInstance().logEvent(new Event("Guest " + g.getName() + " checked out"));
        return g.generateBill(g);
    }


    // EFFECTS: returns a list of guests who are overstaying at the hotel
    public List<Guest> getListOfOverStayGuests() {
        List<Guest> overstaylist = new ArrayList<>();
        for (Guest g : this.getGuestList()) {
            if (g.getOverstayStatus()) {
                overstaylist.add(g);
            }
        }
        EventLog.getInstance().logEvent(new Event("Viewed overstayed guests"));
        return overstaylist;
    }

    // REQUIRES: list notNull
    // EFFECTS: returns guest if they exist in guestList by identifying using phone number
    public Guest findGuest(List<Guest> list, int t) {
        for (Guest g : list) {
            if (g.getNumber() == t) {
                return g;
            }
        }
        return null;
    }

    // REQUIRES: guest exists in guestList
    // MODIFIES: this
    // EFFECTS: sets minibar use to true if guest has not used mini bar till now
    public boolean miniBarUse(Guest g) {
        if (!g.getMinibarUse()) {
            g.setMinibarUse(true);
            return true;
        }
        return false;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("currentNumberOfSuites", currentNumberOfSuites);
        json.put("currentNumberOfSingleRooms", currentNumberOfSingleRooms);
        json.put("currentNumberOfDoubleRooms", currentNumberOfDoubleRooms);
        json.put("guestList", guestListToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray guestListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Guest g : guestList) {
            jsonArray.put(g.toJson());
        }
        return jsonArray;
    }
}
