package com.mkr.hellgame.server;

import com.mkr.hellgame.server.core.Job;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("Server started");

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Map<String, Job> jobs = context.getBeansOfType(Job.class);

        for (Job job: jobs.values()) {
            job.Run();
        }

        try
        {
            System.in.read();
        }
        catch (Exception e)
        {
            // Do nothing.
        }
    }
}
