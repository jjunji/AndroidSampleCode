package com.example.tenmanager_1.Data;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 전지훈 on 2017-11-30.
 */

public class CallHistoryVO extends RealmObject{
    @PrimaryKey
    private long id;
    private String phoneNumber;
    private int type;
    private long date;
    private String callMemo;
    private ContactVO contactVO;
    //private RealmList<ContactVO> contactVORealmList;


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ContactVO getContactVO() {
        return contactVO;
    }

    public void setContactVO(ContactVO contactVO) {
        this.contactVO = contactVO;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getCallMemo() {
        return callMemo;
    }

    public void setCallMemo(String callMemo) {
        this.callMemo = callMemo;
    }

    @Override
    public String toString() {
        return "CallHistoryVO{" +
                "id=" + id +
                ", type=" + type +
                ", date=" + date +
                ", callMemo='" + callMemo + '\'' +
                ", contactVO=" + contactVO +
                '}';
    }
}
