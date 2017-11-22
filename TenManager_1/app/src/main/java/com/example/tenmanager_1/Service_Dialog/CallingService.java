package com.example.tenmanager_1.Service_Dialog;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by 전지훈 on 2017-11-20.
 */

public class CallingService extends Service {
    public static final String TAG = "PHONE STATE";
    private static String mLastState;
    //BroadcastReceiver mReceiver;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        int flag = 0;

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "onReceive()");

            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);  //
            Log.i(TAG, "state =============== " + state);

            if (state.equals("RINGING")) {
                flag = 1;
            } else if (state.equals("OFFHOOK")) {
                flag = 2;
            }else{
                //return;
            }

            Log.i("callingService","flag ============" + flag);

            if (state.equals(mLastState)) {
                return;
            } else {
                mLastState = state;
                Log.i(TAG, "mLastState =========" + mLastState);
            }

            if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
                String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Intent popupIntent = new Intent(context, DialogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("flag", flag);
                bundle.putString("phoneNumber", incomingNumber);
                popupIntent.putExtras(bundle);
                popupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(popupIntent);
                flag = 0;
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // Not used
        return null;
    }

    @Override
    public void onCreate() {
        Log.i("callingService","onDestroy ============");
        IntentFilter filter = new IntentFilter();
        filter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
//        mReceiver = new BroadcastReceiver
        this.registerReceiver(mReceiver, filter);
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i("callingService","onDestroy ============");
        unregisterReceiver(mReceiver);

        super.onDestroy();
    }

}
