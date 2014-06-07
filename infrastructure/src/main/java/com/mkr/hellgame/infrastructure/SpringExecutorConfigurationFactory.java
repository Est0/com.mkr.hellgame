package com.mkr.hellgame.infrastructure;

import java.util.Map;

public class SpringExecutorConfigurationFactory implements ExecutorConfigurationFactory {
    private long executorGranularity = 1000;
    private Map<String, Trigger> triggers;

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

    @Override
    public Configuration getConfiguration() {
        Configuration result = new Configuration();
        result.setExecutorGranularity(executorGranularity);
        result.setTriggers(triggers.values());
        return result;
    }
}
