package com.mkr.hellgame.host;

import com.mkr.hellgame.hell.HellJob;
import com.mkr.hellgame.infrastructure.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.out.println("Host started");

        final ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        context.getAutowireCapableBeanFactory().initializeBean(new Configuration() {
            @Override
            public long getExecutorGranularity() {
                return 1000;
            }

            @Override
            public List<Trigger> getTriggers() {
                return new ArrayList<Trigger>() {{
                    add(new IntervalTrigger(5000) {{
                        setJob(new HellJob());
                    }});
                }};
            }
        }, "Configuration");

        try
        {
            Executor executor = context.getBean(Executor.class);
            executor.start();
            System.in.read();
            executor.stop(1, TimeUnit.SECONDS);
        }
        catch (Exception e)
        {
            // Do nothing.
        }

        System.out.println("Host stopped");
    }
}
