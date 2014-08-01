package com.mkr.hellgame.hell;

import com.mkr.hellgame.hell.dao.HellApiDao;
import com.mkr.hellgame.hell.dao.OrderDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HellJob implements Runnable {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private List<HellApiDao> hellApiDaos;
    private Logger logger = LoggerFactory.getLogger(HellJob.class);

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public List<HellApiDao> getHellApiDaos() {
        return hellApiDaos;
    }

    public void setHellApiDaos(List<HellApiDao> hellApiDaos) {
        this.hellApiDaos = hellApiDaos;
    }

    @Override
    public void run() {
        logger.debug("Hell yea!");
        throw new RuntimeException("asdf");
        /*List<Order> newOrders = orderDao.getNewOrders();
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
            for (HellApiDao hellApiDao: hellApiDaos) {
                Qualifier qualifier = hellApiDao.getClass().getAnnotation(Qualifier.class);
                if (qualifier != null && qualifier.value() == "VK") {
                    hellApiDao.SendRequest(order);
                    break;
                }
            }
        }*/
    }
}
