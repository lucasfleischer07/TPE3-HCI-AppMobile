package com.example.smartclick_app.model.Devices;

import com.example.smartclick_app.model.Device;

public class Speaker extends Device {
    private int volume;
    private String status;
    private String genre;
    public final static String TYPE_ID="c89b94e8581855bc";
    public final static String ACTION_SET_VOLUME="setVolume";
    public final static String ACTION_PLAY="play";
    public final static String ACTION_STOP="stop";
    public final static String ACTION_PAUSE="pause";
    public final static String ACTION_RESUME="resume";
    public final static String ACTION_NEXT_SONG="nextSong";
    public final static String ACTION_PREVIOUS_SONG="previousSong";
    public final static String ACTION_SET_GENRE="setGenre";
    public final static String ACTION_GET_PLAYLIST="getPlaylist";


    public Speaker(String id,String name,int volume,String status,String genre){
        super(id,name,TYPE_ID);
        this.volume=volume;
        this.status=status;
        this.genre=genre;
    }

    public int getVolume() {
        return volume;
    }

    public String getStatus() {
        return status;
    }

    public String getGenre() {
        return genre;
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
