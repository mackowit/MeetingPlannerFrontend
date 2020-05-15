package com.crud.planner_frontend.service;

import com.crud.planner_frontend.model.Location;
import java.util.ArrayList;
import java.util.List;

public class LocationService {

    public List<Location> getLocations() {
        List<Location> locationList = new ArrayList<>();
        locationList = locationsExampleList();
        return locationList;
    }

    public List<Location> locationsExampleList() {
        List<Location> exampleList = new ArrayList<>();
        exampleList.add(new Location("location1", "city1", "address1"));
        exampleList.add(new Location("location3", "city2", "address2"));
        return exampleList;
    }
}
