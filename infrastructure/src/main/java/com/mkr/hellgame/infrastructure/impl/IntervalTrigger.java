package com.mkr.hellgame.infrastructure.impl;

import com.mkr.hellgame.infrastructure.abstraction.Trigger;

public class IntervalTrigger implements Trigger {
    private Runnable job;
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
    public Runnable getJob() {
        return job;
    }

    public void setJob(Runnable job) {
        this.job = job;
    }
}
