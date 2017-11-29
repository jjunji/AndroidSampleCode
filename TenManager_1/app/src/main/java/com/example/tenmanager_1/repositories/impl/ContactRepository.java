package com.example.tenmanager_1.repositories.impl;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.tenmanager_1.Data.CallMemoVO;
import com.example.tenmanager_1.Data.ContactGroupVO;
import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.Data.SmsGroupVO;
import com.example.tenmanager_1.Loader.ContactLoader;
import com.example.tenmanager_1.MyApplication;
import com.example.tenmanager_1.repositories.service.ContactDataSource;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by 전지훈 on 2017-11-22.
 */

public class ContactRepository implements ContactDataSource {
    private static ContactRepository INSTANCE;

    private Realm realm;
    public static ContactRepository getInstance(){
        if(INSTANCE == null){
            INSTANCE = new ContactRepository();
        }
        return INSTANCE;
    }

    public ContactRepository(){
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void initGroup() {
        RealmResults<ContactGroupVO> arContactGorup = realm.where(ContactGroupVO.class).findAll();
        if(arContactGorup.size()<2){
            realm.beginTransaction();

            ContactGroupVO contactGroupVO = realm.createObject(ContactGroupVO.class, 1);
//            contactGroupVO.setId(1);
            contactGroupVO.setName("미분류");

            ContactGroupVO contactGroupVO2 = realm.createObject(ContactGroupVO.class, 2);
//            contactGroupVO2.setId(2);
            contactGroupVO2.setName("중개업소");

            realm.commitTransaction();
        }
    }

    @Override
    public void contactCopyFromDevice() {
        ContactLoader contactLoader = new ContactLoader(MyApplication.getInstance());
        List<ContactVO> arContact = contactLoader.getContacts();

        realm.beginTransaction();
        realm.delete(ContactVO.class);
        realm.delete(CallMemoVO.class);
        realm.commitTransaction();

        ContactGroupVO contactGroupVO = realm.where(ContactGroupVO.class).equalTo("id", 1).findFirst();

        if(contactGroupVO == null){
            initGroup();
            contactGroupVO = realm.where(ContactGroupVO.class).equalTo("id", 1).findFirst();
        }

        realm.beginTransaction();
        for(int i=0; i<arContact.size(); i++){
            ContactVO tempVO = arContact.get(i);
            ContactVO contactVO = realm.createObject(ContactVO.class, i+1);
            contactVO.setName(tempVO.getName());
            contactVO.setCellPhone(tempVO.getCellPhone());
            contactVO.setGroup(contactGroupVO);
            contactVO.setArCallMemo(new RealmList<CallMemoVO>());
        }
        realm.commitTransaction();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("init", false);  // 값을 저장하면 sharedPreferences 에 true 값 저장.
        editor.commit();

        printAllContact();
    }

    @Override
    public RealmResults<ContactGroupVO> getContactGroupList() {
        RealmResults<ContactGroupVO> results = realm.where(ContactGroupVO.class).findAll();
/*        results.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<ContactGroupVO>>() {
            @Override
            public void onChange(RealmResults<ContactGroupVO> contactGroupVOs, OrderedCollectionChangeSet changeSet) {

            }
        });*/
        return results;
    }


    //    public void doCopyContact(Realm realm){
//        realm.beginTransaction(); //
//        //Log.i(TAG, "copy start");
//
//        for(int i = 0; i<datas.size(); i++){ // 주소록의 사이즈만큼 돌면서
//            String tel = datas.get(i).getTel();
//            ContactVO contactVO = realm.where(ContactVO.class).equalTo("phoneNumber", tel).findFirst();  // 번호가 기존 데이터에 있는지 검사(중복 검사)
//
//            // 중복되는 것이 없다면 추가.
//            if(contactVO == null){
//                Number maxid = realm.where(ContactVO.class).max("id");
//
//                int id = 1;
//                if(maxid != null){
//                    id = maxid.intValue()+1;
//                }
//
//                ContactVO cv = realm.createObject(ContactVO.class); // cv : 새로운 객체 생성.
//                cv.setId(id);
//                cv.setName(datas.get(i).getName());
//                cv.setCellPhone(tel); // TODO: 2017-11-10
//                //cv.setTel(datas.get(i).getTel());
//                //Log.i(TAG, "insert data is : "+cv.toString());
//            }
//        }
//        realm.commitTransaction();
//        printAllContact();
//    }
//
    private void printAllContact() {
        RealmResults<ContactVO>  results = realm.where(ContactVO.class).findAll();

        for(ContactVO contactVO :  results){
            Log.i("test", "printAllContact data is : "+contactVO.toString());
        }
    }

}
