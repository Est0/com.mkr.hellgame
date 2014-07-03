package com.mkr.hellgame.infrastructure.impl;

import com.mkr.hellgame.infrastructure.abstraction.Trigger;

public class OneTimeTrigger implements Trigger {
    private Runnable job;
    private boolean executed = false;

    @Override
    public long calcNextScheduledExecuteIn() {
        long result = executed ? Long.MAX_VALUE : 0;
        executed = true;
        return result;
    }

    @Override
    public Runnable getJob() {
        return job;
    }

    public void setJob(Runnable job) {
        this.job = job;
    }
}
