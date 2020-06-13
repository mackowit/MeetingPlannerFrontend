package com.crud.planner_frontend.service;

import com.crud.planner_frontend.gravatar.Gravatar;
import com.crud.planner_frontend.gravatar.GravatarDefaultImage;
import com.crud.planner_frontend.gravatar.GravatarRating;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;

public class GravatarService {

    private Gravatar gravatar = new Gravatar();

    public Image getImage(String email) {
        gravatar.setSize(50);
        gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
        gravatar.setDefaultImage(GravatarDefaultImage.MONSTERID);
        byte[] jpg = gravatar.download(email);
        StreamResource resource = new StreamResource("avatar.jpg", () -> new ByteArrayInputStream(jpg));
        return new Image(resource, "avatar");
    }
}
