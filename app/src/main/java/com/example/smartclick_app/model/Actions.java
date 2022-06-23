package com.example.smartclick_app.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.ObjectStreamException;
import java.util.List;

public class Actions implements Parcelable {
    private final String name;
    private final String params;

    public Actions(String name, String params){
        this.name=name;
        this.params=params;
    }

    public Actions(Parcel input){
        name=input.readString();
        params=input.readString();
    }

    public String getActionName(){
        return name;
    }

    public String getParams(){
        return params;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(params);
    }

    public static final Parcelable.Creator<Actions> CREATOR
            = new Parcelable.Creator<Actions>(){

        @Override
        public Actions createFromParcel(Parcel source) {
            return new Actions(source);
        }

        @Override
        public Actions[] newArray(int size) {
            return new Actions[0];
        }
    };
}
