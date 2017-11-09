package com.example.tenmanager_1;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.tenmanager_1.Data.ContactData;
import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.Fragment.FindContactFragment.ContactFragment;
import com.example.tenmanager_1.Fragment.FindContactFragment.RecentCallFragment;

import java.util.ArrayList;

public class FindContactActivity extends AppCompatActivity implements View.OnClickListener{
    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;
    TextView btnContact, btnRecentCall;
    ArrayList<ContactVO> checkedContactResult;
    ContactFragment contactFragment = new ContactFragment();
    RecentCallFragment recentCallFragment = new RecentCallFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_contact);
        init();
        setButtonClickListener();
        callFragment(FRAGMENT1);
    }

    private void init() {
        btnContact = (TextView) findViewById(R.id.btnContact);
        btnRecentCall = (TextView) findViewById(R.id.btnRecentCall);
        checkedContactResult = new ArrayList<>();
    }

    public void setButtonClickListener(){
        btnContact.setOnClickListener(this);
        btnRecentCall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnContact :
                callFragment(FRAGMENT1);
                break;

            case R.id.btnRecentCall :
                callFragment(FRAGMENT2);
                break;
        }
    }

    public void callFragment(int fragmentNo) {
        // 프래그먼트 사용을 위해 트랜잭션 객체 선언
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (fragmentNo) {
            case 1:
                transaction.replace(R.id.fragment_contact_container, contactFragment);
                transaction.commit();
                break;

            case 2:
                transaction.replace(R.id.fragment_contact_container, recentCallFragment);
                transaction.commit();
                break;
        }
    }

    public void selectedContact(ArrayList<ContactVO> list){
        //           변수(객체) list.size() 만큼
        for(ContactVO contactVO : list){

            Log.i("test", "selected contact : " + contactVO.toString());
        }

        // 나중에 activityfForResult 사용시 데이터 전달하는 방법 찾아서 넣기

    }
}
