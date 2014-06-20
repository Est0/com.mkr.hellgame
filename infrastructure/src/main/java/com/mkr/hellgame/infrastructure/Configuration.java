package com.mkr.hellgame.infrastructure;

import com.mkr.hellgame.infrastructure.abstraction.JobRunStrategy;
import com.mkr.hellgame.infrastructure.abstraction.Trigger;

import java.util.Collection;

public class Configuration {
    private long executorGranularity;
    private Collection<Trigger> triggers;
    private JobRunStrategy jobRunStrategy;

    public long getExecutorGranularity() {
        return executorGranularity;
    }

    public void setExecutorGranularity(long executorGranularity) {
        this.executorGranularity = executorGranularity;
    }

    public Collection<Trigger> getTriggers() {
        return triggers;
    }

    public void setTriggers(Collection<Trigger> triggers) {
        this.triggers = triggers;
    }

    public JobRunStrategy getJobRunStrategy() {
        return jobRunStrategy;
    }

    public void setJobRunStrategy(JobRunStrategy jobRunStrategy) {
        this.jobRunStrategy = jobRunStrategy;
    }
}
