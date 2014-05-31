package com.mkr.hellgame.hell.entities;

import java.util.Date;

public class Order {
    private int id;
    private int inGameUserId;
    private int durationMilliSeconds;
    private Date startDate;
    private Date endDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInGameUserId() {
        return inGameUserId;
    }

    public void setInGameUserId(int inGameUserId) {
        this.inGameUserId = inGameUserId;
    }

    public int getDurationMilliSeconds() {
        return durationMilliSeconds;
    }

    public void setDurationMilliSeconds(int durationMilliSeconds) {
        this.durationMilliSeconds = durationMilliSeconds;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
