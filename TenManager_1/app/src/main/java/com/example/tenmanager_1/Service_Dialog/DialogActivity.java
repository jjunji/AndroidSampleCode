package com.example.tenmanager_1.Service_Dialog;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.tenmanager_1.Loader.CallHistoryLoader;
import com.example.tenmanager_1.R;


public class DialogActivity extends AppCompatActivity {
    private final String TAG = DialogActivity.class.getSimpleName();
    Button btnSms, btnHistory;
    TextView txtPhoneNumber, txtName;
    TextView txtCallState;
    private final int SMSFRAGMENT = 1;
    private final int HISTORYFRAGMENT = 2;

    CallHistoryLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);

        init();
        callFragment(SMSFRAGMENT);
        setText();
        setButtonListener();
    }

    private void callFragment(int fragmentIndex) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (fragmentIndex){
            case 1 :
                Dialog_SmsFragment dialogSmsFragment = new Dialog_SmsFragment();
                transaction.replace(R.id.dialogContainer, dialogSmsFragment);
                transaction.commit();
                break;
            case 2 :
                Dialog_HistoryFragment dialogHistoryFragment = new Dialog_HistoryFragment();
                transaction.replace(R.id.dialogContainer, dialogHistoryFragment);
                Bundle bundle = new Bundle(1); // 파라미터는 전달할 데이터 개수
                bundle.putString("phoneNumber", getIntent().getExtras().getString("phoneNumber")); // key , value
                dialogHistoryFragment.setArguments(bundle);
                transaction.commit();
                break;
        }
    }

    private void setButtonListener(){
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callFragment(SMSFRAGMENT);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callFragment(HISTORYFRAGMENT);
            }
        });
    }

    private void setText(){
        //txtPhoneNumber.setText(getIntent().getStringExtra("phoneNumber"));
        String name = null;
        if(loader.getContacts().get(0).getName() != null){
            name = loader.getContacts().get(0).getName();
        }else{
            name = null;
        }

        int flag = getIntent().getExtras().getInt("flag");
            if(flag == 1){
                txtCallState.setText("전화를 받았습니다.");
            }else if(flag == 2){
                txtCallState.setText("전화를 걸었습니다.");
        }

        txtPhoneNumber.setText(getIntent().getExtras().getString("phoneNumber"));
        txtName.setText(name);

    }

    private void init() {
        loader = new CallHistoryLoader(DialogActivity.this);
        btnSms = (Button) findViewById(R.id.btnSms);
        btnHistory = (Button) findViewById(R.id.btnHistory);
        txtName = (TextView) findViewById(R.id.txtName);
        txtPhoneNumber = (TextView) findViewById(R.id.txtPhoneNumber);
        txtCallState = (TextView) findViewById(R.id.txtCallState);
    }

}
