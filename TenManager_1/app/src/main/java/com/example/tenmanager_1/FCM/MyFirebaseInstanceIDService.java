package com.example.tenmanager_1.FCM;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by 전지훈 on 2017-12-01.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService{
    private final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        //super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
    }
}
