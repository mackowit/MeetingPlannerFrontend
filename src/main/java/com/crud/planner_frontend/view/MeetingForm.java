package com.crud.planner_frontend.view;

import com.crud.planner_frontend.model.Location;
import com.crud.planner_frontend.model.User;
import com.crud.planner_frontend.service.LocationService;
import com.crud.planner_frontend.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;

public class MeetingForm extends VerticalLayout {

    private Label startLabel = new Label("Set start date and time");
    private Label endLabel = new Label("Set end date and time");
    private DatePicker startDate = new DatePicker();
    private DatePicker endDate = new DatePicker();
    private TimePicker startTime = new TimePicker();
    private TimePicker endTime = new TimePicker();
    private Label locationLabel = new Label("Select location");
    private ComboBox<Location> location = new ComboBox<>();
    private Label meetingOwnerLabel = new Label("Select meeting owner");
    private Label participantsLabel = new Label("Select participants");
    private ComboBox<User> meetingOwner = new ComboBox<>("Meeting Owner");
    private ComboBox<User> participants = new ComboBox<>("Participants");

    private LocationService locationService = new LocationService();
    private UserService userService = new UserService();

    private Button manageLocations = new Button("Manage locations");
    private Button manageUsers = new Button("Manage users");

    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");

    private LocationForm locationForm = new LocationForm();
    private UsersForm usersForm = new UsersForm();

    public MeetingForm() {
        location.setItems(locationService.getLocations());
        meetingOwner.setItems(userService.getUsers());
        participants.setItems(userService.getUsers());

        //date and time
        HorizontalLayout startFields = new HorizontalLayout(startDate, startTime);
        VerticalLayout startPickers = new VerticalLayout(startLabel, startFields);
        startPickers.setSpacing(false);
        HorizontalLayout endFields = new HorizontalLayout(endDate, endTime);
        VerticalLayout endPickers = new VerticalLayout(endLabel, endFields);
        endPickers.setSpacing(false);
        HorizontalLayout setStartAndEnd = new HorizontalLayout(startPickers, endPickers);
        setStartAndEnd.setSizeFull();

        //location
        locationForm.setVisible(false);
        HorizontalLayout locationFieldAndButton = new HorizontalLayout(location, manageLocations);
        HorizontalLayout locationFieldAndButtonAndForm = new HorizontalLayout(locationFieldAndButton, locationForm);
        VerticalLayout selectLocation = new VerticalLayout(locationLabel, locationFieldAndButtonAndForm);
        selectLocation.setSpacing(false);
        selectLocation.setSizeFull();
        manageLocations.addClickListener(event -> locationForm.setVisible(true));

        //users
        usersForm.setVisible(false);
        VerticalLayout meetingOwnerField = new VerticalLayout(meetingOwnerLabel, meetingOwner);
        VerticalLayout participantsField = new VerticalLayout(participantsLabel, participants);
        HorizontalLayout usersFieldsAndButton = new HorizontalLayout(meetingOwnerField, participantsField, manageUsers);
        usersFieldsAndButton.setSpacing(false);
        HorizontalLayout selectUsers = new HorizontalLayout(usersFieldsAndButton, usersForm);
        selectUsers.setSpacing(false);
        selectUsers.setSizeFull();
        selectUsers.getStyle().set("margin-left", "auto");
        manageUsers.addClickListener(event -> usersForm.setVisible(true));

        //general
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        save.addClickListener(event -> this.setEnabled(false));
        cancel.addClickListener(event -> this.setEnabled(false));

        add(setStartAndEnd, selectLocation, selectUsers, buttons);
    }

}
