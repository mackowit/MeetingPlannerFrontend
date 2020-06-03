package com.crud.planner_frontend.service;

import com.crud.planner_frontend.model.Group;
import com.crud.planner_frontend.model.Location;
import com.crud.planner_frontend.model.Meeting;
import com.crud.planner_frontend.view.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeetingService {

    final static String url = "http://localhost:8080/v1/planner/meetings";

    public List<Meeting> getMeetings() {
        RestTemplate rest = new RestTemplate();
        ResponseEntity<Meeting[]> exchange = rest.exchange(
                    url,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    Meeting[].class);
        return Arrays.asList(exchange.getBody());
    }

    public void saveMeeting(Meeting meeting) {
        RestTemplate restTemplate = new RestTemplate();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        String jsonToSent = gson.toJson(meeting);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpEntity httpEntity = new HttpEntity(jsonToSent, httpHeaders);
        ResponseEntity exchange = restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                Void.class);
    }

    public void updateMeeting(Meeting meeting) {
        RestTemplate restTemplate = new RestTemplate();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        String jsonToSent = gson.toJson(meeting);
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

    public void deleteMeeting(Long id) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("meetingId", id.toString());
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(url + "/{meetingId}" ,  params);
    }
}
