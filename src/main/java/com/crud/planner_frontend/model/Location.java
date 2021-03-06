package com.crud.planner_frontend.model;

public class Location {

    private Long id;
    private String description;
    private String city;
    private String address;

    public Location() {
    }

    public Location(String description, String city, String address) {
        this.description = description;
        this.city = city;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "" + description + ", " + city + ", " +address;
    }
}
