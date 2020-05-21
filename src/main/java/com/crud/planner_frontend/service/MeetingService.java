package com.crud.planner_frontend.service;

import com.crud.planner_frontend.model.Group;
import com.crud.planner_frontend.model.Location;
import com.crud.planner_frontend.model.Meeting;
import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public List<Meeting> meetingsExampleList() {
        List<Meeting> exampleList = new ArrayList<>();
        exampleList.add(new Meeting(
                LocalDateTime.of(2020, 4, 30, 12, 0, 0),
                LocalDateTime.of(2020, 4, 30, 13, 0, 0 ),
                new Location("location1", "city1", "addres1"),
                null));
        exampleList.add(new Meeting(LocalDateTime.of(2020, 4, 30, 12, 0, 0),
                LocalDateTime.of(2020, 4, 30, 13, 0, 0 ),
                new Location("location2", "city2", "addres2"),
                null));
        return exampleList;
    }

    public void saveMeeting(Meeting meeting) {
        RestTemplate restTemplate = new RestTemplate();
        Gson gson = new Gson();
        String jsonToSent = gson.toJson(meeting);
        System.out.println(meeting.getEndDate().toString());
        System.out.println(jsonToSent);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpEntity httpEntity = new HttpEntity(jsonToSent, httpHeaders);
        ResponseEntity exchange = restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                Void.class);
    }

}
