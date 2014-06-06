package com.mkr.hellgame.infrastructure;

public interface Trigger {
    long nextScheduledExecuteIn();

    Job getJob();

    void setJob(Job job);
}
