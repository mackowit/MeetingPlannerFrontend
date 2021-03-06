package com.crud.planner_frontend.view;

import com.crud.planner_frontend.model.Location;
import com.crud.planner_frontend.model.Meeting;
import com.crud.planner_frontend.model.User;
import com.crud.planner_frontend.service.LocationService;
import com.crud.planner_frontend.service.UserService;
import com.crud.planner_frontend.service.WBService;
import com.crud.planner_frontend.weatherbit.WeatherBitForecast;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.util.ArrayList;
import java.util.List;

public class ViewMeetingForm extends VerticalLayout {

        private Label formLabel = new Label("View meeting");
        private TextField topic = new TextField("Meeting topic");
        private Label startLabel = new Label("Start date and time");
        private Label endLabel = new Label("End date and time");
        private LocalDateTimeField startDate = new LocalDateTimeField();
        private LocalDateTimeField endDate = new LocalDateTimeField();
        private Label locationLabel = new Label("Location");
        private ComboBox<Location> location = new ComboBox<>();
        private Label participantsGridLabel = new Label("List of participants");
        private Grid<User> participants = new Grid<>(User.class);
        private TextField content = new TextField("Message content");

        private LocationService locationService = new LocationService();
        private UserService userService = new UserService();
        private WBService wbService = new WBService();

        private Button weatherForecast = new Button("View weather forecast");
        private Button cancel = new Button("Cancel");

        private Binder<Meeting> binder = new Binder<>(Meeting.class);

        private List<WeatherBitForecast> wBForecast = new ArrayList<>();

        public ViewMeetingForm(MainView mainView) {
            //binding
            binder.bindInstanceFields(this);
            //location.setItems(locationService.getLocations());
            //participants.setItems(binder.getBean().getParticipants());

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
            HorizontalLayout locationFieldAndButton = new HorizontalLayout(location, weatherForecast);
            VerticalLayout selectLocation = new VerticalLayout(locationLabel, locationFieldAndButton);
            selectLocation.setSpacing(false);
            selectLocation.setSizeFull();

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

            /*//binding
            binder.bindInstanceFields(this);*/

            add(formLabel, topic, setStartAndEnd, selectLocation, forecastAndButton, participantsList, content, buttons);
        }

        public void setMeeting(Meeting meeting) {
            binder.setBean(meeting);
            System.out.println(binder.getBean().getTopic().toString());
            System.out.println(binder.getBean().getStartDate().toString());
            System.out.println(binder.getBean().getEndDate().toString());
            System.out.println(binder.getBean().getLocation().toString());
            System.out.println(binder.getBean().getParticipants().toString());
            System.out.println(binder.getBean().getContent().toString());

            participants.setItems(binder.getBean().getParticipants());

            if(meeting == null) {
                setVisible(false);
            } else {
                setVisible(true);
                startDate.focus();
            }
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
}
