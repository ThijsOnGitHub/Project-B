package com.example.deopendagapp;

public class Workshops {
    private String roomcode, study, starttime, subject, timeleft;

    public Workshops(String roomcode, String study, String starttime, String subject, String timeleft) {
        this.subject = subject;
        this.study = study;
        this.roomcode = roomcode;
        this.starttime = starttime;
        this.timeleft = timeleft;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStudy() {
        return study;
    }

    public void setStudy(String study) {
        this.study = study;
    }

    public String getRoomcode() {
        return roomcode;
    }

    public void setRoomcode(String roomcode) {
        this.roomcode = roomcode;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getTimeleft() {
        return timeleft;
    }

    public void setTimeleft(String timeleft) {
        this.timeleft = timeleft;
    }
}
