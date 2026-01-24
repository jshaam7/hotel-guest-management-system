package ui;

import model.Event;
import model.EventLog;
import model.Guest;
import model.Hotel;

import persistence.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// contains methods that govern ui and console output
public class HotelManagementApp {
    private static final String JSON_STORE = "./data/hotel.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Scanner input;
    private Hotel hotel = new Hotel("Hotel Grand", 5, 40, 50);


    // EFFECTS: runs the application
    public HotelManagementApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runHotelManagement();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runHotelManagement() {
        input = new Scanner(System.in);
        boolean keepGoing = true;
        String command;


        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        quitApplication();
        System.out.println("\nGoodbye!");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tnew -> New Guest In:");
        System.out.println("\tout -> Check Out :");
        System.out.println("\tsetm -> Set Mini Bar Use:");
        System.out.println("\tgenb -> Generate Guest Bill:");
        System.out.println("\tprint -> Return Guest List:");
        System.out.println("\tset -> Set Guest as Over Stayer:");
        System.out.println("\tget -> Get List of Over Stayers:");
        System.out.println("\tload -> Load to a JSON file:");
        System.out.println("\tsave -> Save to a JSON file:");
        System.out.println("\tq -> Quit App:");
    }

    // MODIFIES: this
// EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("new")) {
            checkIn();
        } else if (command.equals("out")) {
            checkOut();
        } else if (command.equals("setm")) {
            miniBarUse();
        } else if (command.equals("genb")) {
            getBill();
        } else if (command.equals("print")) {
            printGuests();
        } else if (command.equals("set")) {
            setOverStayer();
        } else if (command.equals("get")) {
            System.out.println(this.hotel.getListOfOverStayGuests());
        } else if (command.equals("load")) {
            loadHotel();
        } else if (command.equals("save")) {
            saveHotel();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // REQUIRES: guest does not exist in hotel guestList
    // MODIFIES: this
    // EFFECTS: checks guest into the hotel and adds guest to the guestList
    public void checkIn() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter guest name:");
        String name = scanner.nextLine();

        System.out.println("Enter guest phone number:");
        int phoneNumber = scanner.nextInt();

        scanner.nextLine();

        System.out.println("Enter room type(Suite/Single Room/Double Room):");
        String roomType = scanner.nextLine();

        System.out.println("Enter check-in date dd-MM-yyyy:");
        String checkInDate = scanner.nextLine();

        System.out.println("Enter check-out date dd-MM-yyyy:");
        String checkOutDate = scanner.nextLine();


        Guest g = new Guest(name, phoneNumber, roomType, checkInDate, checkOutDate);
        hotel.completeCheckIn(g);

        List<Guest> currentList = hotel.getGuestList();
        currentList.add(g);
        hotel.setGuestList(currentList);
        System.out.println("Guest " + g.getName() + " has been checked in!");


    }

    // REQUIRES: guest exists in hotel guestList
    // MODIFIES: this
    // EFFECTS: checks guest out of hotel and removes guest from guestList
    public void checkOut() {
        Guest guestToBeCheckedOut;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter guest number:");
        int phoneNumber = scanner.nextInt();

        List<Guest> guestList = hotel.getGuestList();
        guestToBeCheckedOut = hotel.findGuest(guestList, phoneNumber);
        hotel.completeCheckOut(guestToBeCheckedOut);
        guestList.remove(guestToBeCheckedOut);
        hotel.setGuestList(guestList);
        System.out.println("Guest " + guestToBeCheckedOut.getName() + " has been checked out!");
        System.out.println(guestToBeCheckedOut.getName() + " 's" + " bill is "
                + guestToBeCheckedOut.getOutstandingBill());
    }

    // EFFECTS: prints all the guests in hotel to the console
    private void printGuests() {
        List<Guest> guestList = hotel.getGuestList();

        for (Guest g : guestList) {
            System.out.println(g);
        }
    }

    // EFFECTS: sets minibar use of guest from console
    public void miniBarUse() {
        Guest guest;
        System.out.println("Enter guest number:");
        int phoneNumber = input.nextInt();
        input.nextLine(); // Consume newline character

        List<Guest> guestList = hotel.getGuestList();
        guest = hotel.findGuest(guestList, phoneNumber);
        hotel.miniBarUse(guest);
        System.out.println("Minibar status of " + guest.getName() + " has been updated!");
    }


    // EFFECTS: sets guest as over stayer from console
    public void setOverStayer() {
        Guest guest;
        System.out.println("Enter guest number:");
        int phoneNumber = input.nextInt();
        input.nextLine();

        List<Guest> guestList = hotel.getGuestList();
        guest = hotel.findGuest(guestList, phoneNumber);
        guest.setOverstayStatus(true);
        hotel.setGuestList(guestList);
    }


    // EFFECTS: displays outstanding bill of guest
    public void getBill() {
        Guest guest;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter guest number:");
        int phoneNumber = scanner.nextInt();

        List<Guest> guestList = this.hotel.getGuestList();
        guest = this.hotel.findGuest(guestList, phoneNumber);
        System.out.println("Guest " + guest.getName() + " bill is $ " + guest.generateBill(guest)); ;
    }

    // EFFECTS: saves the hotel to file
    private void saveHotel() {
        try {
            jsonWriter.open();
            jsonWriter.write(hotel);
            jsonWriter.close();
            System.out.println("Saved " + hotel.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads hotel from file
    private void loadHotel() {
        try {
            hotel = jsonReader.read();
            System.out.println("Loaded " + hotel.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: console
    // EFFECTS: displays logged events
    public void quitApplication() {
        EventLog eventLog = EventLog.getInstance();
        System.out.println("All logged events:\n");
        int i = 1;
        for (Event event : eventLog) {
            System.out.println(i + ". " + event.getDescription() + " at " + event.getDate());
            i++;
        }
    }


}
