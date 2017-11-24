package com.example.tenmanager_1.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tenmanager_1.Fragment.SmsFragment_Child.PromoteFragment;
import com.example.tenmanager_1.Fragment.SmsFragment_Child.RepresentFragment;
import com.example.tenmanager_1.Fragment.SmsFragment_Child.StoredSmsFragment;
import com.example.tenmanager_1.Fragment.SmsFragment_Child.ManageSmsFragment;
import com.example.tenmanager_1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SmsFragment extends Fragment implements View.OnClickListener{
    View view;
    TextView btnStored, btnRepresent, btnPromote, btnWrite;
    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;
    private final int FRAGMENT3 = 3;
    private final int FRAGMENT4 = 4;

    public SmsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sms, container, false);

        initView();
        setButtonListener();
        callFragment(FRAGMENT1);

        return view;
    }

    private void initView() {
        btnStored = (TextView) view.findViewById(R.id.btnStored);
        btnRepresent = (TextView) view.findViewById(R.id.btnRepresent);
        btnPromote = (TextView) view.findViewById(R.id.btnPromote);
        btnWrite = (TextView) view.findViewById(R.id.btnManage);
    }


    private void callFragment(int num) {
        // 프래그먼트 사용을 위해 트랜잭션 객체 선언
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        switch (num){
            case FRAGMENT1 :
                StoredSmsFragment storedFragment = new StoredSmsFragment();
                transaction.replace(R.id.smsFragmentContainer, storedFragment);
                transaction.commit();
                break;
            case FRAGMENT2 :
                RepresentFragment representFragment = new RepresentFragment();
                transaction.replace(R.id.smsFragmentContainer, representFragment);
                transaction.commit();
                break;
            case FRAGMENT3 :
                PromoteFragment promoteFragment = new PromoteFragment();
                transaction.replace(R.id.smsFragmentContainer, promoteFragment);
                transaction.commit();
                break;
            case FRAGMENT4 :
                ManageSmsFragment manageSmsFragment = new ManageSmsFragment();
                transaction.replace(R.id.smsFragmentContainer, manageSmsFragment);
                transaction.commit();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStored :
                callFragment(FRAGMENT1);
                break;
            case R.id.btnRepresent :
                callFragment(FRAGMENT2);
                break;
            case R.id.btnPromote :
                callFragment(FRAGMENT3);
                break;
            case R.id.btnManage :
                callFragment(FRAGMENT4);
                break;
        }
    }

    private void setButtonListener(){
        btnStored.setOnClickListener(this);
        btnPromote.setOnClickListener(this);
        btnRepresent.setOnClickListener(this);
        btnWrite.setOnClickListener(this);
    }

}
