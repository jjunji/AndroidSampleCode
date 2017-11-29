package com.example.tenmanager_1.repositories.service;

import com.example.tenmanager_1.Data.SmsGroupVO;

import io.realm.RealmResults;

/**
 * Created by 전지훈 on 2017-11-24.
 */

public interface SmsDataSource {
    RealmResults<SmsGroupVO> getSmsGroupList();
    void initSmsGroup();
    void initStoredSMS();
    void initRepresentSMS();
    void initPromoteSMS();
}
