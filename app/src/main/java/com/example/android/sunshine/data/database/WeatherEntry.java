/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.sunshine.data.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

@Entity(tableName = "weather", indices = {@Index(value = {"date"}, unique = true)})
public class WeatherEntry {


    // region  Fields for Api json response and DataBase
    @SerializedName("temp")
    @Ignore
    private Temp temp;
    private double pressure;
    private double humidity;
    @SerializedName("weather")
    @Ignore
    private List<Weather> weatherList;
    @SerializedName("speed")
    private double wind;
    @SerializedName("deg")
    private double degrees;
    //  endregion

    // region Fields only for DataBase
    @PrimaryKey(autoGenerate = true)
    private int idEntry;

    @ColumnInfo(name = "weather_icon_id")
    private int weatherIconId;
    private Date date;
    private double min;
    private double max;
    //  endregion


//    /**
//     * This constructor is used by OpenWeatherJsonParser. When the network fetch has JSON data, it
//     * converts this data to WeatherEntry objects using this constructor.
//     * @param weatherIconId Image id for weather
//     * @param date Date of weather
//     * @param min Min temperature
//     * @param max Max temperature
//     * @param humidity Humidity for the day
//     * @param pressure Barometric pressure
//     * @param wind Wind speed
//     * @param degrees Wind direction
//     */
//    @Ignore
//    public WeatherEntry(int weatherIconId, Date date, Temp temp, double humidity, double pressure, double wind, double degrees) {
//        this.weatherIconId = weatherIconId;
//        this.date = date;
//        this.min = min;
//        this.max = max;
//        this.humidity = humidity;
//        this.pressure = pressure;
//        this.wind = wind;
//        this.degrees = degrees;
//    }

    public WeatherEntry(int idEntry, int weatherIconId, Date date, double min, double max, double humidity, double pressure, double wind, double degrees) {
        this.idEntry = idEntry;
        this.weatherIconId = weatherIconId;
        this.date = date;
        this.min = min;
        this.max = max;
        this.humidity = humidity;
        this.pressure = pressure;
        this.wind = wind;
        this.degrees = degrees;
    }


    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public double getWind() {
        return wind;
    }

    public void setWind(double wind) {
        this.wind = wind;
    }

    public double getDegrees() {
        return degrees;
    }

    public void setDegrees(double degrees) {
        this.degrees = degrees;
    }


    public int getIdEntry() {
        return idEntry;
    }

    public int getWeatherIconId() {
        if(weatherList == null || weatherList.isEmpty())
            return weatherIconId;
        return weatherList.get(0).getWeatherIconId();
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getMin() {
        if(temp == null)
            return min;
        return temp.getMin();
    }


    public double getMax() {
        if(temp == null)
            return max;
        return temp.getMax();
    }


    /**
     * Object inside Weather response that contains temperatures
     */
    public class Temp {
        @SerializedName("day")
        private double tempByDay;
        private double min;
        private double max;
        @SerializedName("night")
        private double tempByNight;
        @SerializedName("eve")
        private double tempByEve;
        @SerializedName("morn")
        private double tempByMorn;

        public Temp(double tempByDay, double min, double max, double tempByNight, double tempByEve, double tempByMorn) {
            this.tempByDay = tempByDay;
            this.min = min;
            this.max = max;
            this.tempByNight = tempByNight;
            this.tempByEve = tempByEve;
            this.tempByMorn = tempByMorn;
        }

        public double getTempByDay() {
            return tempByDay;
        }

        public void setTempByDay(double tempByDay) {
            this.tempByDay = tempByDay;
        }

        public double getMin() {
            return min;
        }

        public void setMin(double min) {
            this.min = min;
        }

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }

        public double getTempByNight() {
            return tempByNight;
        }

        public void setTempByNight(double tempByNight) {
            this.tempByNight = tempByNight;
        }

        public double getTempByEve() {
            return tempByEve;
        }

        public void setTempByEve(double tempByEve) {
            this.tempByEve = tempByEve;
        }

        public double getTempByMorn() {
            return tempByMorn;
        }

        public void setTempByMorn(double tempByMorn) {
            this.tempByMorn = tempByMorn;
        }
    }

    /**
     * Object that contains description and icon data, inside the Weather response
     */
    public class Weather
    {
        @SerializedName("id")
        private int weatherIconId;
        private String main;
        private String description;
        private String icon;

        public Weather(int weatherIconId, String main, String description, String icon) {
            this.weatherIconId = weatherIconId;
            this.main = main;
            this.description = description;
            this.icon = icon;
        }

        public int getWeatherIconId() {
            return weatherIconId;
        }

        public void setWeatherIconId(int weatherIconId) {
            this.weatherIconId = weatherIconId;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

}
