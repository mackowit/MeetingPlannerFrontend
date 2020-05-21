package com.crud.planner_frontend.view;

import com.crud.planner_frontend.model.Group;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class UsersForm extends HorizontalLayout {

    private Label manageUsersLabel = new Label("Manage users");
    private Grid<User> usersGrid = new Grid<>(User.class);
    private TextField firstname = new TextField("First name");
    private TextField lastname = new TextField("Last name");
    private TextField email = new TextField("email");
    private ComboBox<Group> group = new ComboBox<>("Group of users");
    private Button saveUser = new Button("Save");
    private Button cancelUser = new Button("Cancel");
    private Button addUser = new Button("Add");
    private Button editUser = new Button("Edit");
    private Button deleteUser = new Button("Delete");

    private Button manageGroups = new Button("Manage groups");

    boolean addUserFlag = false;
    boolean editUserFlag = false;

    private GroupForm groupForm = new GroupForm();

    private UserService userService = new UserService();

    private Binder<User> binder = new Binder<>(User.class);

    public UsersForm() {
        groupForm.setVisible(false);
        usersGrid.setColumns("firstname", "lastname", "email", "group");
        HorizontalLayout manageButtons = new HorizontalLayout(addUser, editUser, deleteUser);
        VerticalLayout gridAndButtons = new VerticalLayout(usersGrid, manageButtons);
        gridAndButtons.setSpacing(false);
        HorizontalLayout saveAndCancelButtons = new HorizontalLayout(saveUser, cancelUser);
        HorizontalLayout groupGridAndButton = new HorizontalLayout(group, manageGroups);
        VerticalLayout formAndButtons = new VerticalLayout(firstname, lastname, email, groupGridAndButton, saveAndCancelButtons);
        formAndButtons.setSpacing(false);
        formAndButtons.setVisible(false);
        manageGroups.addClickListener(event -> groupForm.setVisible(true));

        add(gridAndButtons, formAndButtons, groupForm);

        //binding
        binder.bindInstanceFields(this);

        usersGrid.setItems(userService.getUsers());
        saveUser.addClickListener(event -> this.setVisible(false));
        cancelUser.addClickListener(event -> this.setVisible(false));

        addUser.addClickListener(event -> {
            formAndButtons.setVisible(true);
            addUserFlag = true;
        });
        editUser.addClickListener(event -> {
            setUser(usersGrid.asSingleSelect().getValue());
            formAndButtons.setVisible(true);
            editUserFlag = true;
        });
    }

    public void setUser(User user) {
        binder.setBean(user);

        if(user == null) {
            setVisible(false);
        } else {
            setVisible(true);
            firstname.focus();
        }
    }
}
