package com.crud.planner_frontend;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Route
public class MainView extends VerticalLayout {
    final Grid<Location> grid;

    public MainView() {
        this.grid = new Grid<>(Location.class);
        add(grid);
        listLocations();
    }

    private void listLocations() {
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
    }
}
