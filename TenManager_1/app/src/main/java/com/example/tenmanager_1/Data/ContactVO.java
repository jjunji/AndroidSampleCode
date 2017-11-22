package com.example.tenmanager_1.Data;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 전지훈 on 2017-11-07.
 */

public class ContactVO extends RealmObject{
    @PrimaryKey
    private long id;
    private String name;
    private String tel1;
    private String tel2;
    private String cellPhone;
    private ContactGroupVO group;
    private String address;
    private String memo;
    private RealmList<CallMemoVO> arCallMemo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public ContactGroupVO getGroup() {
        return group;
    }

    public void setGroup(ContactGroupVO group) {
        this.group = group;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RealmList<CallMemoVO> getArCallMemo() {
        return arCallMemo;
    }

    public void setArCallMemo(RealmList<CallMemoVO> arCallMemo) {
        this.arCallMemo = arCallMemo;
    }

    @Override
    public String toString() {
        return "ContactVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tel1='" + tel1 + '\'' +
                ", tel2='" + tel2 + '\'' +
                ", cellPhone='" + cellPhone + '\'' +
                ", contactGroupVO=" + group +
                ", address='" + address + '\'' +
                ", memo='" + memo + '\'' +
                ", arCallMemo=" + arCallMemo +
                '}';
    }
}
