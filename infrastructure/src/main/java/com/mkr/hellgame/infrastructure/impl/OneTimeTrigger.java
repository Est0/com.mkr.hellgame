package com.mkr.hellgame.infrastructure.impl;

import com.mkr.hellgame.infrastructure.abstraction.Job;
import com.mkr.hellgame.infrastructure.abstraction.Trigger;

public class OneTimeTrigger implements Trigger {
    private Job job;
    private boolean executed = false;

    @Override
    public long calcNextScheduledExecuteIn() {
        long result = executed ? Long.MAX_VALUE : 0;
        executed = true;
        return result;
    }

    @Override
    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
