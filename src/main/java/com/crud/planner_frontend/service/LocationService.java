package com.crud.planner_frontend.service;

import com.crud.planner_frontend.model.Location;
import com.crud.planner_frontend.model.Meeting;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocationService {

    final static String url = "http://localhost:8080/v1/planner/location";

    public List<Location> getLocations() {
        /*List<Location> locationList = new ArrayList<>();
        locationList = locationsExampleList();
        return locationList;*/
        RestTemplate rest = new RestTemplate();
        ResponseEntity<Location[]> exchange = rest.exchange(
                url,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                Location[].class);
        return Arrays.asList(exchange.getBody());
    }

    public List<Location> locationsExampleList() {
        List<Location> exampleList = new ArrayList<>();
        exampleList.add(new Location("location1", "city1", "address1"));
        exampleList.add(new Location("location3", "city2", "address2"));
        return exampleList;
    }
}
