package com.crud.planner_frontend.service;

import com.crud.planner_frontend.gravatar.Gravatar;
import com.crud.planner_frontend.gravatar.GravatarDefaultImage;
import com.crud.planner_frontend.gravatar.GravatarRating;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;

public class GravatarService {

    private Gravatar gravatar = new Gravatar();

    public Image getGravatarImage(String rating, String defaultImage, int size, String mail) {
        gravatar.setSize(size);
        gravatar.setDefaultImage(GravatarDefaultImage.valueOf(defaultImage));
        gravatar.setRating(GravatarRating.valueOf(rating));
        byte[] jpg = gravatar.download(mail);
        StreamResource resource = new StreamResource("avatar.jpg", () -> new ByteArrayInputStream(jpg));
        return new Image(resource, "avatar");
    }
}
