package com.example.prototype;

public class Workshops {
    private String roomcode, study, starttime, subject, timeleft;

    public Workshops(String roomcode, String study, String starttime, String subject) {
        this.subject = subject;
        this.study = study;
        this.roomcode = roomcode;
        this.starttime = starttime;
        this.timeleft = ""; // starttime - time.now()
    }

    public String getSubject() {
        return subject;
    }

    public String getStudy() {
        return study;
    }

    public String getRoomcode() {
        return roomcode;
    }

    public String getStarttime() {
        return starttime;
    }

    public String getTimeleft() {
        return timeleft;
    }
}