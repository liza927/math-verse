package com.mathverse.legacy.model;

public class Lecture {
    private int id;
    private String title;
    private String topic;
    private String content;

    public Lecture(int id, String title, String topic, String content) {
        this.id = id;
        this.title = title;
        this.topic = topic;
        this.content = content;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getTopic() { return topic; }
    public String getContent() { return content; }
}