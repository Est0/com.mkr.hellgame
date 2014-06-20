package com.mkr.hellgame.infrastructure.abstraction;

public interface Trigger {
    long calcNextScheduledExecuteIn();

    Job getJob();
}
