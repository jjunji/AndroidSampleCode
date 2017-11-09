package com.example.tenmanager_1.Data;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by 전지훈 on 2017-11-07.
 */

public class ContactVO extends RealmObject implements Serializable{

    private String name;
    private String tel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "name :" + name + "/n  tel :" +tel;

    }
}
