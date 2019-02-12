package com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.impl;

import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dao.MTUserDao;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.entity.MTUser;
import com.twentytwodegreescelcious.telegrambot.meticuloustranslator.core.domain.dbo.service.MTUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by twentytwodegreescelcious on 2/12/2019.
 */
@Service
public class MTUserServiceImpl implements MTUserService {

    @Autowired
    private MTUserDao mtUserDao;

    @Override
    public MTUser createMTUser(MTUser mtUser) {
        return mtUserDao.save(mtUser);
    }

    @Override
    public MTUser getMTUser(Integer id) {
        Optional<MTUser> optional= mtUserDao.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else return null;
    }

    @Override
    public MTUser editMTUser(MTUser mtUser) {
        return mtUserDao.saveAndFlush(mtUser);
    }

    @Override
    public void deleteMTUser(MTUser mtUser) {
        mtUserDao.delete(mtUser);
    }

    @Override
    public void deleteMTUser(Integer id) {
        mtUserDao.deleteById(id);
    }

    @Override
    public List<MTUser> getAllMTUsers() {
        return mtUserDao.findAll();
    }

    @Override
    public Long countMTUsers() {
        return mtUserDao.count();
    }
}
