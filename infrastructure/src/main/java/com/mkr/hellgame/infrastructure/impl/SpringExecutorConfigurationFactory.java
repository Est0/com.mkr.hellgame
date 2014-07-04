package com.mkr.hellgame.infrastructure.impl;

import com.mkr.hellgame.infrastructure.Configuration;
import com.mkr.hellgame.infrastructure.ExecutorServiceType;
import com.mkr.hellgame.infrastructure.abstraction.ExecutorConfigurationFactory;
import com.mkr.hellgame.infrastructure.abstraction.JobRunStrategy;
import com.mkr.hellgame.infrastructure.abstraction.Trigger;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SpringExecutorConfigurationFactory implements ExecutorConfigurationFactory {
    private long executorGranularity = 1000;
    private ExecutorServiceType executorServiceType;
    private int fixedThreadPoolAmount = 1;
    private Map<String, Trigger> triggers;
    private JobRunStrategy jobRunStrategy;

    public long getExecutorGranularity() {
        return executorGranularity;
    }

    public void setExecutorGranularity(long executorGranularity) {
        this.executorGranularity = executorGranularity;
    }

    public ExecutorServiceType getExecutorServiceType() {
        return executorServiceType;
    }

    public void setExecutorServiceType(ExecutorServiceType executorServiceType) {
        this.executorServiceType = executorServiceType;
    }

    public int getFixedThreadPoolAmount() {
        return fixedThreadPoolAmount;
    }

    public void setFixedThreadPoolAmount(int fixedThreadPoolAmount) {
        this.fixedThreadPoolAmount = fixedThreadPoolAmount;
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
        ExecutorService executorService;
        switch (executorServiceType) {
            case FIXED_THREAD_POOL:
                executorService = Executors.newFixedThreadPool(fixedThreadPoolAmount);
                break;

            case CACHED_THREAD_POOL:
                executorService = Executors.newCachedThreadPool();
                break;
            case SINGLE_THREAD_POOL:
            default:
                executorService = Executors.newSingleThreadExecutor();
                break;
        }
        result.setExecutorService(executorService);
        result.setTriggers(triggers.values());
        result.setJobRunStrategy(jobRunStrategy);
        return result;
    }
}
