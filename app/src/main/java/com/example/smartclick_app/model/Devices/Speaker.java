package com.example.smartclick_app.model.Devices;

import com.example.smartclick_app.model.Device;

public class Speaker extends Device {
    private int volume;
    private String status;
    private String genre;

    public Speaker(String id,String name,String typeId,int volume,String status,String genre){
        super(id,name,typeId);
        this.volume=volume;
        this.status=status;
        this.genre=genre;
    }

    public void changeVolume(int newVolume){
        if(volume!=newVolume){
            //llamar a la api
        }
    }

    public void nextSong(){
        //llamar la api
    }

    public void startSong(){
        //llamar la api
    }

    public void pauseSong(){
        //llamar la api
    }

    public void prevSong(){
        //llamar la api
    }

    public void changeGenre(Genre genre){

    }

    private enum Genre{
        classical("Classical","Clásico"),
        country("Country","Country"),
        dance("Dance","Baile"),
        latina("Latina","Latina"),
        pop("Pop","Pop"),
        rock ("Rock","Rock");
        private String nombreIngles;
        private String nombreEsp;

        private Genre(String nombreIngles, String nombreEsp){
            this.nombreIngles=nombreIngles;
            this.nombreEsp=nombreEsp;
        }
    }
}
