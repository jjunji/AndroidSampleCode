package com.example.tenmanager_1.Data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 전지훈 on 2017-11-07.
 */

public class CallMemoVO extends RealmObject{
    @PrimaryKey
    private int id;
    private ContactVO contactVO;
    private String content;
    private long regdate;

    public ContactVO getContactVO() {
        return contactVO;
    }

    public void setContactVO(ContactVO contactVO) {
        this.contactVO = contactVO;
    }

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

    @Override
    public String toString() {
        return "CallMemoVO{" +
                "id=" + id +
                ", contactVO=" + contactVO +
                ", content='" + content + '\'' +
                ", regdate=" + regdate +
                '}';
    }
}
