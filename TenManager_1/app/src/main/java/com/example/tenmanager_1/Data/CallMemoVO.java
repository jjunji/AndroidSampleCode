package com.example.tenmanager_1.Data;

import io.realm.RealmObject;

/**
 * Created by 전지훈 on 2017-11-07.
 */

public class CallMemoVO extends RealmObject{

    private int id;
    private String content;
    private long regdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getRegdate() {
        return regdate;
    }

    public void setRegdate(long regdate) {
        this.regdate = regdate;
    }
}
