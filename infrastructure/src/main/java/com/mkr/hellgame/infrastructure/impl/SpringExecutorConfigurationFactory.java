package com.mkr.hellgame.infrastructure.impl;

import com.mkr.hellgame.infrastructure.Configuration;
import com.mkr.hellgame.infrastructure.abstraction.ExecutorConfigurationFactory;
import com.mkr.hellgame.infrastructure.abstraction.JobRunStrategy;
import com.mkr.hellgame.infrastructure.abstraction.Trigger;

import java.util.Map;

public class SpringExecutorConfigurationFactory implements ExecutorConfigurationFactory {
    private long executorGranularity = 1000;
    private Map<String, Trigger> triggers;
    private JobRunStrategy jobRunStrategy;

    public long getExecutorGranularity() {
        return executorGranularity;
    }

    public void setExecutorGranularity(long executorGranularity) {
        this.executorGranularity = executorGranularity;
    }

    public Map<String, Trigger> getTriggers() {
        return triggers;
    }

    public void setTriggers(Map<String, Trigger> triggers) {
        this.triggers = triggers;
    }

    public JobRunStrategy getJobRunStrategy() {
        return jobRunStrategy;
    }

    public void setJobRunStrategy(JobRunStrategy jobRunStrategy) {
        this.jobRunStrategy = jobRunStrategy;
    }

    @Override
    public Configuration getConfiguration() {
        Configuration result = new Configuration();
        result.setExecutorGranularity(executorGranularity);
        result.setTriggers(triggers.values());
        result.setJobRunStrategy(jobRunStrategy);
        return result;
    }
}
