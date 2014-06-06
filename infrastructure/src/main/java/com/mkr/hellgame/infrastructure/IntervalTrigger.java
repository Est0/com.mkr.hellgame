package com.mkr.hellgame.infrastructure;

public class IntervalTrigger implements Trigger {
    private Job job;
    private final long interval;

    public IntervalTrigger(long interval) {
        this.interval = interval;
    }

    @Override
    public long nextScheduledExecuteIn() {
        return interval;
    }

    @Override
    public Job getJob() {
        return job;
    }

    @Override
    public void setJob(Job job) {
        this.job = job;
    }
}
