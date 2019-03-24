package com.example.mazzam.chatproject.FireBaseUtil.Model;

public class Room {
    String room_id;
    String room_name;
    String room_desc;
    String room_stamp;
    int current_users;

    public Room() {
    }

    public Room(String room_id, String room_name, String room_desc, String room_stamp, int current_users) {
        this.room_id = room_id;
        this.room_name = room_name;
        this.room_desc = room_desc;
        this.room_stamp = room_stamp;
        this.current_users = current_users;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getRoom_desc() {
        return room_desc;
    }

    public void setRoom_desc(String room_desc) {
        this.room_desc = room_desc;
    }

    public String getRoom_stamp() {
        return room_stamp;
    }

    public void setRoom_stamp(String room_stamp) {
        this.room_stamp = room_stamp;
    }

    public int getCurrent_users() {
        return current_users;
    }

    public void setCurrent_users(int current_users) {
        this.current_users = current_users;
    }
}
