package com.mkr.hellgame.hell.dao;

import com.mkr.hellgame.hell.entities.Order;

public interface HellApiDao {
    void SendRequest(Order order);
}
