package com.example.tenmanager_1.Service_Dialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneNumberUtils;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by 전지훈 on 2017-11-20.
 */

public class IncomingCallBroadcastReceiver extends BroadcastReceiver{
    public static final String TAG = "PHONE STATE";
    TelephonyManager manager;
    private static String mLastState;
    PhoneStateListener phoneStateListener;
    //


    //private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onReceive(final Context context, final Intent intent) {
        //Log.i(TAG, "init =============" + PhoneStateListener.LISTEN_CALL_STATE);
        //manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        //manager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        //final int mInitialCallState = manager.getCallState();  // idle

        Log.i(TAG,"onReceive()");

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);  //
        Log.i(TAG, "state =============== " +state);
        if (state.equals(mLastState)) {
            return;
        } else {
            mLastState = state;
            Log.i(TAG, "mLastState =========" + mLastState);
        }

                    // 통화를 걸고 난 후 종료한 상태
        //if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
        if(TelephonyManager.EXTRA_STATE_IDLE.equals(state)){
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            final String phone_number = PhoneNumberUtils.formatNumber(incomingNumber);

            Intent serviceIntent = new Intent(context, CallingService.class);
            serviceIntent.putExtra("phoneNumber", phone_number);
            context.startService(serviceIntent);
        }
    }
}
