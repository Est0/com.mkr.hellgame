package com.mkr.hellgame.hell;

import com.mkr.hellgame.hell.dao.HellApiDao;
import com.mkr.hellgame.hell.entities.Ticket;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Arrays;

public class HellApiDaoResolverTest {
    private HellApiDaoResolver sut;

    @Qualifier("test")
    private class HellApiDaoMock implements HellApiDao {
        @Override
        public void sendRequest(Ticket ticket) {
        }
    }

    @Before
    public void setUp() throws Exception {
        sut = new HellApiDaoResolver();
    }

    @Test
    public void resolve_GivenValidId_ThenReturnCorrespondingHellApiDaoInstance() throws Exception {
        // given
        HellApiDao hellApiDao = new HellApiDaoMock();
        sut.setHellApiDaos(Arrays.asList(hellApiDao));

        // when
        HellApiDao result = sut.resolve("test");

        // then
        Assert.assertEquals(hellApiDao, result);
    }

    @Test
    public void resolve_GivenNoDaosAvailable_ThenAlwaysReturnNull() throws Exception {
        // given

        // when
        HellApiDao result = sut.resolve("test");

        // then
        Assert.assertNull(result);
    }

    @Test
    public void resolve_GivenInvalidId_ThenReturnNull() throws Exception {
        // given
        HellApiDao hellApiDao = new HellApiDaoMock();
        sut.setHellApiDaos(Arrays.asList(hellApiDao));

        // when
        HellApiDao result = sut.resolve("test1");

        // then
        Assert.assertNull(result);
    }

    @Test
    public void resolve_GivenEmptydId_ThenReturnNull() throws Exception {
        // given
        HellApiDao hellApiDao = new HellApiDaoMock();
        sut.setHellApiDaos(Arrays.asList(hellApiDao));

        // when
        HellApiDao result = sut.resolve("");

        // then
        Assert.assertNull(result);
    }

    @Test
    public void resolve_GivenNullId_ThenReturnNull() throws Exception {
        // given
        HellApiDao hellApiDao = new HellApiDaoMock();
        sut.setHellApiDaos(Arrays.asList(hellApiDao));

        // when
        HellApiDao result = sut.resolve(null);

        // then
        Assert.assertNull(result);
    }
}
