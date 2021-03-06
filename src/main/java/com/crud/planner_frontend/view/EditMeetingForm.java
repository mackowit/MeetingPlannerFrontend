package com.crud.planner_frontend.view;

import com.crud.planner_frontend.model.Location;
import com.crud.planner_frontend.model.Meeting;
import com.crud.planner_frontend.model.User;
import com.crud.planner_frontend.service.*;
import com.crud.planner_frontend.weatherbit.WeatherBitForecast;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.util.ArrayList;
import java.util.List;

public class EditMeetingForm extends VerticalLayout implements MeetingForm {

        private Label formLabel = new Label("Edit meeting");
        private TextField topic = new TextField("Meeting topic");
        private Label startLabel = new Label("Set start date and time");
        private Label endLabel = new Label("Set end date and time");
        private LocalDateTimeField startDate = new LocalDateTimeField();
        private LocalDateTimeField endDate = new LocalDateTimeField();
        private Label locationLabel = new Label("Select location");
        private ComboBox<Location> location = new ComboBox<>();
        private Label participantsLabel = new Label("Select participants");
        private Label nameLabel = new Label();
        private Label mailLabel = new Label();
        private Label groupLabel = new Label();
        private ComboBox<User> participantPicker = new ComboBox<>("Participants");
        private Label participantsGridLabel = new Label("List of participants");
        private Grid<User> participants = new Grid<>(User.class);
        private TextArea content = new TextArea("Message content");

        private MeetingService meetingService = new MeetingService();
        private LocationService locationService = new LocationService();
        private UserService userService = new UserService();
        private WBService wbService = new WBService();
        private GravatarService gravatarService = new GravatarService();

        private Button manageLocations = new Button("Manage locations");
        private Button weatherForecast = new Button("Get weather forecast");
        private Button manageUsers = new Button("Manage users");
        private Button addParticipants = new Button("Add participant");
        private Button removeParticipants = new Button("Remove participant");

        private Button save = new Button("Save");
        private Button cancel = new Button("Cancel");

        private LocationForm locationForm = new LocationForm(this);
        private UsersForm usersForm = new UsersForm(this);

        private Binder<Meeting> binder = new Binder<>(Meeting.class);

        private List<User> participantsToAddList = new ArrayList<>();

        private Image image = new Image("https://dummyimage.com/80x80/000/fff.jpg&text=Pick+participants", "avatar image");

        private List<WeatherBitForecast> wBForecast = new ArrayList<>();

