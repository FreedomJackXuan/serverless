package org.serverless.service;

import org.serverless.pojo.ServicePojo;

import java.util.HashMap;
import java.util.List;

public interface SerService {
    List<ServicePojo> findAllService();
    List<ServicePojo> findServiceByEmail(String email);
    List<ServicePojo> findServiceByService(String sevicename);
    ServicePojo findServiceByUUID(String uuid);
    String findPathByUUID(String uuid);
    int insertService(ServicePojo servicePojo);
    int updateService(ServicePojo servicePojo);
    List<ServicePojo> getNextPage(HashMap hashMap);
}
