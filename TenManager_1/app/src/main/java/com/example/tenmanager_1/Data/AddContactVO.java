
package com.example.tenmanager_1.Data;

import io.realm.RealmObject;

/**
 * Created by 전지훈 on 2017-11-10.
 */

public class AddContactVO extends RealmObject{
    private long id;
    private String Name;
    private String Call1;
    private String Call2;
    private String phoneNumber;
    private String address;
    private String memo;
    private String callMemo;
    private int group;

    @Override
    public String toString() {
        return "AddContactVO{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", Call1='" + Call1 + '\'' +
                ", Call2='" + Call2 + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", memo='" + memo + '\'' +
                ", callMemo='" + callMemo + '\'' +
                ", group=" + group +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
}
