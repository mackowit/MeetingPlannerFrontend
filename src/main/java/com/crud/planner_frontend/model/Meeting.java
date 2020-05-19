package com.crud.planner_frontend.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Meeting {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Location location;
    private List<User> participants;

    public Meeting() {
    }

    public Meeting(LocalDateTime startDate, LocalDateTime endDate, Location location, List<User> participants) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.participants = new ArrayList<>();
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }
}