        public EditMeetingForm(MainView mainView) {
            refresh();

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
            VerticalLayout locationButtons = new VerticalLayout(manageLocations, weatherForecast);
            HorizontalLayout locationFieldAndButtons = new HorizontalLayout(location, locationButtons);
            HorizontalLayout locationFieldAndButtonAndForm = new HorizontalLayout(locationFieldAndButtons, locationForm);
            VerticalLayout selectLocation = new VerticalLayout(locationLabel, locationFieldAndButtonAndForm);
            selectLocation.setSpacing(false);
            selectLocation.setSizeFull();
            manageLocations.addClickListener(event -> locationForm.setVisible(true));

            //weather forecast
            Button closeForecast = new Button("Close forecast");
            HorizontalLayout forecast = new HorizontalLayout();
            VerticalLayout forecastAndButton = new VerticalLayout(forecast, closeForecast);
            forecastAndButton.setVisible(false);
            weatherForecast.addClickListener(event -> {
                forecastAndButton.setVisible(true);
                if(!location.isEmpty()) {
                    wBForecast = wbService.getWeatherForecast(location.getValue().getCity());
                    createWeatherForecastLayout(forecast, wBForecast);
                } else {
                    weatherForecast.setEnabled(false);
                    forecast.add(new Label("To present weather forecast please select location"));
                }
            });
            closeForecast.addClickListener(event -> {
                forecast.removeAll();
                forecastAndButton.setVisible(false);
                weatherForecast.setEnabled(true);
            });

            //users
            usersForm.setVisible(false);
            image.setVisible(false);
            VerticalLayout participantsCard = new VerticalLayout(image, nameLabel, mailLabel, groupLabel);
            VerticalLayout participantsField = new VerticalLayout(participantsLabel, participantPicker, participantsCard, addParticipants);
            participants.setColumns("firstname", "lastname", "email");
            VerticalLayout participantsList = new VerticalLayout(participantsGridLabel, participants, removeParticipants);
            addParticipants.addClickListener(event -> {
                participantsToAddList.add(participantPicker.getValue());
                participants.setItems(participantsToAddList);
            });
            participantPicker.addValueChangeListener(event -> {
                participantsCard.remove(image);
                image = gravatarService.getGravatarImage("GENERAL_AUDIENCES", "MONSTERID", 80, participantPicker.getValue().getEmail());
                nameLabel.setText(participantPicker.getValue().toString());
                mailLabel.setText(participantPicker.getValue().getEmail());
                groupLabel.setText(participantPicker.getValue().getGroup().getName());
                participantsCard.setAlignItems(Alignment.CENTER);
                participantsCard.add(image);
                image.setVisible(true);
            });
            removeParticipants.addClickListener(event -> {
                participantsToAddList.remove(participants.asSingleSelect().getValue());
                participants.setItems(participantsToAddList);
            });
            manageUsers.setWidthFull();
            HorizontalLayout usersFieldsAndButton = new HorizontalLayout(participantsField, participantsList, manageUsers);
            usersFieldsAndButton.setSpacing(false);
            HorizontalLayout selectUsers = new HorizontalLayout(usersFieldsAndButton, usersForm);
            selectUsers.setSpacing(false);
            selectUsers.setSizeFull();
            manageUsers.addClickListener(event -> usersForm.setVisible(true));

            //general
            HorizontalLayout buttons = new HorizontalLayout(save, cancel);

            //save&cancel actions
            save.addClickListener(event -> {
                this.setVisible(false);
                mainView.getAddNewMeeting().setEnabled(true);
                mainView.getViewMeeting().setEnabled(true);
                mainView.getEditMeeting().setEnabled(true);
                mainView.getDeleteMeeting().setEnabled(true);
                mainView.getMeetingsMainViewGrid().setEnabled(true);
                Meeting meeting = bindingForm();
                meeting.setId(binder.getBean().getId());
                meetingService.updateMeeting(meeting);
                mainView.refresh();
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

            add(formLabel, topic, setStartAndEnd, selectLocation, forecastAndButton, selectUsers, content, buttons);
        }

        public void setMeeting(Meeting meeting) {
            binder.setBean(meeting);
            participantsToAddList = binder.getBean().getParticipants();
            participants.setItems(participantsToAddList);

            if(meeting == null) {
                setVisible(false);
            } else {
                setVisible(true);
                startDate.focus();
            }
        }

        public Meeting bindingForm() {
            Meeting meeting = new Meeting();
            meeting.setTopic(topic.getValue());
            meeting.setStartDate(startDate.getValue());
            meeting.setEndDate(endDate.getValue());
            meeting.setLocation(location.getValue());
            meeting.setParticipants(participantsToAddList);
            meeting.setContent(content.getValue());
            return meeting;
        }

        public void createWeatherForecastLayout(HorizontalLayout forecast, List<WeatherBitForecast> wBForecast) {
            for(int i = 0; i < wBForecast.size(); i++){
                forecast.add(new VerticalLayout(
                        new Label(wBForecast.get(i).getDatetime()),
                        wbService.getWBIcon(wBForecast.get(i).getIcon()),
                        new Label(wBForecast.get(i).getDescription()),
                        new Label(wBForecast.get(i).getTemp())));
            }
            weatherForecast.setEnabled(false);
        }

    @Override
    public void refresh() {
        location.setItems(locationService.getLocations());
        participantPicker.setItems(userService.getUsers());
    }
}
