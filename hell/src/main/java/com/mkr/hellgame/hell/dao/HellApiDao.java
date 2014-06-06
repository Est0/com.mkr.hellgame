package com.mkr.hellgame.hell.dao;

import com.mkr.hellgame.hell.domain.Order;

public interface HellApiDao {
    void SendRequest(Order order);
}
