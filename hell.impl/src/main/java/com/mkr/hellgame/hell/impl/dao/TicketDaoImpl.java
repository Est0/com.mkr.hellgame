package com.mkr.hellgame.hell.impl.dao;

import com.mkr.hellgame.hell.dao.TicketDao;
import com.mkr.hellgame.hell.entities.Ticket;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TicketDaoImpl extends DbCrudDaoBase<Ticket> implements TicketDao {
    public TicketDaoImpl() {
        super(Ticket.class);
    }

    @Override
    public List<Ticket> getNewTickets() {
        return new ArrayList<>();
    }

    @Override
    public List<Ticket> getFinishedTickets() {
        return new ArrayList<>();
    }
}
