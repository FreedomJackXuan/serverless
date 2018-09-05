package org.serverless.service.impl;

import org.serverless.dao.EarnDao;
import org.serverless.pojo.EarnPojo;
import org.serverless.service.EarnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class EarnServiceImpl implements EarnService {
    @Autowired
    private EarnDao earnDao;

    @Override
    public List<EarnPojo> findAllEarn() {
        return earnDao.findAllEarn();
    }

    @Override
    public Integer findClickNumByUUID(String uuid) {
        return earnDao.findClickNumByUUID(uuid);
    }

    @Override
    public List<EarnPojo> findClickNumByEmail(String email) {
        return earnDao.findClickNumByEmail(email);
    }

    @Override
    public List<EarnPojo> findByEmail(String  email) {
        return earnDao.findByEmail(email);
    }

    @Override
    public List<EarnPojo> getNextPage(HashMap hashMap) {
        return earnDao.getNextPage(hashMap);
    }

    @Override
    public void upDateClickNum(String uuid) {
        earnDao.upDateClickNum(uuid);
    }

    @Override
    public void addServer(EarnPojo earnPojo) {
        earnDao.addServer(earnPojo);
    }

}
