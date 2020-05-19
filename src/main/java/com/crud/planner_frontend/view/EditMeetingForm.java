package com.crud.planner_frontend.view;

import com.crud.planner_frontend.model.Location;
import com.crud.planner_frontend.model.Meeting;
import com.crud.planner_frontend.model.User;
import com.crud.planner_frontend.service.LocationService;
import com.crud.planner_frontend.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;

public class EditMeetingForm extends VerticalLayout {

        private Label formLabel = new Label("Edit meeting");
        private Label startLabel = new Label("Set start date and time");
        private Label endLabel = new Label("Set end date and time");
        private LocalDateTimeField startDate = new LocalDateTimeField();
        private LocalDateTimeField endDate = new LocalDateTimeField();
        private Label locationLabel = new Label("Select location");
        private ComboBox<Location> location = new ComboBox<>();
        private Label participantsLabel = new Label("Select participants");
        private ComboBox<User> participantPicker = new ComboBox<>("Participants");
        private Label participantsGridLabel = new Label("List of participants");
        private Grid<User> participants = new Grid<>(User.class);

        private LocationService locationService = new LocationService();
        private UserService userService = new UserService();

        private Button manageLocations = new Button("Manage locations");
        private Button manageUsers = new Button("Manage users");
        private Button addParticipants = new Button("Add participant");
        private Button removeParticipants = new Button("Remove participant");

        private Button save = new Button("Save");
        private Button cancel = new Button("Cancel");

        private LocationForm locationForm = new LocationForm();
        private UsersForm usersForm = new UsersForm();

        private Binder<Meeting> binder = new Binder<>(Meeting.class);

        public EditMeetingForm(MainView mainView) {
            location.setItems(locationService.getLocations());
            participants.setItems(userService.getUsers());

            //date and time
            HorizontalLayout startFields = new HorizontalLayout(startDate);
            VerticalLayout startPickers = new VerticalLayout(startLabel, startFields);
            startPickers.setSpacing(false);
            HorizontalLayout endFields = new HorizontalLayout(endDate);
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
            VerticalLayout participantsField = new VerticalLayout(participantsLabel, participantPicker, addParticipants);
            participants.setColumns("firstname", "lastname", "email");
            VerticalLayout participantsList = new VerticalLayout(participantsGridLabel, participants, removeParticipants);
            HorizontalLayout usersFieldsAndButton = new HorizontalLayout(participantsField, participantsList, manageUsers);
            usersFieldsAndButton.setSpacing(false);
            HorizontalLayout selectUsers = new HorizontalLayout(usersFieldsAndButton, usersForm);
            selectUsers.setSpacing(false);
            selectUsers.setSizeFull();
            manageUsers.addClickListener(event -> usersForm.setVisible(true));

            //general
            HorizontalLayout buttons = new HorizontalLayout(save, cancel);
            save.addClickListener(event -> {
                this.setVisible(false);
                mainView.getAddNewMeeting().setEnabled(true);
                mainView.getViewMeeting().setEnabled(true);
                mainView.getEditMeeting().setEnabled(true);
                mainView.getDeleteMeeting().setEnabled(true);
                mainView.getMeetingsMainViewGrid().setEnabled(true);
            });
            cancel.addClickListener(event -> {
                this.setVisible(false);
                mainView.getAddNewMeeting().setEnabled(true);
                mainView.getViewMeeting().setEnabled(true);
                mainView.getEditMeeting().setEnabled(true);
                mainView.getDeleteMeeting().setEnabled(true);
                mainView.getMeetingsMainViewGrid().setEnabled(true);
            });

            //binding
            binder.bindInstanceFields(this);

            add(formLabel, setStartAndEnd, selectLocation, selectUsers, buttons);
        }

        public void setMeeting(Meeting meeting) {
            binder.setBean(meeting);

            if(meeting == null) {
                setVisible(false);
            } else {
                setVisible(true);
                startDate.focus();
            }
        }
}
