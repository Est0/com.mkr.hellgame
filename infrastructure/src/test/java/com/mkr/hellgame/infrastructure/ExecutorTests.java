package com.mkr.hellgame.infrastructure;

import com.mkr.hellgame.infrastructure.annotation.Loggable;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class ExecutorTests {
    @Loggable
    private Logger logger;

    @Test
    public void simple() {
        logger.info("It works!");
        Assert.assertEquals(1,1);
    }
}
