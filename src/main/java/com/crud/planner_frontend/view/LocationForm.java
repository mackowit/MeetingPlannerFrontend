package com.crud.planner_frontend.view;

import com.crud.planner_frontend.model.Location;
import com.crud.planner_frontend.model.User;
import com.crud.planner_frontend.service.LocationService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

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
    private Button cancelForm = new Button("Cancel");

    private boolean addLocationFlag = false;
    private boolean editLocationFlag = false;

    private LocationService locationService = new LocationService();

    private Binder<Location> binder = new Binder<>(Location.class);

    public LocationForm(MeetingForm meetingForm) {
        deleteLocation.setEnabled(false);
        locationGrid.setColumns("description", "city", "address");
        HorizontalLayout manageButtons = new HorizontalLayout(addLocation, editLocation, deleteLocation);
        VerticalLayout gridAndButtons = new VerticalLayout(manageLocationLabel, locationGrid, manageButtons);
        gridAndButtons.setSpacing(false);
        HorizontalLayout saveAndCancelButtons = new HorizontalLayout(saveLocation, cancelLocation);
        VerticalLayout formAndButtons = new VerticalLayout(description, city, address, saveAndCancelButtons);
        formAndButtons.setVisible(false);
        formAndButtons.setSpacing(false);

        add(gridAndButtons, formAndButtons);

        //binding
        binder.bindInstanceFields(this);

        refresh();
        saveLocation.addClickListener(event -> {
            formAndButtons.setVisible(false);
            Location location = bindingForm();
            if(addLocationFlag) {
                location.setId(null);
                locationService.saveLocation(location);
            }
            if(editLocationFlag) {
                location.setId(binder.getBean().getId());
                locationService.updateLocation(location);
            }
            formClear();
            refresh();
            meetingForm.refresh();
        });
        cancelLocation.addClickListener(event -> {
                formAndButtons.setVisible(false);
                formClear();
                refresh();
        });

        addLocation.addClickListener(event -> {
            formAndButtons.setVisible(true);
            addLocationFlag = true;
            editLocationFlag = false;
        });
        editLocation.addClickListener(event -> {
            if(setLocation(locationGrid.asSingleSelect().getValue())) {
                formAndButtons.setVisible(true);
                editLocationFlag = true;
                addLocationFlag = false;
            }
        });
        deleteLocation.addClickListener(event -> {
            if(setLocation(locationGrid.asSingleSelect().getValue())) {
                locationService.deleteLocation(binder.getBean().getId());
                refresh();
            }
        });
        cancelForm.addClickListener(event -> {
           this.setVisible(false);
           formClear();
        });
    }

    public boolean setLocation(Location location) {
        binder.setBean(location);
        if(location == null) {
            return false;
        } else {
            description.focus();
            return true;
        }
    }

    public Location bindingForm() {
        Location location = new Location();
        location.setDescription(description.getValue());
        location.setCity(city.getValue());
        location.setAddress(address.getValue());
        return location;
    }

    public void formClear() {
        description.clear();
        city.clear();
        address.clear();
    }

    public void refresh() {
        locationGrid.setItems(locationService.getLocations());
    }
}
