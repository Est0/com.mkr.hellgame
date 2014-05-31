package com.mkr.hellgame.hell.impl.dao;

import com.mkr.hellgame.hell.dao.HellApiDao;
import com.mkr.hellgame.hell.entities.Order;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("VK")
public class VKHellApiDaoImpl implements HellApiDao {
    @Override
    public void SendRequest(Order order) {
    }
}
