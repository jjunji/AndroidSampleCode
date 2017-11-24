package com.example.tenmanager_1.Data;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 전지훈 on 2017-11-07.
 */

public class SmsVO extends RealmObject{
    @PrimaryKey
    private long id;
//    private int location;
    private String title;
    private String content;
    private SmsGroupVO group;
    private long regdate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SmsGroupVO getGroup() {
        return group;
    }

    public void setGroup(SmsGroupVO group) {
        this.group = group;
    }

//    public int getLocation() {
//        return location;
//    }

    public long getRegdate() {
        return regdate;
    }

    public void setRegdate(long regdate) {
        this.regdate = regdate;
    }
//
//    public void setLocation(int location) {
//        this.location = location;
//    }

}

