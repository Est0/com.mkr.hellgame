package com.mkr.hellgame.hell.impl.dao;

import com.mkr.hellgame.hell.dao.TicketDao;
import com.mkr.hellgame.hell.entities.Ticket;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class TicketDaoImpl extends DbCrudDaoBase<Ticket> implements TicketDao {
    public TicketDaoImpl() {
        super(Ticket.class);
    }

    @Override
    public List<Ticket> getNewTickets() {
        return sessionFactory.getCurrentSession().createQuery("from Ticket where startDate is null").list();
    }

    @Override
    public List<Ticket> getFinishedTickets() {
        return sessionFactory.getCurrentSession().createQuery("from Ticket where startDate < current_timestamp - duration").list();
    }
}
