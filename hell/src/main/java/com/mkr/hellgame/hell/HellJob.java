package com.mkr.hellgame.hell;

import com.mkr.hellgame.hell.dao.HellApiDao;
import com.mkr.hellgame.hell.dao.TicketDao;
import com.mkr.hellgame.hell.entities.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class HellJob implements Runnable {
    @Autowired
    private TicketDao ticketDao;
    @Autowired
    private HellApiDaoResolver hellApiDaoResolver;
    private static final Logger logger = LoggerFactory.getLogger(HellJob.class);

    public TicketDao getTicketDao() {
        return ticketDao;
    }

    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    public HellApiDaoResolver getHellApiDaoResolver() {
        return hellApiDaoResolver;
    }

    public void setHellApiDaoResolver(HellApiDaoResolver hellApiDaoResolver) {
        this.hellApiDaoResolver = hellApiDaoResolver;
    }

    @Override
    public void run() {
        logger.info("HellJob Started");
        List<Ticket> newTickets = ticketDao.getNewTickets();
        logger.debug("There are {} new tickets", newTickets.size());
        List<Ticket> finishedTickets = ticketDao.getFinishedTickets();
        logger.debug("There are {} finished tickets", finishedTickets.size());

        List<Ticket> ticketsToProcess = new ArrayList<>();
        addNonOverlappingNewTickets(ticketsToProcess, newTickets);
        addRecreatedFinishedTickets(ticketsToProcess, newTickets, finishedTickets);

        for (Ticket ticket : ticketsToProcess) {
            processTicket(ticket);
        }

        logger.info("HellJob finished");
    }

    private void addNonOverlappingNewTickets(List<Ticket> ticketsToProcess, List<Ticket> newTickets) {
        for (Ticket ticket : newTickets) {
            boolean newerOverlappingTicketExists = false;
            for (Ticket otherTicket : newTickets) {
                if (ticket.getInGameUser().getId() == otherTicket.getInGameUser().getId() &&
                        ticket.getId() < otherTicket.getId()) {
                    logger.debug("Deleting ticket {} as there is at least one newer ticket");
                    newerOverlappingTicketExists = true;
                    ticketDao.delete(ticket);
                    break;
                }
            }
            if (!newerOverlappingTicketExists) {
                ticketsToProcess.add(ticket);
            }
        }
    }

    private void addRecreatedFinishedTickets(List<Ticket> ticketsToProcess, List<Ticket> newTickets, List<Ticket> finishedTickets) {
        for (Ticket finishedTicket : finishedTickets) {
            boolean newTicketExists = false;
            for (Ticket newTicket : newTickets) {
                if (newTicket.getInGameUser().getId() == finishedTicket.getInGameUser().getId()) {
                    newTicketExists = true;
                    break;
                }
            }
            if (!newTicketExists) {
                logger.debug("There is no new ticket which overlaps with finished ticket {}, therefore it will be restarted", finishedTicket);
                Ticket newTicket = new Ticket();
                newTicket.setAction(finishedTicket.getAction());
                newTicket.setDurationMilliSeconds(finishedTicket.getDurationMilliSeconds());
                newTicket.setInGameUser(finishedTicket.getInGameUser());
                ticketDao.create(newTicket);
                ticketsToProcess.add(newTicket);
            }
        }
    }

    private void processTicket(Ticket ticket) {
        String gameVariant = ticket.getInGameUser().getGameVariant().getTitle();
        HellApiDao hellApiDao = hellApiDaoResolver.resolve(gameVariant);
        if (hellApiDao != null) {
            logger.debug("Processing ticket {}", ticket);
            ticket.setStartDate(new Date());
            hellApiDao.sendRequest(ticket);
            ticketDao.update(ticket);
        } else {
            logger.warn("Cannot process ticket for GameVariant \"{}\"", gameVariant);
        }
    }
}
