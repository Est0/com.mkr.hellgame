package com.mkr.hellgame.hell.impl.dao;

import com.mkr.hellgame.hell.dao.OrderDao;
import com.mkr.hellgame.hell.entities.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class OrderDaoImpl implements OrderDao {
    @Override
    public List<Order> getNewOrders() {
        List<Order> result = new ArrayList<Order>();
        result.add(new Order()
            {{
                setId(1);
                setInGameUserId(1);
                setDurationMilliSeconds(1000);
            }});
        return result;
    }

    @Override
    public List<Order> getFinishedOrders() {
        List<Order> result = new ArrayList<Order>();
        result.add(new Order()
            {{
                setId(2);
                setInGameUserId(1);
                setDurationMilliSeconds(1000);
                setStartDate(new Date(new Date().getTime() - 1001));
            }});
        result.add(new Order()
            {{
                setId(3);
                setInGameUserId(2);
                setDurationMilliSeconds(1000);
                setStartDate(new Date(new Date().getTime() - 1001));
            }});
        return result;
    }
}
