package com.mkr.hellgame.hell;

import com.mkr.hellgame.core.Job;
import org.springframework.stereotype.Component;

@Component
public class HellJob implements Job {
    @Override
    public void Run() {
        System.out.println("HellJob!");
    }
}
