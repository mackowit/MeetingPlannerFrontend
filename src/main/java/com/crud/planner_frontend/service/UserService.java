package com.crud.planner_frontend.service;

import com.crud.planner_frontend.model.Group;
import com.crud.planner_frontend.model.Location;
import com.crud.planner_frontend.model.Meeting;
import com.crud.planner_frontend.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    public List<User> getUsers() {
        List<User> userList = new ArrayList<>();
        userList = usersExampleList();
        return userList;
    }

    public List<User> usersExampleList() {
        List<User> exampleList = new ArrayList<>();
        exampleList.add(new User("firstname1", "lastname1", "email1", new Group("group1")));
        exampleList.add(new User("firstname2", "lastname2", "email2", new Group("group1")));
        return exampleList;
    }

}
