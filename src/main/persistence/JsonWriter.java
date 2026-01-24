package persistence;

import model.*;

import java.io.*;

import org.json.JSONObject;


// Citation: Used code blocks from WorkRoom sample provided
// Represents a writer that writes JSON representation of hotel to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;
    private EventLog eventLog;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of hotel to file
    public void write(Hotel hotel) {
        JSONObject json = hotel.toJson();
        saveToFile(json.toString(TAB));
        eventLog.getInstance().logEvent(new Event("Hotel state saved to file"));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
