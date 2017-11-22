package com.example.tenmanager_1.Data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 전지훈 on 2017-11-22.
 */

public class ContactGroupVO extends RealmObject{
    @PrimaryKey
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
        return "ContactGroupVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
