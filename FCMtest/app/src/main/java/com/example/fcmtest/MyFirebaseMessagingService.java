package com.example.fcmtest;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by 전지훈 on 2017-12-01.
 *
 */

                            //com.google.firebase.messaging.FirebaseMessagingService
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FirebaseMsgService";
    Context context = MyApplication.getInstanc();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Intent popupIntent = new Intent(context, DialogActivity.class);
        //Bundle bundle = new Bundle();
        //popupIntent.putExtras(bundle);
        popupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(popupIntent);

        sendPushNotification(remoteMessage.getData().get("message"));
        Log.i(TAG, "onReceive() onMessageReceived");
    }

    private void sendPushNotification(String message) {
        System.out.println("received message : " + message);
        Intent intent = new Intent(this, DialogActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle("Push Title ")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri).setLights(000000255, 500, 2000)
                .setContentIntent(pendingIntent);  // -> 주석 지우면 특정 화면으로 이동 가능

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakelock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        wakelock.acquire(5000);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

    }

}