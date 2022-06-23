package com.example.smartclick_app.model.Devices;

import com.example.smartclick_app.model.Device;

public class Speaker extends Device {
    private int volume;
    private String status;
    private String genre;
    private String song;
    private String songProgress;
    private String songTotalDuration;
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

    public final static String PLAY="playing";
    public final static String STOP="stopped";
    public final static String PAUSE="paused";
    public final static String RESUME="resume";
    public final static String NEXT_SONG="nextSong";
    public final static String PREVIOUS_SONG="previousSong";

    public final static String GENDER_CLASSICAL="classical";
    public final static String GENDER_COUNTRY="country";
    public final static String GENDER_DANCE="dance";
    public final static String GENDER_LATINA="latina";
    public final static String GENDER_POP="pop";
    public final static String GENDER_ROCK="rock";



    public Speaker(String id,String name,int volume,String status,String genre,String song,String songProgress,String songTotalDuration){
        super(id,name,TYPE_ID);
        this.volume=volume;
        this.status=status;
        this.genre=genre;
        this.song=song;
        this.songProgress=songProgress;
        this.songTotalDuration=songTotalDuration;
    }

    public String getSongTotalDuration() {
        return songTotalDuration;
    }

    public String getSong() {
        return song;
    }

    public String getSongProgress() {
        return songProgress;
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

        Genre(String nombreIngles, String nombreEsp){
            this.nombreIngles=nombreIngles;
            this.nombreEsp=nombreEsp;
        }
    }
}
