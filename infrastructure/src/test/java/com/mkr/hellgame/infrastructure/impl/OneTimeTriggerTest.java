package com.mkr.hellgame.infrastructure.impl;

import com.mkr.hellgame.infrastructure.abstraction.Trigger;
import org.junit.Assert;
import org.junit.Test;

public class OneTimeTriggerTest {
    @Test
    public void triggerJobExecutionImmediatelyForTheFirstTime() {
        Trigger trigger = new OneTimeTrigger();
        long value = trigger.calcNextScheduledExecuteIn();
        Assert.assertEquals(value, 0);
    }

    @Test
    public void triggerJobExecutionAfterLongMaxValueForSecondAndSubsequesntTimes() {
        Trigger trigger = new OneTimeTrigger();
        trigger.calcNextScheduledExecuteIn();
        long value2 = trigger.calcNextScheduledExecuteIn();
        long value3 = trigger.calcNextScheduledExecuteIn();
        Assert.assertEquals(Long.MAX_VALUE, value2);
        Assert.assertEquals(Long.MAX_VALUE, value3);
    }
}
