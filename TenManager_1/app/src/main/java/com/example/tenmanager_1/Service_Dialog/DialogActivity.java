package com.example.tenmanager_1.Service_Dialog;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.tenmanager_1.Data.CallHistoryVO;
import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.Data.SmsVO;
import com.example.tenmanager_1.Loader.CallHistoryLoader;
import com.example.tenmanager_1.R;

import io.realm.Realm;


public class DialogActivity extends AppCompatActivity {
    private final String TAG = DialogActivity.class.getSimpleName();
    Realm realm;
    Button btnSms, btnHistory;
    TextView txtPhoneNumber, txtName;
    TextView txtCallState;
    private final int SMSFRAGMENT = 1;
    private final int HISTORYFRAGMENT = 2;

    CallHistoryLoader loader;
    //MatchingHistoryLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        init();
        storeCalldata();
        callFragment(SMSFRAGMENT);
        setText();
        setButtonListener();
    }

    // 통화 종료후 나타나는 팝업창에서 문자, 통화내역 각 버튼을 눌렀을 때 교체되는 프래그먼트
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

    private void storeCalldata(){
        int flag = getIntent().getExtras().getInt("flag");
        String phoneNumber = getIntent().getExtras().getString("phoneNumber");

        Number maxid = realm.where(CallHistoryVO.class).max("id");
        int id = 1; // 빈 데이터베이스면 index 1로 시작.
        if(maxid != null){ // 비어있지 않으면 마지막 index + 1
            id = maxid.intValue()+1;
        }

        if(flag == 1){
            realm.beginTransaction();
            CallHistoryVO callHistoryVO = realm.createObject(CallHistoryVO.class, id);
            ContactVO contactVO = realm.where(ContactVO.class).equalTo("cellPhone", phoneNumber).findFirst();
            callHistoryVO.setContactVO(contactVO);
            callHistoryVO.setType(1);
            callHistoryVO.setDate(System.currentTimeMillis());
            realm.commitTransaction();
        }else if(flag == 2){
            realm.beginTransaction();
            CallHistoryVO callHistoryVO = realm.createObject(CallHistoryVO.class, id);
            ContactVO contactVO = realm.where(ContactVO.class).equalTo("cellPhone", phoneNumber).findFirst();
            callHistoryVO.setContactVO(contactVO);
            callHistoryVO.setType(2);
            callHistoryVO.setDate(System.currentTimeMillis());
            realm.commitTransaction();
        }

    }

    private void init() {
        realm = Realm.getDefaultInstance();
        loader = new CallHistoryLoader(DialogActivity.this);
        btnSms = (Button) findViewById(R.id.btnSms);
        btnHistory = (Button) findViewById(R.id.btnHistory);
        txtName = (TextView) findViewById(R.id.txtName);
        txtPhoneNumber = (TextView) findViewById(R.id.txtPhoneNumber);
        txtCallState = (TextView) findViewById(R.id.txtCallState);
    }

}
