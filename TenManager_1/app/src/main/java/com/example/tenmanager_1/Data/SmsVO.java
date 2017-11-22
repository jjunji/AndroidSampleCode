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
    private SmsGroupVO group;
}
