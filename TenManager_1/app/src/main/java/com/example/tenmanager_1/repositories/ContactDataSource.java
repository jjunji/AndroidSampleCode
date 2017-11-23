package com.example.tenmanager_1.repositories;

import com.example.tenmanager_1.Data.ContactGroupVO;

import io.realm.RealmResults;

/**
 * Created by 전지훈 on 2017-11-22.
 */

public interface ContactDataSource {
    void initGroup();
    void contactCopyFromDevice();
    RealmResults<ContactGroupVO> getContactGroupList();

}
