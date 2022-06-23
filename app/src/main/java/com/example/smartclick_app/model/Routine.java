package com.example.smartclick_app.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.smartclick_app.MyApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Routine implements Parcelable {
    private final String id;
    private final String name;
    private final String houseId;
    private final HashMap<Device,Actions> deviceAndActionsMap = new HashMap<>();

    public Routine(String id, String name, String houseId){
        this.id=id;
        this.name=name;
        this.houseId=houseId;
    }

    public Routine(Parcel input){
        id=input.readString();
        name=input.readString();
        houseId=input.readString();
        int size=input.readInt();
        for(int i = 0; i < size; i++){
            Device key = input.readParcelable(getClass().getClassLoader());
            Actions value = input.readParcelable(getClass().getClassLoader());
            deviceAndActionsMap.put(key,value);
        }
    }

    public Map<Device, Actions> getDeviceAndActionsMap() {
        return deviceAndActionsMap;
    }

    public String getId() {
            return id;
        }
    
    public String getName() {
        return name;
    }

    public String getHouseId() {
        return houseId;
    }

    public void addDeviceAndActions(Device device,Actions actions){
        getDeviceAndActionsMap().put(device,actions);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(houseId);
        dest.writeInt(deviceAndActionsMap.size());//escribo el size para usarlo despues en el for
        for(Map.Entry<Device,Actions> entry : deviceAndActionsMap.entrySet()){
            dest.writeParcelable(entry.getKey(),0);
            dest.writeParcelable(entry.getValue(),0);
        }
    }

    public static final Parcelable.Creator<Routine> CREATOR
            = new Parcelable.Creator<Routine>(){

        @Override
        public Routine createFromParcel(Parcel source) {
            return new Routine(source);
        }

        @Override
        public Routine[] newArray(int size) {
            return new Routine[0];
        }
    };
}
