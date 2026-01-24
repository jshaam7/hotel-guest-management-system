package model;

import org.json.JSONObject;
import persistence.Writable;


// constructs Guest
// modifies Guest details based on different ui scenarios
public class Guest implements Writable {

    private String name;
    private int phoneNumber;
    private String roomType;
    private String checkIn;
    private String checkOut;
    private int outstandingBill;
    private boolean overstayStatus;
    private boolean minibarUse;



    // EFFECTS: instantiates a guest with phoneNumber, checkIn and checkOut date
    public Guest(String n,
                 int number,
                 String rt,
                 String in,
                 String out) {


        this.name = n;
        this.phoneNumber = number;
        this.roomType = rt;
        this.checkIn = in;
        this.checkOut = out;
        this.outstandingBill = 0;
        this.overstayStatus = false;
        this.minibarUse = false;


    }

    public String getName() {
        return this.name;
    }

    public int getNumber() {
        return this.phoneNumber;
    }

    public String getRoomType() {
        return this.roomType;
    }

    public String getCheckIn() {
        return this.checkIn;
    }

    public String getCheckOut() {
        return this.checkOut;
    }

    public int getOutstandingBill() {
        return this.outstandingBill;
    }

    public boolean getOverstayStatus() {
        return this.overstayStatus;
    }

    public boolean getMinibarUse() {
        return this.minibarUse;
    }


    public void setName(String s) {
        this.name = s;
    }

    public void setNumber(int n) {
        this.phoneNumber = n;
    }

    public void setRoomType(String s) {
        this.roomType = s;
    }

    public void setCheckIn(String d1) {
        this.checkIn = d1;
    }

    public void setCheckOut(String d2) {
        this.checkOut = d2;
    }

    public void setOutstandingBill(int b) {
        this.outstandingBill = this.outstandingBill + b;
    }

    public void setOverstayStatus(boolean y) {
        this.overstayStatus = y;
        if (y == true) {
            EventLog.getInstance().logEvent(new Event("Guest " + this.getName() + " set as overstayer"));
        }
    }

    public void setMinibarUse(boolean y) {
        this.minibarUse = y;

    }


    // REQUIRES: guest exists in hotel guestList
    // EFFECTS: returns the outstanding bill of a guest
    public int generateBill(Guest g) {
        return g.getOutstandingBill();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("phoneNumber", phoneNumber);
        json.put("roomType", roomType);
        json.put("checkIn", checkIn);
        json.put("checkOut", checkOut);
        json.put("outstandingBill", outstandingBill);
        json.put("overstayStatus", overstayStatus);
        json.put("minibarUse", minibarUse);
        return json;
    }

    @Override
    public String toString() {
        return "Guest{"
                +
                "name=" + name
                +
                ", phoneNumber=" + phoneNumber
                +
                ", roomType=" + roomType
                +
                ", checkIn=" + checkIn
                +
                ", checkOut=" + checkOut
                +
                ", outstandingBill=" + outstandingBill
                +
                ", overstayStatus=" + overstayStatus
                +
                ", minibarUse=" + minibarUse
                +
                '}';
    }

}

