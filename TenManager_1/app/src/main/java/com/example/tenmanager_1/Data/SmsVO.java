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
    private String title;
    private String content;
    private SmsGroupVO group; // 그룹 1: 저장문자, 그룹 2: 대표안내문자, 그룹 3 : 매물홍보문자
    private long regdate; // 저장문자 순서 변경을 위해 작성 시간 저장용(id값을 기본키로 바꾸면서 이동 못하게됨.)

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

    public long getRegdate() {
        return regdate;
    }

    public void setRegdate(long regdate) {
        this.regdate = regdate;
    }

}

