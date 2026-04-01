package com.sonumaddheshiya.journalapk.responseApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class WeatherResponse {

    private Current current;

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public static class Current {

        private int temperature;

        @JsonProperty("weather_descriptions")
        private List<String> weatherDetails;

        @JsonProperty("feelslike")
        private int feelsLike;

        public int getTemperature() {
            return temperature;
        }

        public void setTemperature(int temperature) {
            this.temperature = temperature;
        }

        public List<String> getWeatherDetails() {
            return weatherDetails;
        }

        public void setWeatherDetails(List<String> weatherDetails) {
            this.weatherDetails = weatherDetails;
        }

        public int getFeelsLike() {
            return feelsLike;
        }

        public void setFeelsLike(int feelsLike) {
            this.feelsLike = feelsLike;
        }
    }
}

