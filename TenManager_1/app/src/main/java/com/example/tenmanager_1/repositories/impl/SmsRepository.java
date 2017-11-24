package com.example.tenmanager_1.repositories.impl;

import com.example.tenmanager_1.Data.SmsGroupVO;
import com.example.tenmanager_1.repositories.service.SmsDataSource;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by 전지훈 on 2017-11-24.
 */

public class SmsRepository implements SmsDataSource{
    private Realm realm;

    public SmsRepository() {
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void initSmsGroup() {
        //RealmResults<SmsGroupVO> arSmsGroup = realm.where(SmsGroupVO.class).findAll();
        realm.beginTransaction();

        SmsGroupVO smsGroupVO = realm.createObject(SmsGroupVO.class, 1);
        smsGroupVO.setName("저장문자");

        SmsGroupVO smsGroupVO2 = realm.createObject(SmsGroupVO.class, 2);
        smsGroupVO2.setName("대표안내문자");

        SmsGroupVO smsGroupVO3 = realm.createObject(SmsGroupVO.class, 3);
        smsGroupVO3.setName("매물홍보문자");

        realm.commitTransaction();
    }

    @Override
    public RealmResults<SmsGroupVO> getSmsGroupList() {
        RealmResults<SmsGroupVO> result = realm.where(SmsGroupVO.class).findAll();
        return result;
    }

}
