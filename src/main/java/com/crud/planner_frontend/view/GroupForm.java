package com.crud.planner_frontend.view;

import com.crud.planner_frontend.model.Group;
import com.crud.planner_frontend.service.GroupService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class GroupForm extends VerticalLayout {

    private Label manageGroupLabel = new Label("Manage groups");
    private Grid<Group> groupGrid = new Grid<>(Group.class);
    private TextField name = new TextField("Group name");
    private Button saveGroup = new Button("Save");
    private Button cancelGroup = new Button("Cancel");
    private Button addGroup = new Button("Add");
    private Button editGroup = new Button("Edit");
    private Button deleteGroup = new Button("Delete");
    private Button cancelManage = new Button("Cancel");

    private GroupService groupService = new GroupService();

    boolean addGroupFlag = false;
    boolean editGroupFlag = false;

    Binder<Group> binder = new Binder(Group.class);

    public GroupForm(UsersForm usersForm) {
        groupGrid.setColumns("name");
        HorizontalLayout manageButtons = new HorizontalLayout(addGroup, editGroup, deleteGroup, cancelManage);
        VerticalLayout gridAndButtons = new VerticalLayout(manageGroupLabel, groupGrid, manageButtons);
        gridAndButtons.setSpacing(false);
        HorizontalLayout saveAndCancelButtons = new HorizontalLayout(saveGroup, cancelGroup);
        VerticalLayout formAndButtons = new VerticalLayout(name, saveAndCancelButtons);
        formAndButtons.setSpacing(false);
        formAndButtons.setVisible(false);

        add(gridAndButtons, formAndButtons);
        groupGrid.setItems(groupService.getGroups());

        //binding
        binder.bindInstanceFields(this);

        //saving new or edited group
        saveGroup.addClickListener(event -> {
            formAndButtons.setVisible(false);
            Group group = new Group();
            if(addGroupFlag) {
                group.setId(null);
                group.setName(name.getValue());
                groupService.saveGroup(group);
            }
            if(editGroupFlag) {
                group.setId(binder.getBean().getId());
                group.setName(name.getValue());
                groupService.updateGroup(group);
            }
            name.clear();
            groupGrid.setItems(groupService.getGroups());
            usersForm.refresh();
        });
        //deleting group
        deleteGroup.addClickListener(event -> {
            setGroup(groupGrid.asSingleSelect().getValue());
            if(groupService.deleteGroup(binder.getBean().getId()))
            groupGrid.setItems(groupService.getGroups());
            else groupDeletingDenial(binder.getBean());
        });
        //canceling editing form
        cancelGroup.addClickListener(event -> formAndButtons.setVisible(false));

        //invocation of editing form to add or edit
        addGroup.addClickListener(event -> {
            formAndButtons.setVisible(true);
            addGroupFlag = true;
        });
        editGroup.addClickListener(event -> {
            setGroup(groupGrid.asSingleSelect().getValue());
            formAndButtons.setVisible(true);
            editGroupFlag = true;
        });
        //canceling whole managing form and return to usersform
        cancelManage.addClickListener(event -> this.setVisible(false));
    }

    public void setGroup(Group group) {
        binder.setBean(group);

        if(group == null) {
            setVisible(false);
        } else {
            setVisible(true);
            name.focus();
        }
    }

    public void groupDeletingDenial(Group group) {
        saveGroup.setEnabled(false);
        editGroup.setEnabled(false);
        deleteGroup.setEnabled(false);
        cancelManage.setEnabled(false);
        cancelGroup.setEnabled(false);
        addGroup.setEnabled(false);
        Label deleteDenialLabel = new Label("Users assigned to " + group.getName() + " exists, so it cannot be deleted");
        Button deleteDenialLabelConfirmation = new Button("OK");
        this.add(deleteDenialLabel, deleteDenialLabelConfirmation);
        deleteDenialLabelConfirmation.addClickListener(event -> {
           this.remove(deleteDenialLabel, deleteDenialLabelConfirmation);
            saveGroup.setEnabled(true);
            editGroup.setEnabled(true);
            deleteGroup.setEnabled(true);
            cancelManage.setEnabled(true);
            cancelGroup.setEnabled(true);
            addGroup.setEnabled(true);
        });
    }
}
