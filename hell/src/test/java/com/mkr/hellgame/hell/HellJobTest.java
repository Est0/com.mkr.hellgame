package com.mkr.hellgame.hell;

import com.mkr.hellgame.hell.dao.HellApiDao;
import com.mkr.hellgame.hell.dao.TicketDao;
import com.mkr.hellgame.hell.entities.GameVariant;
import com.mkr.hellgame.hell.entities.InGameUser;
import com.mkr.hellgame.hell.entities.Ticket;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class HellJobTest {
    private final static String DEFAULT_ACTION_TITLE = "some action";
    private final static long DEFAULT_ACTION_DURATION = 1;

    private HellJob sut = new HellJob();
    private HellApiDao hellApiDao;
    private TicketDao ticketDao;
    private HellApiDaoResolver hellApiDaoResolver;

    @Before
    public void setUp() throws Exception {
        sut = new HellJob();

        hellApiDao = mock(HellApiDao.class);
        ticketDao = mock(TicketDao.class);
        hellApiDaoResolver = mock(HellApiDaoResolver.class);

        when(ticketDao.getNewTickets()).thenReturn(new ArrayList<Ticket>());
        when(ticketDao.getFinishedTickets()).thenReturn(new ArrayList<Ticket>());
        when(hellApiDaoResolver.resolve(any(String.class))).thenReturn(hellApiDao);

        sut.setTicketDao(ticketDao);
        sut.setHellApiDaoResolver(hellApiDaoResolver);
    }

    @Test
    public void run_GivenNoFinishedOrNewTickets_ThenDoNothing() throws Exception {
        // given

        // when
        sut.run();

        // then
        verify(hellApiDao, never()).sendRequest(any(Ticket.class));
        verify(ticketDao, never()).create(any(Ticket.class));
        verify(ticketDao, never()).update(any(Ticket.class));

    }

    @Test
    public void run_GivenOneFinishedTicketAndNoNewTickets_ThenRestartFinishedTicket() throws Exception {
        // given
        Ticket ticket = createDummyTicket();
        DateTime now = DateTime.now();
        ticket.setStartDate(now.minus(DEFAULT_ACTION_DURATION).toDate());

        when(ticketDao.getFinishedTickets()).thenReturn(Arrays.asList(ticket));
        ArgumentCaptor<Ticket> apiTicketCaptor = ArgumentCaptor.forClass(Ticket.class);
        ArgumentCaptor<Ticket> daoTicketCaptor = ArgumentCaptor.forClass(Ticket.class);

        // when
        sut.run();

        // then
        verify(hellApiDao, times(1)).sendRequest(apiTicketCaptor.capture());

        Ticket capturedTicket = apiTicketCaptor.getValue();
        Assert.assertThat(capturedTicket.getAction(), is(equalTo(DEFAULT_ACTION_TITLE)));
        Assert.assertThat(capturedTicket.getDurationMilliSeconds(), is(equalTo(DEFAULT_ACTION_DURATION)));
        Assert.assertThat(capturedTicket.getStartDate(), is(greaterThanOrEqualTo(now.toDate())));
        Assert.assertThat(capturedTicket.getInGameUser(), is(equalTo(ticket.getInGameUser())));

        verify(ticketDao, times(1)).create(daoTicketCaptor.capture());
        Assert.assertThat(daoTicketCaptor.getValue(), is(equalTo(capturedTicket)));
    }

    @Test
    public void run_GivenNoFinishedTicketsAndOneNewTicket_ThenStartTheNewTicket() throws Exception {
        // given
        Ticket ticket = createDummyTicket();

        when(ticketDao.getNewTickets()).thenReturn(Arrays.asList(ticket));
        ArgumentCaptor<Ticket> apiTicketCaptor = ArgumentCaptor.forClass(Ticket.class);
        ArgumentCaptor<Ticket> daoTicketCaptor = ArgumentCaptor.forClass(Ticket.class);

        Date now = new Date();

        // when
        sut.run();

        // then
        verify(hellApiDao, times(1)).sendRequest(apiTicketCaptor.capture());

        Ticket capturedTicket = apiTicketCaptor.getValue();
        Assert.assertThat(capturedTicket, is(equalTo(ticket)));
        Assert.assertThat(capturedTicket.getStartDate(), is(greaterThanOrEqualTo(now)));

        verify(ticketDao, times(1)).update(daoTicketCaptor.capture());
        Assert.assertThat(daoTicketCaptor.getValue(), is(equalTo(capturedTicket)));
    }

    @Test
    public void run_GivenOneFinishedAndOneNewTicket_ThenStartTheNewTicket() throws Exception {
        // given
        Ticket finishedTicket = createDummyTicket();
        DateTime now = DateTime.now();
        finishedTicket.setStartDate(now.minus(DEFAULT_ACTION_DURATION).toDate());

        Ticket newTicket = createDummyTicket();
        newTicket.setAction("some another action");

        when(ticketDao.getFinishedTickets()).thenReturn(Arrays.asList(finishedTicket));
        when(ticketDao.getNewTickets()).thenReturn(Arrays.asList(newTicket));
        ArgumentCaptor<Ticket> apiTicketCaptor = ArgumentCaptor.forClass(Ticket.class);
        ArgumentCaptor<Ticket> daoTicketCaptor = ArgumentCaptor.forClass(Ticket.class);

        // when
        sut.run();

        // then
        verify(hellApiDao, times(1)).sendRequest(apiTicketCaptor.capture());

        Ticket capturedTicket = apiTicketCaptor.getValue();
        Assert.assertThat(capturedTicket, is(equalTo(newTicket)));
        Assert.assertThat(capturedTicket.getStartDate(), is(greaterThanOrEqualTo(now.toDate())));

        verify(ticketDao, times(1)).update(daoTicketCaptor.capture());
        Assert.assertThat(daoTicketCaptor.getValue(), is(equalTo(capturedTicket)));
    }

    @Test
    public void run_GivenThereIsNoImplForGameVariant_ThenDoNothing() throws Exception {
        // given
        Ticket ticket = createDummyTicket();
        when(ticketDao.getNewTickets()).thenReturn(Arrays.asList(ticket));
        when(hellApiDaoResolver.resolve(any(String.class))).thenReturn(null);

        // when
        sut.run();

        // then
        verify(hellApiDao, never()).sendRequest(any(Ticket.class));
        verify(ticketDao, never()).create(any(Ticket.class));
        verify(ticketDao, never()).update(any(Ticket.class));
    }

    @Test
    public void run_GivenNoFinishedTicketsAndMultipleOverlappingNewTickets_ThenLeaveOnlyLatestNewTicket() throws Exception {
        // given
        Ticket ticket1 = createDummyTicket();
        ticket1.setId(1);
        Ticket ticket2 = createDummyTicket();
        ticket2.setId(2);
        Ticket ticket3 = createDummyTicket();
        ticket3.setId(3);

        when(ticketDao.getNewTickets()).thenReturn(Arrays.asList(ticket1, ticket2, ticket3));
        ArgumentCaptor<Ticket> apiTicketCaptor = ArgumentCaptor.forClass(Ticket.class);
        ArgumentCaptor<Ticket> daoUpdateTicketCaptor = ArgumentCaptor.forClass(Ticket.class);
        ArgumentCaptor<Ticket> daoDeleteTicketCaptor = ArgumentCaptor.forClass(Ticket.class);

        // when
        sut.run();

        // then
        verify(hellApiDao, times(1)).sendRequest(apiTicketCaptor.capture());
        Assert.assertThat(apiTicketCaptor.getValue().getId(), is(equalTo(ticket3.getId())));

        verify(ticketDao, times(1)).update(daoUpdateTicketCaptor.capture());
        Assert.assertThat(daoUpdateTicketCaptor.getValue(), is(equalTo(apiTicketCaptor.getValue())));

        verify(ticketDao, times(2)).delete(daoDeleteTicketCaptor.capture());
        for (Ticket ticket : daoDeleteTicketCaptor.getAllValues()) {
            Assert.assertThat(ticket.getId(), is(lessThan(ticket3.getId())));
        }
    }

    private Ticket createDummyTicket() {
        Ticket ticket = new Ticket();
        ticket.setAction(DEFAULT_ACTION_TITLE);
        ticket.setDurationMilliSeconds(DEFAULT_ACTION_DURATION);

        GameVariant gameVariant = new GameVariant();
        InGameUser inGameUser = new InGameUser();
        inGameUser.setId(1);
        inGameUser.setGameVariant(gameVariant);
        ticket.setInGameUser(inGameUser);

        return ticket;
    }
}
