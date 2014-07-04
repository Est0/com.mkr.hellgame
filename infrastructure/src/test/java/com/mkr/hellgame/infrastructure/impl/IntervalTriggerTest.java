package com.mkr.hellgame.infrastructure.impl;

import org.junit.Assert;
import org.junit.Test;

public class IntervalTriggerTest {
    @Test
    public void calcNextScheduledExecuteIn_IfAllIsOk_ThenReturnSameValueAllTheTime() {
        final long INTERVAL = 12345;
        IntervalTrigger trigger = new IntervalTrigger();
        trigger.setInterval(INTERVAL);
        long value1 = trigger.calcNextScheduledExecuteIn();
        long value2 = trigger.calcNextScheduledExecuteIn();
        long value3 = trigger.calcNextScheduledExecuteIn();
        Assert.assertEquals(INTERVAL, value1);
        Assert.assertEquals(INTERVAL, value2);
        Assert.assertEquals(INTERVAL, value3);
    }
}
