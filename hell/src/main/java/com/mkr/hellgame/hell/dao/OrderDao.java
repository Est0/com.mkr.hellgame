package com.mkr.hellgame.hell.dao;

import com.mkr.hellgame.hell.domain.Order;

import java.util.List;

public interface OrderDao {
    List<Order> getNewOrders();

    List<Order> getFinishedOrders();

    void create(Order order);
}
