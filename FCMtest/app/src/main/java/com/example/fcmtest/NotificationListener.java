/*
package com.example.fcmtest;

import android.annotation.TargetApi;
import android.app.Notification;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.StringTokenizer;

*/
/**
 * Created by 전지훈 on 2017-12-01.
 *//*


@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NotificationListener extends NotificationListenerService {

    private static final String TAG = NotificationListener.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Notification Listener created!");
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void addNotification(StatusBarNotification sbn, boolean updateDash) {
        if (sbn == null) return;

        Notification mNotification = sbn.getNotification();
        Bundle extras = mNotification.extras;
        String from = null;
        String text = null;
        if (sbn != null && sbn.getPackageName().equalsIgnoreCase("com.whatsapp")) {
            from = sbn.getNotification().tickerText.toString();
            Log.d(TAG, "whatsapp>> from: " + from);
        } else if (sbn != null && sbn.getPackageName().equalsIgnoreCase("com.facebook.orca")) {
            StringTokenizer stringTokenizer = new StringTokenizer(sbn.getNotification().tickerText.toString(), ":");
            Log.d(TAG, "Ticker: " + sbn.getNotification().tickerText.toString());
            from = stringTokenizer.nextToken();
            text = stringTokenizer.nextToken();
            Log.d(TAG, "facebook>>" + "from: " + from + ", Text: " + text);

        } else if (sbn != null && sbn.getPackageName().equalsIgnoreCase("com.kakao.talk")) {
            from = extras.getString(Notification.EXTRA_TITLE);
            text = extras.getString(Notification.EXTRA_TEXT);
            Log.d(TAG, "kakao>> " + "Title: " + from + ", Text: " + text);
        }


    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        Log.d(TAG, "Notification Posted:\n");
        Log.d(TAG, "Notification=");
        addNotification(sbn, true);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.d(TAG, "Notification Removed:\n");
        if (sbn != null && sbn.getPackageName().equalsIgnoreCase("com.whatsapp")) {
//            MessageManager mManager=MessageManager.getInstance(this);
//            if (mManager!=null){
//                mManager.clearAll();
//            }
        } else if (sbn != null && sbn.getPackageName().equalsIgnoreCase("com.facebook.orca")) {

        }
    }
}

*/
