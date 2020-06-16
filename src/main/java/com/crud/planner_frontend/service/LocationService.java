package com.crud.planner_frontend.service;

import com.crud.planner_frontend.model.Location;
import com.crud.planner_frontend.model.Meeting;
import com.crud.planner_frontend.model.User;
import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

public class LocationService {

    final static String url = "http://localhost:8080/v1/planner/location";

    private MeetingService meetingService = new MeetingService();

    public List<Location> getLocations() {
        RestTemplate rest = new RestTemplate();
        ResponseEntity<Location[]> exchange = rest.exchange(
                url,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                Location[].class);
        return Arrays.asList(exchange.getBody());
    }

    public void saveLocation(Location location) {
        RestTemplate restTemplate = new RestTemplate();
        Gson gson = new Gson();
        String jsonToSent = gson.toJson(location);
        System.out.println(jsonToSent);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpEntity httpEntity = new HttpEntity(jsonToSent, httpHeaders);
        ResponseEntity exchange = restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                Void.class);
        System.out.println(exchange);
    }

    public void updateLocation(Location location) {
        RestTemplate restTemplate = new RestTemplate();
        Gson gson = new Gson();
        String jsonToSent = gson.toJson(location);
        System.out.println(jsonToSent);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpEntity httpEntity = new HttpEntity(jsonToSent, httpHeaders);
        ResponseEntity exchange = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                httpEntity,
                Void.class);
        System.out.println(exchange);
    }

    public boolean deleteLocation(Long id) {
        List<Meeting> meetingsList = meetingService.getMeetings();
        List<Meeting> meetingsListLocationFiltered = meetingsList.stream()
                .filter(meeting -> meeting.getLocation().getId().equals(id))
                .collect(Collectors.toList());
        if (meetingsListLocationFiltered.size() > 0) {
            return false;
        } else {
            Map<String, String> params = new HashMap<String, String>();
            params.put("locationId", id.toString());
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.delete(url + "/{locationId}", params);
            return true;
        }
    }
}
