package com.crud.planner_frontend.service;

import com.crud.planner_frontend.model.Group;
import com.crud.planner_frontend.weatherbit.WeatherBitForecast;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vaadin.flow.component.html.Image;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class WBService {
    final static String url = "https://api.weatherbit.io/v2.0/forecast/daily?";
    //https://api.weatherbit.io/v2.0/forecast/daily?city=Raleigh,NC&key=API_KEY

    public List<WeatherBitForecast> getWeatherForecast(String city) {
        List<WeatherBitForecast> wBForecast = new ArrayList<>();
        RestTemplate rest = new RestTemplate();
        ResponseEntity<String> exchange = rest.exchange(
                url + "city=" + city + "&key=0b3f7173fdd545a1aa36e3f27e22d9bb",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                String.class);

        JsonObject jsonObject = new JsonParser().parse(exchange.getBody()).getAsJsonObject();
        JsonArray arr = jsonObject.getAsJsonArray("data");
        for (int i = 0; i < arr.size(); i++) {
            JsonElement weather = arr.get(i).getAsJsonObject().get("weather");
            wBForecast.add(new WeatherBitForecast(arr.get(i).getAsJsonObject().get("datetime").getAsString(),
                        weather.getAsJsonObject().get("icon").getAsString(),
                        weather.getAsJsonObject().get("description").getAsString(),
                        arr.get(i).getAsJsonObject().get("temp").getAsString()));
        }
        System.out.println(wBForecast);
        return wBForecast;
    }

    public Image getWBIcon(String icon) {
        return new Image("https://www.weatherbit.io/static/img/icons/" + icon + ".png", icon);
    }
}
