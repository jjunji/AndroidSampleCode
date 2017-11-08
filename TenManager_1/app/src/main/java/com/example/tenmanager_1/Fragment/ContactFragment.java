package com.example.tenmanager_1.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tenmanager_1.Fragment.ContactFragment_Child.Child_ContactFragment;
import com.example.tenmanager_1.Fragment.ContactFragment_Child.Child_RecentCallFragment;
import com.example.tenmanager_1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment implements View.OnClickListener{
    View view;
    TextView btnContact, btnRecentCall;

    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contact, container, false);

        init();
        setButtonListener();

        return view;
    }

    private void init() {
        callFragment(FRAGMENT1);
        btnContact = (TextView) view.findViewById(R.id.btnContact);
        btnRecentCall = (TextView) view.findViewById(R.id.btnRecentCall);
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

    public void setButtonListener(){
        btnContact.setOnClickListener(this);
        btnRecentCall.setOnClickListener(this);
    }

    public void callFragment(int fragmentNo) {
        // 프래그먼트 사용을 위해 트랜잭션 객체 선언
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        switch (fragmentNo) {
            case 1:
                Child_ContactFragment childContactFragment = new Child_ContactFragment();
                transaction.replace(R.id.fragment_contact_container, childContactFragment);
                transaction.commit();
                break;

            case 2:
                Child_RecentCallFragment childRecentCallFragmet = new Child_RecentCallFragment();
                transaction.replace(R.id.fragment_contact_container, childRecentCallFragmet);
                transaction.commit();
                break;
        }
    }
}
