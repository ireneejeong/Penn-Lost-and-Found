package edu.upenn.cis350.lostandfoundpenn.Data;

public class Item {

    // parameters of lost and found items
    private String name;
    private String location;

    public Item(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }
}
