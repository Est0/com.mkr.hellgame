package com.mkr.hellgame.hell.dao;

import com.mkr.hellgame.hell.entities.Ticket;

import java.util.List;

public interface TicketDao {
    List<Ticket> getNewTickets();

    List<Ticket> getFinishedTickets();

    void create(Ticket ticket);

    void update(Ticket ticket);

    void delete(Ticket ticket);
}
