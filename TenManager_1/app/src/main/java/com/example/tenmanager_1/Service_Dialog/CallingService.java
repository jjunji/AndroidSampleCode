package com.example.tenmanager_1.Service_Dialog;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by 전지훈 on 2017-11-20.
 */

public class CallingService extends Service  {

    public static final String EXTRA_CALL_NUMBER = "call_number";
    //protected View rootView;
    //Context context = getApplicationContext();
    String call_number;

    //WindowManager.LayoutParams params;
    //private WindowManager windowManager;

    @Override
    public IBinder onBind(Intent intent) {
        // Not used
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent popupIntent = new Intent(this, DialogActivity.class);
        popupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(popupIntent);

        return super.onStartCommand(intent, flags, startId);
    }

    private void setExtra(Intent intent) {
        if (intent == null) {
            //removePopup();
            return;
        }
        call_number = intent.getStringExtra(EXTRA_CALL_NUMBER);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //removePopup();
    }

/*    public void removePopup() {
        if (rootView != null && windowManager != null) windowManager.removeView(rootView);
    }*/
}
