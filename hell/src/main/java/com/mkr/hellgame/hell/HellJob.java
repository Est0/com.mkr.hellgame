package com.mkr.hellgame.hell;

import com.mkr.hellgame.hell.dao.HellApiDao;
import com.mkr.hellgame.hell.dao.OrderDao;
import com.mkr.hellgame.hell.domain.Order;
import com.mkr.hellgame.infrastructure.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public void run() {
        while (true) {
            List<Order> newOrders = orderDao.getNewOrders();
            List<Order> finishedOrders = orderDao.getFinishedOrders();

            Date now = new Date();
            for (Order finishedOrder : finishedOrders) {
                finishedOrder.setEndDate(now);
                boolean newOrderExists = false;
                for (Order newOrder: newOrders) {
                    if (newOrder.getInGameUserId() == finishedOrder.getInGameUserId()) {
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
                HellApiDao hellApiDao = null;
                for (HellApiDao hellApiDao: hellApiDaos) {
                    Qualifier qualifier = hellApiDao.getClass().getAnnotation(Qualifier.class);
                    if (qualifier != null && qualifier.value() == order.get)
                }
                hellApiDaos.get(0).SendRequest(order);
            }

            try {
                Thread.sleep(10000);
            }
            catch (InterruptedException e) {
                return;
            }
        }
    }
}
