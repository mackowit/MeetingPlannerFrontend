package com.crud.planner_frontend.view;

import com.crud.planner_frontend.gravatar.GravatarDefaultImage;
import com.crud.planner_frontend.model.Group;
import com.crud.planner_frontend.model.Location;
import com.crud.planner_frontend.model.Meeting;
import com.crud.planner_frontend.model.User;
import com.crud.planner_frontend.service.GroupService;
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
    private TextField email = new TextField("E-mail");
    private ComboBox<Group> group = new ComboBox<>("Group of users");
    private Button saveUser = new Button("Save");
    private Button cancelUser = new Button("Cancel");
    private Button addUser = new Button("Add");
    private Button editUser = new Button("Edit");
    private Button deleteUser = new Button("Delete");
    private Button cancelForm = new Button("Cancel");

    private Button manageGroups = new Button("Manage groups");

    boolean addUserFlag = false;
    boolean editUserFlag = false;

    private GroupForm groupForm = new GroupForm(this);

    private UserService userService = new UserService();
    private GroupService groupService = new GroupService();

    private Binder<User> binder = new Binder<>(User.class);

    public UsersForm(MeetingForm meetingForm) {
        deleteUser.setEnabled(false);
        groupForm.setVisible(false);
        usersGrid.setColumns("firstname", "lastname", "email", "group");
        refresh();
        HorizontalLayout manageButtons = new HorizontalLayout(addUser, editUser, deleteUser, cancelForm);
        VerticalLayout gridAndButtons = new VerticalLayout(manageUsersLabel, usersGrid, manageButtons);
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

        saveUser.addClickListener(event -> {
            formAndButtons.setVisible(false);
            User user = bindingForm();
            if(addUserFlag) {
                user.setId(null);
                userService.saveUser(user);
            }
            if(editUserFlag) {
                user.setId(binder.getBean().getId());
                userService.updateUser(user);
            }
            formClear();
            refresh();
            meetingForm.refresh();
        });
        cancelUser.addClickListener(event -> {
            formAndButtons.setVisible(false);
            formClear();
            refresh();
        });

        addUser.addClickListener(event -> {
            formAndButtons.setVisible(true);
            editUserFlag = false;
            addUserFlag = true;
        });
        editUser.addClickListener(event -> {
            if(setUser(usersGrid.asSingleSelect().getValue())) {
                formAndButtons.setVisible(true);
                addUserFlag = false;
                editUserFlag = true;
            }
        });
        deleteUser.addClickListener(event -> {
            if(setUser(usersGrid.asSingleSelect().getValue())) {
                userService.deleteUser(binder.getBean().getId());
                refresh();
            }
        });
        cancelForm.addClickListener(event -> {
            this.setVisible(false);
            formClear();
        });
    }

    public boolean setUser(User user) {
        binder.setBean(user);
        if(user == null) {
            return false;
        } else {
            firstname.focus();
            return true;
        }
    }

    public User bindingForm() {
        User user = new User();
        user.setFirstname(firstname.getValue());
        user.setLastname(lastname.getValue());
        user.setEmail(email.getValue());
        user.setGroup(group.getValue());
        return user;
    }

    public void formClear() {
        firstname.clear();
        lastname.clear();
        email.clear();
        group.clear();
    }

    public void refresh() {
        usersGrid.setItems(userService.getUsers());
        group.setItems(groupService.getGroups());
    }
}
