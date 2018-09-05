package org.serverless.dao;

import org.serverless.pojo.EarnPojo;

import java.util.HashMap;
import java.util.List;

public interface EarnDao {
    List<EarnPojo> findAllEarn();
    Integer findClickNumByUUID(String uuid);
    List<EarnPojo> findClickNumByEmail(String email);
    List<EarnPojo> findByEmail(String email);
    List<EarnPojo> getNextPage(HashMap hashMap);
    void upDateClickNum (String uuid);
    void addServer(EarnPojo earnPojo);
}
