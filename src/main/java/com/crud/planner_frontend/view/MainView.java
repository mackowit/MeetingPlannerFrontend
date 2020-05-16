package com.crud.planner_frontend.view;

import com.crud.planner_frontend.model.Meeting;
import com.crud.planner_frontend.service.MeetingService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route
public class MainView extends VerticalLayout {
    private Label mainLabel = new Label("Meeting planner");
    private Label meetingLabel = new Label("Planned meetings");

    private Grid<Meeting> meetingsMainViewGrid = new Grid<>(Meeting.class);
    private MeetingService meetingService = new MeetingService();

    private Button addNewMeeting = new Button("Add new meeting");
    private Button viewMeeting = new Button("View meeting");
    private Button editMeeting = new Button("Edit meeting");
    private Button deleteMeeting = new Button("Cancel meeting");

    private MeetingForm meetingForm = new MeetingForm();

    public MainView() {
        meetingForm.setEnabled(false);
        meetingsMainViewGrid.setColumns("startDate", "endDate", "location");
        viewMeeting.setEnabled(false);
        editMeeting.setEnabled(false);
        deleteMeeting.setEnabled(false);

        HorizontalLayout buttonBar = new HorizontalLayout(addNewMeeting, viewMeeting, editMeeting, deleteMeeting);
        VerticalLayout mainContent = new VerticalLayout(meetingLabel, meetingsMainViewGrid, buttonBar);
        mainContent.setSizeFull();
        mainContent.getStyle().set("border", "1px solid black");
        add(mainLabel, mainContent, meetingForm);
        meetingsMainViewGrid.setItems(meetingService.getMeetings());

        addNewMeeting.addClickListener(event -> addMeeting());
        viewMeeting.addClickListener(event -> viewMeeting());

        meetingsMainViewGrid.asSingleSelect().addValueChangeListener(event -> {
            viewMeeting.setEnabled(true);
            editMeeting.setEnabled(true);
            deleteMeeting.setEnabled(true);
        });
    }

    public void addMeeting() {
        meetingForm.setEnabled(true);
    }

    public void viewMeeting() {
        meetingForm.setMeeting(meetingsMainViewGrid.asSingleSelect().getValue());
        meetingForm.setEnabled(true);
        meetingForm.getSave().setVisible(false);
        meetingForm.getCancel().setVisible(false);
        meetingForm.getAddParticipants().setVisible(false);
        meetingForm.getManageLocations().setVisible(false);
        meetingForm.getManageUsers().setVisible(false);
        meetingForm.getRemoveParticipants().setVisible(false);
        meetingForm.focusOn();
    }

    /*private void listLocations() {
       // List<Location> locationsList = exampleData();
        List<Location> locationsList = getLocations();
        grid.setItems(locationsList);
    }

    private List<Location> exampleData() {
        List<Location> locationsList = new ArrayList<>();
        locationsList.add(new Location("description1", "city1", "addres1"));
        locationsList.add(new Location("description2", "city2", "addres2"));
        locationsList.add(new Location("description3", "city3", "addres3"));
        return locationsList;
    }

    public List<Location> getLocations() {
        RestTemplate rest = new RestTemplate();
        ResponseEntity<Location[]> exchange = rest.exchange(
                "http://localhost:8080/v1/planner/location",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                Location[].class);
        return Arrays.asList(exchange.getBody());
    }*/
}
