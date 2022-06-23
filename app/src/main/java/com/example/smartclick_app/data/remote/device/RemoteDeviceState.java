package com.example.smartclick_app.data.remote.device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RemoteDeviceState {
    public String getStatus() {
        return status;
    }

    public int getVolume() {
        return volume;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void setHeat(String heat) {
        this.heat = heat;
    }

    public void setGrill(String grill) {
        this.grill = grill;
    }

    public void setConvection(String convection) {
        this.convection = convection;
    }

    public void setFreezerTemperature(int freezerTemperature) {
        this.freezerTemperature = freezerTemperature;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setLock(String lock) {
        this.lock = lock;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public String getGenre() {
        return genre;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getHeat() {
        return heat;
    }

    public String getGrill() {
        return grill;
    }

    public String getConvection() {
        return convection;
    }

    public int getFreezerTemperature() {
        return freezerTemperature;
    }

    public String getMode() {
        return mode;
    }

    public String getLock() {
        return lock;
    }

    public String getColor() {
        return color;
    }

    public int getBrightness() {
        return brightness;
    }

    public RemoteDeviceStateSong getSong() {
        return song;
    }

    public void setSong(RemoteDeviceStateSong song) {
        this.song = song;
    }

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("volume")
    @Expose
    private int volume;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("temperature")
    @Expose
    private int temperature;
    @SerializedName("heat")
    @Expose
    private String heat;
    @SerializedName("grill")
    @Expose
    private String grill;
    @SerializedName("convection")
    @Expose
    private String convection;
    @SerializedName("freezerTemperature")
    @Expose
    private int freezerTemperature;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("lock")
    @Expose
    private String lock;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("brightness")
    @Expose
    private int brightness;
    @SerializedName("song")
    @Expose
    private RemoteDeviceStateSong song;



}
