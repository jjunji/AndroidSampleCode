package com.example.tenmanager_1.Fragment.FindContactFragment;

import java.io.Serializable;

/**
 * Created by 전지훈 on 2017-11-28.
 */
                                // unable to marshal value 에러 -> 객체가 직렬화 되어 있지 않아 발생한 에러 -> implements Serializable
public class SelectedDataModel implements Serializable{
    private Long id;
    private String phoneNumber;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SelectedDataModel{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
