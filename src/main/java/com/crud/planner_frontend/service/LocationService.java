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

import java.time.LocalDateTime;
import java.util.*;

public class LocationService {

    final static String url = "http://localhost:8080/v1/planner/location";

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

    public void deleteLocation(Long id) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("locationId", id.toString());
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(url + "/{locationId}" ,  params);
    }
}
