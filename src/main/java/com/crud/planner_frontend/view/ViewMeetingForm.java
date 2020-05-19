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

public class ViewMeetingForm extends VerticalLayout {

        private Label formLabel = new Label("View meeting");
        private Label startLabel = new Label("Start date and time");
        private Label endLabel = new Label("End date and time");
        private LocalDateTimeField startDate = new LocalDateTimeField();
        private LocalDateTimeField endDate = new LocalDateTimeField();
        private Label locationLabel = new Label("Location");
        private ComboBox<Location> location = new ComboBox<>();
        private Label participantsGridLabel = new Label("List of participants");
        private Grid<User> participants = new Grid<>(User.class);

        private LocationService locationService = new LocationService();
        private UserService userService = new UserService();

        private Button cancel = new Button("Cancel");

        private Binder<Meeting> binder = new Binder<>(Meeting.class);

        public ViewMeetingForm(MainView mainView) {
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
            HorizontalLayout locationField = new HorizontalLayout(location);
            VerticalLayout selectLocation = new VerticalLayout(locationLabel, locationField);
            selectLocation.setSpacing(false);
            selectLocation.setSizeFull();

            //users
            participants.setColumns("firstname", "lastname", "email");
            VerticalLayout participantsList = new VerticalLayout(participantsGridLabel, participants);

            //general
            HorizontalLayout buttons = new HorizontalLayout(cancel);
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

            add(formLabel, setStartAndEnd, selectLocation, participantsList, buttons);
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
