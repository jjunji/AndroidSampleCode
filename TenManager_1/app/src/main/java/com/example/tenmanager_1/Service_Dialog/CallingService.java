package com.example.tenmanager_1.Service_Dialog;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Telephony;
import android.telephony.PhoneNumberUtils;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by 전지훈 on 2017-11-20.
 */

public class CallingService extends Service {
    public static final String TAG = "PHONE STATE";
//    BroadcastReceiver mReceiver;
    private static String mLastState;

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
                //final String phone_number = PhoneNumberUtils.formatNumber(incomingNumber);

                //Intent serviceIntent = new Intent(context, CallingService.class);
                //serviceIntent.putExtra("phoneNumber", phone_number);
                //context.startService(serviceIntent);
                Intent popupIntent = new Intent(context, DialogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("flag", flag);
                bundle.putString("phoneNumber", incomingNumber);
                //popupIntent.putExtra("phoneNumber", incomingNumber);
                // popupIntent.putExtra("flag", flag);
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
        //filter.addAction("anotherAction");
//        mReceiver = new BroadcastReceiver
        this.registerReceiver(mReceiver, filter);
        super.onCreate();

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //String pn = String.valueOf(intent.getExtras());
        //String pn = intent.getStringExtra("phoneNumber");
        // Log.i("Service","pn================" + pn);

/*        Intent popupIntent = new Intent(this, DialogActivity.class);
        //popupIntent.putExtra("phoneNumber", pn);
        popupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(popupIntent);*/
        /*IntentFilter filter = new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        filter.addAction(String.valueOf(PhoneStateListener.LISTEN_CALL_STATE));
        //filter.addAction("anotherAction");
        mReceiver = new MyReceiver();
        registerReceiver(mReceiver, filter);*/

        //return super.onStartCommand(intent, flags, startId);
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
