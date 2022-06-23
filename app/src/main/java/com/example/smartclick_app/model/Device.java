package com.example.smartclick_app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Device implements Parcelable {
    public String id;
    public String name;
    public String typeId;

    public Device(String id, String name, String typeId) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
    }

    public Device(Parcel input) {
        id = input.readString();
        name = input.readString();
        typeId = input.readString();
    }

    public void changeName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTypeId() {
        return typeId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(typeId);
    }

    public static final Parcelable.Creator<Device> CREATOR
            = new Parcelable.Creator<Device>() {

        @Override
        public Device createFromParcel(Parcel source) {
            return new Device(source);
        }

        @Override
        public Device[] newArray(int size) {
            return new Device[0];
        }
    };

}
