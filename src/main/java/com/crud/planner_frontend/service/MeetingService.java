package com.crud.planner_frontend.service;

import com.crud.planner_frontend.model.Group;
import com.crud.planner_frontend.model.Location;
import com.crud.planner_frontend.model.Meeting;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MeetingService {


    public List<Meeting> getMeetings() {
        List<Meeting> meetingList = new ArrayList<>();
        meetingList = meetingsExampleList();
        return meetingList;
    }

    public List<Meeting> meetingsExampleList() {
        List<Meeting> exampleList = new ArrayList<>();
        exampleList.add(new Meeting(
                LocalDateTime.of(2020, 4, 30, 12, 0, 0),
                LocalDateTime.of(2020, 4, 30, 13, 0, 0 ),
                new Location("location1", "city1", "addres1"),
                null));
        exampleList.add(new Meeting(LocalDateTime.of(2020, 4, 30, 12, 0, 0),
                LocalDateTime.of(2020, 4, 30, 13, 0, 0 ),
                new Location("location2", "city2", "addres2"),
                null));
        return exampleList;
    }

}
