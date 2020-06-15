package com.crud.planner_frontend.service;

import com.crud.planner_frontend.model.Group;
import com.crud.planner_frontend.model.User;
import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupService {

    private UserService userService = new UserService();
    final static String url = "http://localhost:8080/v1/planner/groups";

    public List<Group> getGroups() {
        RestTemplate rest = new RestTemplate();
        ResponseEntity<Group[]> exchange = rest.exchange(
                url,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                Group[].class);
        return Arrays.asList(exchange.getBody());
    }

    public void saveGroup(Group group) {
        RestTemplate restTemplate = new RestTemplate();
        Gson gson = new Gson();
        String jsonToSent = gson.toJson(group);
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

    public void updateGroup(Group group) {
        RestTemplate restTemplate = new RestTemplate();
        Gson gson = new Gson();
        String jsonToSent = gson.toJson(group);
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

    public boolean deleteGroup(Long id) {
        List<User> usersList = userService.getUsers();
        List<User> usersListGroupFiltered = usersList.stream()
                .filter(user -> user.getGroup().getId().equals(id))
                .collect(Collectors.toList());
        if (usersListGroupFiltered.size() > 0) {
            return false;
        } else {
            Map<String, String> params = new HashMap<String, String>();
            params.put("groupId", id.toString());
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.delete(url + "/{groupId}", params);
            return true;
        }
    }
}
