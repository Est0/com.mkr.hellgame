package com.mkr.hellgame.host;

import com.mkr.hellgame.infrastructure.Executor;
import com.mkr.hellgame.infrastructure.abstraction.ExecutorConfigurationFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Host started");

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        try {
            ExecutorConfigurationFactory executorConfigurationFactory = context.getBean(ExecutorConfigurationFactory.class);
            Executor executor = new Executor(executorConfigurationFactory.getConfiguration());
            executor.start();
            System.in.read();
            logger.info("Stopping executor...");
            boolean success = executor.stop(2, TimeUnit.SECONDS);
            if (success) {
                logger.info("Executor stopped successfully");
            } else {
                logger.info("Executor was not stopped in time");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("Host stopped");
    }
}
