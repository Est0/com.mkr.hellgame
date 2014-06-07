package com.mkr.hellgame.host;

import com.mkr.hellgame.infrastructure.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.out.println("Host started");

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        try
        {
            Executor executor = context.getBean(Executor.class);
            executor.start();
            System.in.read();
            System.out.println("Stopping executor");
            boolean success = executor.stop(2, TimeUnit.SECONDS);
            if (success) {
                System.out.println("Executor stopped successfully");
            }
            else {
                System.out.println("Executor was not stopped in time");
            }
        }
        catch (Exception e) {
        }

        System.out.println("Host stopped");
    }
}
