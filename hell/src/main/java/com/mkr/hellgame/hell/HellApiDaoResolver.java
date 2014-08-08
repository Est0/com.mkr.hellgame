package com.mkr.hellgame.hell;

import com.mkr.hellgame.hell.dao.HellApiDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class HellApiDaoResolver {
    @Autowired
    private List<HellApiDao> hellApiDaos = new ArrayList<>();

    List<HellApiDao> getHellApiDaos() {
        return hellApiDaos;
    }

    void setHellApiDaos(List<HellApiDao> hellApiDaos) {
        this.hellApiDaos = hellApiDaos;
    }

    public HellApiDao resolve(String id)
    {
        for (HellApiDao hellApiDao: hellApiDaos) {
            Qualifier qualifier = hellApiDao.getClass().getAnnotation(Qualifier.class);
            if (qualifier != null && qualifier.value().equals(id)) {
                return hellApiDao;
            }
        }

        return null;
    }
}
