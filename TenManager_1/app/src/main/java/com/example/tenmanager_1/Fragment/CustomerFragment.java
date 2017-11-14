package com.example.tenmanager_1.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tenmanager_1.CustomerUtil.CustomerAdapter;
import com.example.tenmanager_1.Fragment.CustomerFragment_Child.CustomerListFragment;
import com.example.tenmanager_1.Fragment.CustomerFragment_Child.CustomerRecentCallFragment;
import com.example.tenmanager_1.Fragment.SmsFragment_Child.RepresentFragment;
import com.example.tenmanager_1.Fragment.SmsFragment_Child.StoredSmsFragment;
import com.example.tenmanager_1.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerFragment extends Fragment implements View.OnClickListener{
    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;
    View view;
    TextView btnContact, btnRecentCall;

    public CustomerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_customer, container, false);
        init();
        callFragment(FRAGMENT1);

        return view;
    }

    private void callFragment(int num) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        switch (num) {
            case FRAGMENT1:
                btnContact.setBackgroundColor(Color.GRAY);
                btnRecentCall.setBackgroundColor(Color.WHITE);
                CustomerListFragment customerListFragment = new CustomerListFragment();
                transaction.replace(R.id.fragment_customer_container, customerListFragment);
                transaction.commit();
                break;
            case FRAGMENT2:
                btnContact.setBackgroundColor(Color.WHITE);
                btnRecentCall.setBackgroundColor(Color.GRAY);
                CustomerRecentCallFragment customerRecentCallFragment = new CustomerRecentCallFragment();
                transaction.replace(R.id.fragment_customer_container, customerRecentCallFragment);
                transaction.commit();
                break;
        }
    }

    private void init() {
        btnContact = (TextView) view.findViewById(R.id.btnContact);
        btnRecentCall = (TextView) view.findViewById(R.id.btnRecentCall);
        btnContact.setOnClickListener(this);
        btnRecentCall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnContact:
                callFragment(FRAGMENT1);
                break;
            case R.id.btnRecentCall:
                callFragment(FRAGMENT2);
                break;
        }
    }
}
