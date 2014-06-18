package com.mkr.hellgame.infrastructure;

public interface Trigger {
    long calcNextScheduledExecuteIn();

    Job getJob();
}
