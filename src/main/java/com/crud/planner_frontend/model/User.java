package com.crud.planner_frontend.model;

import javax.validation.constraints.NotNull;

public class User {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private Group group;

    public User() {
    }

    public User(String firstname, String lastname, String email, Group group) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.group = group;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "" + firstname + " " + lastname;
    }
}
