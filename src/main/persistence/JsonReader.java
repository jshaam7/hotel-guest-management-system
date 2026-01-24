package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


import org.json.*;


// Citation: Used code blocks from WorkRoom sample provided
// Represents a reader that reads hotel from JSON data stored in file
public class JsonReader {

    private final String source;
    private EventLog eventLog;

    // EFFECTS: Constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: Reads Hotel from the file and returns it;
    // throws IOException if an error occurs reading data from file
    public Hotel read() throws IOException {
        String jsonData = readFile(this.source);
        JSONObject jsonObject = new JSONObject(jsonData);
        eventLog.getInstance().logEvent(new Event("Hotel state loaded from file"));
        return parseHotel(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses hotel from JSON object and returns it
    private Hotel parseHotel(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int currentNumberOfSuites = jsonObject.getInt("currentNumberOfSuites");
        int currentNumberOfSingleRooms = jsonObject.getInt("currentNumberOfSingleRooms");
        int currentNumberOfDoubleRooms = jsonObject.getInt("currentNumberOfDoubleRooms");
        Hotel hotel = new Hotel(name, currentNumberOfSuites, currentNumberOfSingleRooms, currentNumberOfDoubleRooms);
        addGuests(hotel, jsonObject);
        return hotel;
    }

    // MODIFIES: hotel
    // EFFECTS: parses guests from JSON object and adds them to hotel
    private void addGuests(Hotel hotel, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("guestList");
        for (Object json : jsonArray) {
            JSONObject nextGuest = (JSONObject) json;
            addGuest(hotel, nextGuest);
        }
    }


    // MODIFIES: hotel
    // EFFECTS: parses guest from JSON object and adds it to hotel
    private void addGuest(Hotel hotel, JSONObject guestJson) {
        String name = guestJson.getString("name");
        int phoneNumber = guestJson.getInt("phoneNumber");
        String roomType = guestJson.getString("roomType");
        String checkIn = guestJson.getString("checkIn");
        String checkOut = guestJson.getString("checkOut");
        int outstandingBill = guestJson.getInt("outstandingBill");
        boolean overstayStatus = guestJson.getBoolean("overstayStatus");
        boolean minibarUse = guestJson.getBoolean("minibarUse");
        Guest guest = new Guest(name, phoneNumber, roomType, checkIn, checkOut);
        guest.setOutstandingBill(outstandingBill);
        guest.setOverstayStatus(overstayStatus);
        guest.setMinibarUse(minibarUse);
        hotel.addGuest(guest);
    }

}
