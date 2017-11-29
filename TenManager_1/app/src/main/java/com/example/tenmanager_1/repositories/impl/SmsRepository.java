package com.example.tenmanager_1.repositories.impl;

import android.util.Log;

import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.Data.SmsGroupVO;
import com.example.tenmanager_1.Data.SmsVO;
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
    public void initStoredSMS() {
        SmsGroupVO smsGroupVO = realm.where(SmsGroupVO.class).equalTo("id",1).findFirst();

        realm.beginTransaction();

        SmsVO smsVO = realm.createObject(SmsVO.class, 1);
        smsVO.setTitle("소중한 인연이 될 수 있도록 노력하겠습니다.");
        smsVO.setContent("안녕하세요. 전화주셔서 감사합니다. \n고객님과 소중한 인연이 될 수 있도록 노력하겠습니다.");
        smsVO.setGroup(smsGroupVO);
        smsVO.setRegdate(System.currentTimeMillis()); // 날짜를 기준으로 문자를 관리하는데, 초기화 시에는 같은 시간으로 저장되므로 순서를 명시하기 위해 임의로 시간 +1함.
        Log.i("asdfas ", "smsRepo ========= : " + System.currentTimeMillis());

        SmsVO smsVO2 = realm.createObject(SmsVO.class, 2);
        smsVO2.setTitle("신뢰와 믿음을 줄 수 있도록 최선을 다하겠습니다.");
        smsVO2.setContent("안녕하세요. 전화주셔서 감사합니다. \n고객님께 신뢰와 믿음을 줄 수 있도록 최선을 다하겠습니다.");
        smsVO2.setGroup(smsGroupVO);
        smsVO2.setRegdate(System.currentTimeMillis()+1);

        SmsVO smsVO3 = realm.createObject(SmsVO.class, 3);
        smsVO3.setTitle("약속 확인");
        smsVO3.setContent("안녕하세요. 전화주셔서 감사합니다. \n고객님과 **월 **일 **시 **에서 뵙기로 약속하였습니다. \n좋은 하루 되시기 바랍니다.");
        smsVO3.setGroup(smsGroupVO);
        smsVO3.setRegdate(System.currentTimeMillis()+2);

        realm.commitTransaction();

        printAllContact();
    }

    private void printAllContact() {
        RealmResults<SmsVO>  results = realm.where(SmsVO.class).findAll();

        for(SmsVO smsVO :  results){
            Log.i("test", "printAllContact data is : "+smsVO.toString());
        }
    }

    @Override
    public void initRepresentSMS() {
        realm.beginTransaction();

        SmsGroupVO smsGroupVO = realm.where(SmsGroupVO.class).equalTo("id",2).findFirst();
        SmsVO smsVO = realm.createObject(SmsVO.class, 4);
        smsVO.setTitle("항상 기억할 수 있도록 최선을 다하겠습니다.");
        smsVO.setContent("안녕하세요. 전화주셔서 감사합니다. \n고객님께서 항상 기억할 수 있도록 최선을 다하겠습니다.");
        smsVO.setGroup(smsGroupVO);
        smsVO.setRegdate(System.currentTimeMillis());

        realm.commitTransaction();
    }

    @Override
    public void initPromoteSMS() {
        realm.beginTransaction();

        SmsGroupVO smsGroupVO = realm.where(SmsGroupVO.class).equalTo("id",3).findFirst();
        SmsVO smsVO = realm.createObject(SmsVO.class, 5);
        smsVO.setTitle("고객님께서 찾는 매물과 유사한 매물이 있어 안내드립니다.");
        smsVO.setContent("안녕하세요. 고객님께서 찾는 매물과 유사한 매물이 있어 안내해드립니다.\n빠른 시간안에 계약이 성사되도록 노력하겠습니다.\n아래 링크를 클릭하시면 매물을 확인 하실 수 있습니다. 감사합니다.");
        smsVO.setGroup(smsGroupVO);
        smsVO.setRegdate(System.currentTimeMillis());

        realm.commitTransaction();

        printAllContact();
    }

    @Override
    public RealmResults<SmsGroupVO> getSmsGroupList() {
        RealmResults<SmsGroupVO> result = realm.where(SmsGroupVO.class).findAll();
        return result;
    }

}
