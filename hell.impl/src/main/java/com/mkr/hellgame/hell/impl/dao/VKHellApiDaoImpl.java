package com.mkr.hellgame.hell.impl.dao;

import com.mkr.hellgame.hell.dao.HellApiDao;
import com.mkr.hellgame.hell.entities.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("VK")
public class VKHellApiDaoImpl implements HellApiDao {
    private static final Logger logger = LoggerFactory.getLogger(VKHellApiDaoImpl.class);

    @Override
    public void sendRequest(Ticket ticket) {
        logger.info("VKHellApiDaoImpl.sendRequest invoked with {} and performed action: \"{}\"", ticket, ticket.getAction());
    }
}
