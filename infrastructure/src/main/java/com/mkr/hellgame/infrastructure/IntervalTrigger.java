package com.mkr.hellgame.infrastructure;

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
