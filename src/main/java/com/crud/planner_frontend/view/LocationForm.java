package com.crud.planner_frontend.view;

import com.crud.planner_frontend.model.Location;
import com.crud.planner_frontend.service.LocationService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class LocationForm extends HorizontalLayout {

    private Label manageLocationLabel = new Label("Manage locations");
    private Grid<Location> locationGrid = new Grid<>(Location.class);
    private TextField description = new TextField("Description");
    private TextField city = new TextField("City");
    private TextField address = new TextField("Address");
    private Button saveLocation = new Button("Save");
    private Button cancelLocation = new Button("Cancel");
    private Button addLocation = new Button("Add");
    private Button editLocation = new Button("Edit");
    private Button deleteLocation = new Button("Delete");

    private LocationService locationService = new LocationService();

    public LocationForm() {
        locationGrid.setColumns("description", "city", "address");
        HorizontalLayout manageButtons = new HorizontalLayout(addLocation, editLocation, deleteLocation);
        VerticalLayout gridAndButtons = new VerticalLayout(locationGrid, manageButtons);
        gridAndButtons.setSpacing(false);
        HorizontalLayout saveAndCancelButtons = new HorizontalLayout(saveLocation, cancelLocation);
        VerticalLayout formAndButtons = new VerticalLayout(description, city, address, saveAndCancelButtons);
        formAndButtons.setSpacing(false);

        add(gridAndButtons, formAndButtons);
        locationGrid.setItems(locationService.getLocations());
        saveLocation.addClickListener(event -> this.setVisible(false));
        cancelLocation.addClickListener(event -> this.setVisible(false));
    }
}
