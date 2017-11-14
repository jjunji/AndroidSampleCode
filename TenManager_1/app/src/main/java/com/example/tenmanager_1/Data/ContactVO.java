package com.example.tenmanager_1.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by 전지훈 on 2017-11-07.
 */

public class ContactVO extends RealmObject{

    private int id;
    private String name;
    private String phoneNumber;
    private String Call1;
    private String Call2;
    private String address;
    private String memo;
    private String callMemo;
    private int group;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCall1() {
        return Call1;
    }

    public void setCall1(String call1) {
        Call1 = call1;
    }

    public String getCall2() {
        return Call2;
    }

    public void setCall2(String call2) {
        Call2 = call2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCallMemo() {
        return callMemo;
    }

    public void setCallMemo(String callMemo) {
        this.callMemo = callMemo;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "ContactVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tel='" + phoneNumber + '\'' +
                '}';
    }


}
