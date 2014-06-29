package com.mkr.hellgame.infrastructure.impl;

import com.mkr.hellgame.infrastructure.abstraction.Job;
import com.mkr.hellgame.infrastructure.abstraction.Trigger;

public class IntervalTrigger implements Trigger {
    private Job job;
    private long interval;

    @Override
    public long calcNextScheduledExecuteIn() {
        return interval;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    @Override
    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
