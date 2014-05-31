package com.mkr.hellgame.hell;

import com.mkr.hellgame.hell.dao.HellApiDao;
import com.mkr.hellgame.hell.dao.OrderDao;
import com.mkr.hellgame.hell.entities.Order;
import com.mkr.hellgame.server.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class HellJob implements Job {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private List<HellApiDao> hellApiDaos;

    @Override
    public void Run() {
        List<Order> newOrders = orderDao.getNewOrders();
        List<Order> finishedOrders = orderDao.getFinishedOrders();

        Date now = new Date();
        for (Order order: finishedOrders) {
            order.setEndDate(now);
            boolean newOrderExists = false;
            for (Order newOrder: newOrders) {
                if (newOrder.getInGameUserId() == order.getInGameUserId()) {
                    newOrderExists = true;
                    break;
                }
            }
            if (!newOrderExists) {
                newOrders.add(new Order()
                {{
                        setId(3);
                        setInGameUserId(2);
                        setDurationMilliSeconds(1000);
                    }});
            }
        }

        for (Order order: newOrders) {
            order.setStartDate(now);
            hellApiDaos.get(0).SendRequest(order);
        }
    }
}
