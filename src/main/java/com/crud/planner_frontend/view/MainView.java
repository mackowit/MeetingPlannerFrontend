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
    private Button cancelMeeting = new Button("Cancel meeting");

    private NewMeetingForm newMeetingForm = new NewMeetingForm(this);
    private ViewMeetingForm viewMeetingForm = new ViewMeetingForm(this);
    private EditMeetingForm editMeetingForm = new EditMeetingForm(this);

    public MainView() {
        newMeetingForm.setVisible(false);
        viewMeetingForm.setVisible(false);
        editMeetingForm.setVisible(false);
        meetingsMainViewGrid.setColumns("topic", "startDate", "endDate", "location");
        viewMeeting.setEnabled(false);
        editMeeting.setEnabled(false);
        cancelMeeting.setEnabled(false);

        HorizontalLayout buttonBar = new HorizontalLayout(addNewMeeting, viewMeeting, editMeeting, cancelMeeting);
        VerticalLayout mainContent = new VerticalLayout(meetingLabel, meetingsMainViewGrid, buttonBar);
        mainContent.setSizeFull();
        mainContent.getStyle().set("border", "1px solid black");
        add(mainLabel, mainContent, newMeetingForm, viewMeetingForm, editMeetingForm);
        meetingsMainViewGrid.setItems(meetingService.getMeetings());

        addNewMeeting.addClickListener(event -> addMeeting());
        viewMeeting.addClickListener(event -> viewMeeting());
        editMeeting.addClickListener(event -> editMeeting());
        cancelMeeting.addClickListener(event -> cancelConfirmationLayout(this));

        meetingsMainViewGrid.asSingleSelect().addValueChangeListener(event -> {
            viewMeeting.setEnabled(true);
            editMeeting.setEnabled(true);
            cancelMeeting.setEnabled(true);
        });
    }

    public void addMeeting() {
        newMeetingForm.setVisible(true);
        addNewMeeting.setEnabled(false);
        viewMeeting.setEnabled(false);
        editMeeting.setEnabled(false);
        cancelMeeting.setEnabled(false);
        meetingsMainViewGrid.setEnabled(false);
    }

    public void viewMeeting() {
        viewMeetingForm.setMeeting(meetingsMainViewGrid.asSingleSelect().getValue());
        viewMeetingForm.setVisible(true);
        addNewMeeting.setEnabled(false);
        viewMeeting.setEnabled(false);
        editMeeting.setEnabled(false);
        cancelMeeting.setEnabled(false);
        meetingsMainViewGrid.setEnabled(false);
    }

    public void editMeeting() {
        editMeetingForm.setMeeting(meetingsMainViewGrid.asSingleSelect().getValue());
        editMeetingForm.setVisible(true);
        addNewMeeting.setEnabled(false);
        viewMeeting.setEnabled(false);
        editMeeting.setEnabled(false);
        cancelMeeting.setEnabled(false);
    }

    public void cancelMeeting() {
        Meeting meetingToCancel = meetingsMainViewGrid.asSingleSelect().getValue();
        meetingService.deleteMeeting(meetingToCancel.getId());
        refresh();
    }

    public void refresh() {
        meetingsMainViewGrid.setItems(meetingService.getMeetings());
    }

    public void cancelConfirmationLayout(MainView mainView) {
        Label confirmationLabel = new Label("Are you sure you want to cancel this meeting?");
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
        HorizontalLayout confirmationButtons = new HorizontalLayout(yesButton, noButton);
        VerticalLayout confirmation = new VerticalLayout(confirmationLabel, confirmationButtons);
        mainView.add(confirmation);
        yesButton.addClickListener(event -> {
            cancelMeeting();
            mainView.remove(confirmation);
        });
        noButton.addClickListener(event -> {
            mainView.remove(confirmation);
        });

    }

    public Button getAddNewMeeting() {
        return addNewMeeting;
    }

    public Button getViewMeeting() {
        return viewMeeting;
    }

    public Button getEditMeeting() {
        return editMeeting;
    }

    public Button getDeleteMeeting() {
        return cancelMeeting;
    }

    public Grid<Meeting> getMeetingsMainViewGrid() {
        return meetingsMainViewGrid;
    }
}
