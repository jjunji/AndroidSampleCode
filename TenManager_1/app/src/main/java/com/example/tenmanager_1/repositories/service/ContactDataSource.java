package com.example.tenmanager_1.repositories.service;

import com.example.tenmanager_1.Data.ContactGroupVO;
import com.example.tenmanager_1.Data.SmsGroupVO;

import io.realm.RealmResults;

/**
 * Created by 전지훈 on 2017-11-22.
 */

public interface ContactDataSource {
    void initGroup();
    void contactCopyFromDevice();
    RealmResults<ContactGroupVO> getContactGroupList();
}
