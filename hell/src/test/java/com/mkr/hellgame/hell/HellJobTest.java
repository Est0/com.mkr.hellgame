package com.mkr.hellgame.hell;

import com.mkr.hellgame.hell.dao.HellApiDao;
import com.mkr.hellgame.hell.dao.OrderDao;
import com.mkr.hellgame.hell.domain.InGameUser;
import com.mkr.hellgame.hell.domain.Order;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.*;

public class HellJobTest {
    private HellJob sut;
    private HellApiDao hellApiDao;
    private OrderDao orderDao;

    @Before
    public void setUp() throws Exception {
        sut = new HellJob();
        hellApiDao = Mockito.mock(HellApiDao.class);
        orderDao = Mockito.mock(OrderDao.class);
        sut.setHellApiDaos(Arrays.asList(hellApiDao));
        sut.setOrderDao(orderDao);
    }

    @Test
    public void run_WhenThereAreNoOrders_DoNothing() throws Exception {
        // given
        when(orderDao.getNewOrders()).thenReturn(new ArrayList<Order>());
        when(orderDao.getFinishedOrders()).thenReturn(new ArrayList<Order>());

        // when
        sut.run();

        // then
        verify(hellApiDao, never()).SendRequest(any(Order.class));
        verify(orderDao, never()).create(any(Order.class));
    }

    @Test
    public void run_WhenOrderFinishesAndNoNewOrders_RecreateSameOrder() throws Exception {
        // given
        Order order = new Order();
        order.setId(1);
        DateTime now = new DateTime().minus(1);
        order.setStartDate(now.toDate());
        order.setDurationMilliSeconds(1);
        InGameUser inGameUser = new InGameUser();
        inGameUser.se
        order.setInGameUser();
        order.setAction("some action");

        when(orderDao.getFinishedOrders()).thenReturn()

        // when
        sut.run();

        // then
        verify(hellApiDao, times(1)).SendRequest(any(Order.class));
        verify(orderDao, times(1)).create(any(Order.class));
    }
}
