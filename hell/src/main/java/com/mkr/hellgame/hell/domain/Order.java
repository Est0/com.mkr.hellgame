package com.mkr.hellgame.hell.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Order {
    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn(name = "inGameUserId")
    private InGameUser inGameUser;
    @Column(name = "duration")
    private long durationMilliSeconds;
    private Date startDate;
    private String action;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public InGameUser getInGameUser() {
        return inGameUser;
    }

    public void setInGameUser(InGameUser inGameUser) {
        this.inGameUser = inGameUser;
    }

    public long getDurationMilliSeconds() {
        return durationMilliSeconds;
    }

    public void setDurationMilliSeconds(long durationMilliSeconds) {
        this.durationMilliSeconds = durationMilliSeconds;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
