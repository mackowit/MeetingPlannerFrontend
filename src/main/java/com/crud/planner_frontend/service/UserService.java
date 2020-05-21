package com.crud.planner_frontend.service;

import com.crud.planner_frontend.model.Group;
import com.crud.planner_frontend.model.Location;
import com.crud.planner_frontend.model.Meeting;
import com.crud.planner_frontend.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserService {

    final static String url = "http://localhost:8080/v1/planner/users";

    public List<User> getUsers() {
        /*List<Location> locationList = new ArrayList<>();
        locationList = locationsExampleList();
        return locationList;*/
        RestTemplate rest = new RestTemplate();
        ResponseEntity<User[]> exchange = rest.exchange(
                url,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                User[].class);
        return Arrays.asList(exchange.getBody());
    }

    public List<User> usersExampleList() {
        List<User> exampleList = new ArrayList<>();
        exampleList.add(new User("firstname1", "lastname1", "email1", new Group("group1")));
        exampleList.add(new User("firstname2", "lastname2", "email2", new Group("group1")));
        return exampleList;
    }

}
