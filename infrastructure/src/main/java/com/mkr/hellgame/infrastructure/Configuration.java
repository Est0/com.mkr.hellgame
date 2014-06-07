package com.mkr.hellgame.infrastructure;

import java.util.Collection;

public class Configuration {
    private long executorGranularity;
    private Collection<Trigger> triggers;

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
}
