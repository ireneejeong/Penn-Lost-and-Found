package edu.upenn.cis350.lostandfoundpenn.Data;

public class Item {

    // parameters of lost and found items
    private String name;
    private String location;
    public String status;

    public Item(String name, String location, String status) {
        this.name = name;
        this.location = location;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {return status; }
}
