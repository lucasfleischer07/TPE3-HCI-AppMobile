package com.example.smartclick_app.model;

public class Room {

    private String id;
    private String name;
    private String homeId;

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Room(String name,String homeId){
        this(null, name,homeId);
    }

    public Room(String id, String name,String homeId) {
        this.id = id;
        this.name = name;
        this.homeId = homeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null)
            return false;

        if (getClass() != o.getClass())
            return false;

        return this.getId().equals(((Room) o).getId());
    }
}
