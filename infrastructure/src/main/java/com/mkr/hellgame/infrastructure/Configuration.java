package com.mkr.hellgame.infrastructure;

import java.util.List;

public interface Configuration {
    long getExecutorGranularity();

    List<Trigger> getTriggers();
}
