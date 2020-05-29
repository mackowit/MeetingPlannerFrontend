package com.crud.planner_frontend.weatherbit;

public class WeatherBitForecast {
    private String datetime;
    private String icon;
    private String description;
    private String temp;

    public WeatherBitForecast(String datetime, String icon, String description, String temp) {
        this.datetime = datetime;
        this.icon = icon;
        this.description = description;
        this.temp = temp;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    @Override
    public String toString() {
        return "WeatherBitForecast{" +
                "datetime='" + datetime + '\'' +
                ", icon='" + icon + '\'' +
                ", description='" + description + '\'' +
                ", temp='" + temp + '\'' +
                '}';
    }
}
