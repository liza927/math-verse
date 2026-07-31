package com.mathverse.legacy.model;

public class Lecture {
    private int id;
    private String title;
    private String topic;

    public Lecture(int id, String title, String topic) {
        this.id = id;
        this.title = title;
        this.topic = topic;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getTopic() { return topic; }
}