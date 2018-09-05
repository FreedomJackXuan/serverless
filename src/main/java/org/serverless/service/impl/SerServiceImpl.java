package org.serverless.service.impl;

import org.serverless.dao.ServiceDao;
import org.serverless.pojo.ServicePojo;
import org.serverless.service.SerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class SerServiceImpl implements SerService {
    @Autowired
    private ServiceDao serviceDao;

    @Override
    public List<ServicePojo> findAllService() {
        return serviceDao.findAllService();
    }

    @Override
    public List<ServicePojo> findServiceByEmail(String email) {
        return serviceDao.findServiceByEmail(email);
    }

    @Override
    public List<ServicePojo> findServiceByService(String sevicename) {
        return serviceDao.findServiceByService(sevicename);
    }

    @Override
    public ServicePojo findServiceByUUID(String uuid) {
        return serviceDao.findServiceByUUID(uuid);
    }

    @Override
    public String findPathByUUID(String uuid) {
        return serviceDao.findPathByUUID(uuid);
    }

    @Override
    public int insertService(ServicePojo servicePojo) {
        return serviceDao.insertService(servicePojo);
    }

    @Override
    public int updateService(ServicePojo servicePojo) {
        return serviceDao.updateService(servicePojo);
    }

    @Override
    public List<ServicePojo> getNextPage(HashMap hashMap) {
        return serviceDao.getNextPage(hashMap);
    }

}
