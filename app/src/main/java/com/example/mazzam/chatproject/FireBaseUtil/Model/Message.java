package com.example.mazzam.chatproject.FireBaseUtil.Model;

public class Message {
    String id;
    String content;
    String date;
    String sender_name;
    String sender_id;
    String room_id;

    public Message() {
    }

    public Message(String id, String content, String date, String sender_name, String sender_id, String room_id) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.sender_name = sender_name;
        this.sender_id = sender_id;
        this.room_id = room_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }
}
