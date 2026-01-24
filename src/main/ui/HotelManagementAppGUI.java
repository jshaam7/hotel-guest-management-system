package ui;

import model.*;

import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

// The 'HotelManagementAppGUI' class represents a GUI application for managing guests and associated a hotel.
// Provides a button-driven interface to add, view guests, save to a file, load from a file.

public class HotelManagementAppGUI {
    private Hotel hotel;
    private JFrame frame;
    private List<Guest> guestList;
    private JLabel imageLabel;
    private JTextField nameField;
    private JTextField phoneNumberField;
    private JTextField roomTypeField;
    private JTextField checkInField;
    private JTextField checkOutField;
    private EventLog eventLog;


    // MODIFIES: this
    // EFFECTS: Constructs a HotelManagementAppGUI, initializing a guestList, creating a new JFrame with a
    // suitable header, and adding a new JLabel. The method sets the layout, size, buttons, and other necessary
    // configurations for the GUI.
    public HotelManagementAppGUI() {
        eventLog = EventLog.getInstance();
        frame = new JFrame("Hotel Grand Reception");
        guestList = new ArrayList<>();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 400);
        frame.setLayout(new GridLayout(5, 1));
        createAndAddButtons();
        frame.setVisible(true);
        imageLabel = new JLabel();
        frame.add(imageLabel);
        frame.setLocationRelativeTo(null);
        hotel = new Hotel("Hotel Grand", 5, 25, 40);
    }

    // EFFECTS: The 'Main' method initializes the 'HotelManagementAppGUI' and starts the application.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HotelManagementAppGUI());
    }


    // MODIFIES: frame
    // EFFECTS: Creates and adds buttons to the frame
    private void createAndAddButtons() {
        checkInGuestButton();
        checkOutGuestButton();
        viewGuestsButton();
        saveHotelButton();
        loadHotelButton();
        setOverstayerButton();
        getOverStayersButton();
        exitButton();
    }

    // MODIFIES: frame
    // EFFECTS: Adds functionality to the Guest Check In Button and calls the finishCheckIn() method.
    private void checkInGuestButton() {
        JButton addGuestButton = new JButton("Guest Check In");
        addGuestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finishCheckIn();
            }
        });
        frame.add(addGuestButton);
    }

    // MODIFIES: this
    // EFFECTS: Handles the process of adding a new guest.
    private void finishCheckIn() {
        JDialog addGuestDetails = newGuestUserEntries("Add new guest:", 6);
        JButton saveGuestButton = new JButton("Save guest");
        addGuestDetails.add(saveGuestButton);
        saveGuestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean success = addGuest();
                if (success) {
                    JOptionPane.showMessageDialog(frame, "Guest checked in successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to check in guest. Please try again.");
                }
                addGuestDetails.dispose();
            }
        });
        addGuestDetails.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Checks for NumberFormatException and returns true if guest is added properly.
    private boolean addGuest() {
        Guest guest = new Guest(" ", 0, " ", " ", " ");
        try {
            guest.setName(nameField.getText());
            guest.setNumber(Integer.parseInt(phoneNumberField.getText()));
            guest.setRoomType(roomTypeField.getText());
            guest.setCheckIn(checkInField.getText());
            guest.setCheckOut(checkOutField.getText());
            guestList.add(guest);
            hotel.completeCheckIn(guest);
            return true;

        } catch (NumberFormatException ne) {
            if (!phoneNumberField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Invalid phone number format. Please try "
                        + "again. Phone numbers can only have numbers.");
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: Opens a user entry dialogue box that can accept input for
    // name, phoneNumber, roomType, checkIn, checkOut
    private JDialog newGuestUserEntries(String title, int rows) {
        JDialog guestDialog = new JDialog(frame, title, true);
        guestDialog.setSize(1000, 500);
        guestDialog.setLayout(new GridLayout(rows, 2));
        nameField = new JTextField();
        phoneNumberField = new JTextField();
        roomTypeField = new JTextField();
        checkInField = new JTextField();
        checkOutField = new JTextField();
        guestDialog.add(new JLabel("Name:"));
        guestDialog.add(nameField);
        guestDialog.add(new JLabel("Phone Number:"));
        guestDialog.add(phoneNumberField);
        guestDialog.add(new JLabel("Room Type(Suite/Single Room/Double Room):"));
        guestDialog.add(roomTypeField);
        guestDialog.add(new JLabel("Check In Date:"));
        guestDialog.add(checkInField);
        guestDialog.add(new JLabel("Check Out Date:"));
        guestDialog.add(checkOutField);
        return guestDialog;
    }

    // MODIFIES: frame
    // EFFECTS: Adds functionality to the Guest Check Out Button and calls the finishCheckOut() method.
    private void checkOutGuestButton() {
        JButton checkOutGuestButton = new JButton("Guest Check Out");
        checkOutGuestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finishCheckOut();
            }
        });
        frame.add(checkOutGuestButton);
    }

    // REQUIRES: guest to be checked out must exist in guestList
    // MODIFIES: this
    // EFFECTS: Handles the process of checking out an existing guest.
    private void finishCheckOut() {
        JDialog checkOutGuestDialog = checkOutGuestNumber("Guest Phone Number:", 1);
        JButton checkOutButton = new JButton("Check Out");
        checkOutGuestDialog.add(checkOutButton);
        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean success = completeCheckOut();
                if (success) {
                    JOptionPane.showMessageDialog(frame, "Guest checked out successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to check out guest. Please try again.");
                }
                checkOutGuestDialog.dispose();
            }
        });
        checkOutGuestDialog.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Opens a user entry dialogue box that can accept input of guest phone number
    private JDialog checkOutGuestNumber(String title, int rows) {
        JDialog guestDialog = new JDialog(frame, title, true);
        guestDialog.setSize(350, 100);
        guestDialog.setLayout(new GridLayout(rows, 2));
        phoneNumberField = new JTextField();
        guestDialog.add(new JLabel("Phone Number:"));
        guestDialog.add(phoneNumberField);
        return guestDialog;
    }

    // MODIFIES: this
    // EFFECTS: completes the checkout process of the guest by finding guest and removing them from guestList,
    //          if guest does not exist returns a Guest not found string.
    private boolean completeCheckOut() {
        int num = Integer.parseInt(phoneNumberField.getText());
        Guest guest = hotel.findGuest(hotel.getGuestList(), num);
        if (guest != null) {
            hotel.completeCheckOut(guest);
            guestList.remove(guest);
            List<Guest> jsonGuestList = hotel.getGuestList();
            jsonGuestList.removeIf(g -> g.getNumber() == num);
            saveGuests();
            return true;
        } else {
            JOptionPane.showMessageDialog(frame, "Guest not found with the provided phone number.");
            return false;
        }
    }

    // MODIFIES: frame
    // EFFECTS: Adds functionality to the View All Guests Button and calls the viewGuests() method.
    private void viewGuestsButton() {
        JButton viewGuestsButton = new JButton("View All Guests");
        viewGuestsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewGuests();
            }
        });
        frame.add(viewGuestsButton);
    }

    // MODIFIES: frame
    // EFFECTS: returns all the guests in the guestList and displays them on the application for the user.
    private void viewGuests() {
        StringBuilder guests = new StringBuilder();
        for (Guest guest : guestList) {
            guests.append("Name: ").append(guest.getName())
                    .append(", Outstanding Bill: ").append(guest.getOutstandingBill()).append(".00")
                    .append(", Phone Number: ").append(guest.getNumber())
                    .append(", Room Type: ").append(guest.getRoomType())
                    .append(", Check In: ").append(guest.getCheckIn())
                    .append(", Check Out: ").append(guest.getCheckOut())
                    .append("\n");
        }
        JOptionPane.showMessageDialog(frame, guests.toString(), "All Guests", JOptionPane.INFORMATION_MESSAGE);

    }

    // MODIFIES: frame
    // EFFECTS: Adds functionality to the Save Hotel Button and calls the saveGuests() method.
    private void saveHotelButton() {
        JButton saveHotelButton = new JButton("Save Hotel");
        saveHotelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGuests();
            }
        });
        frame.add(saveHotelButton);
    }

    // MODIFIES: this
    // EFFECTS: writes current state of Hotel onto hotel.json
    private void saveGuests() {
        JsonWriter jsonWriter = null;
        try {
            jsonWriter = new JsonWriter("./data/hotel.json");
            jsonWriter.open();
            jsonWriter.write(hotel);
            jsonWriter.close();
            JOptionPane.showMessageDialog(frame, "Guests were successfully saved to hotel!");

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Unable to save guests successfully!");
        } finally {
            if (jsonWriter != null) {
                jsonWriter.close();
            }
        }
    }

    // MODIFIES: frame
    // EFFECTS: Adds functionality to the Load Hotel Button and calls the loadGuests() method.
    private void loadHotelButton() {
        JButton loadHotelButton = new JButton("Load Hotel");
        loadHotelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadGuests();
            }
        });
        frame.add(loadHotelButton);
    }

    // MODIFIES: this
    // EFFECTS: reads current state of Hotel onto hotel.json
    private void loadGuests() {
        try {

            JsonReader jsonReader = new JsonReader("./data/hotel.json");
            Hotel currentHotel = jsonReader.read();
            guestList.clear();
            guestList.addAll(currentHotel.getGuestList());
            hotel = currentHotel;
            JOptionPane.showMessageDialog(frame, "Hotel was loaded successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Unable to load guests!");
        }
    }

    // MODIFIES: frame
    // EFFECTS: Adds functionality to the Set Overstayer Button and calls the setOverstayer() method.
    private void setOverstayerButton() {
        JButton setOverstayerButton = new JButton("Set Overstayer");
        setOverstayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setOverstayer();
            }
        });
        frame.add(setOverstayerButton);
    }

    // REQUIRES: guest should exist
    // MODIFIES: this
    // EFFECTS: sets the overstay status of guest to be chosen by user as true
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void setOverstayer() {
        JDialog setOverstayerDialog = new JDialog(frame, "Set Guest as Overstayer", true);
        setOverstayerDialog.setLayout(new GridLayout(2, 2));
        JTextField phoneNumberField = new JTextField();
        JButton setOverstayerButton = new JButton("Set as Overstayer");
        setOverstayerDialog.add(new JLabel("Enter Phone Number:"));
        setOverstayerDialog.add(phoneNumberField);
        setOverstayerDialog.add(setOverstayerButton);
        setOverstayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Guest guest = hotel.findGuest(hotel.getGuestList(), Integer.parseInt(phoneNumberField.getText()));
                if (guest != null) {
                    guest.setOverstayStatus(true);
                    JOptionPane.showMessageDialog(frame, "Guest marked as overstayer successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Guest not found with the provided phone number.");
                }
                setOverstayerDialog.dispose();
            }
        });
        setOverstayerDialog.setSize(350, 100);
        setOverstayerDialog.setVisible(true);
    }

    // MODIFIES: frame
    // EFFECTS: Adds functionality to the Overstayed Guests Button and calls the overstayGuests() method.
    private void getOverStayersButton() {
        JButton overstayGuestsButton = new JButton("Overstayed Guests");
        overstayGuestsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                overstayGuests();
            }
        });
        frame.add(overstayGuestsButton);
    }

    // MODIFIES: this
    // EFFECTS: Displays all guests in the guestList who have overstay status true.
    private void overstayGuests() {
        StringBuilder guests = new StringBuilder();
        List<Guest> listToAppend = hotel.getListOfOverStayGuests();
        for (Guest guest : listToAppend) {
            guests.append("Name: ").append(guest.getName())
                    .append(", Phone Number: ").append(guest.getNumber())
                    .append(", Room Type: ").append(guest.getRoomType())
                    .append("\n");
        }
        JOptionPane.showMessageDialog(frame, guests.toString(), "All Overstayed Guests",
                JOptionPane.INFORMATION_MESSAGE);

    }

    // MODIFIES: frame
    // EFFECTS: Adds functionality to the Exit Button and exits the application.
    private void exitButton() {
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printAllEventsToConsole();
                printAllEventsLogged();
                System.exit(0);
            }
        });
        frame.add(exitButton);
    }

    // MODIFIES: frame
    // EFFECTS: displays all logged events
    private void printAllEventsLogged() {
        int i = 1;
        StringBuilder logMessage = new StringBuilder("All logged events:\n");
        for (Event event : EventLog.getInstance()) {
            logMessage.append(i + ". " + event.toString()).append("\n\t");
            i++;
        }
        JOptionPane.showMessageDialog(frame, logMessage.toString(), "Event Log", JOptionPane.INFORMATION_MESSAGE);
    }

    // MODIFIES: console
    // EFFECTS: displays logged events
    private void printAllEventsToConsole() {
        int i = 1;
        System.out.println("All logged events:");
        for (Event event : EventLog.getInstance()) {
            System.out.println(i + ". " + event.toString());
            i++;
        }
    }

}

