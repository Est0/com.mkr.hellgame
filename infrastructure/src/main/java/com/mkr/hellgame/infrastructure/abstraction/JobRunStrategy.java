package com.mkr.hellgame.infrastructure.abstraction;

import java.util.concurrent.ExecutorService;

public interface JobRunStrategy {
    void run(ExecutorService executorService, Runnable job);
}
