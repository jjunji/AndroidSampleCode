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
import com.example.tenmanager_1.Fragment.CustomerFragment_Child.CustomerGroupFragment;
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
    private final int CONTACTFRAGMENT = 1;
    private final int CALLHISTORYFRAGMENT = 2;
    private final int GROUPFRAGMNET = 3;
    View view;
    TextView btnContact, btnRecentCall, btnGroup;

    public CustomerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_customer, container, false);
        initView();
        setButtonListener();
        callFragment(CONTACTFRAGMENT);

        return view;
    }

    private void callFragment(int num) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        switch (num) {
            case CONTACTFRAGMENT :
                btnContact.setBackgroundColor(Color.GRAY);
                btnRecentCall.setBackgroundColor(Color.WHITE);
                btnGroup.setBackgroundColor(Color.WHITE);
                CustomerListFragment customerListFragment = new CustomerListFragment();
                transaction.replace(R.id.fragment_customer_container, customerListFragment);
                transaction.commit();
                break;
            case CALLHISTORYFRAGMENT :
                btnContact.setBackgroundColor(Color.WHITE);
                btnRecentCall.setBackgroundColor(Color.GRAY);
                btnGroup.setBackgroundColor(Color.WHITE);
                CustomerRecentCallFragment customerRecentCallFragment = new CustomerRecentCallFragment();
                transaction.replace(R.id.fragment_customer_container, customerRecentCallFragment);
                transaction.commit();
                break;
            case GROUPFRAGMNET :
                btnContact.setBackgroundColor(Color.WHITE);
                btnRecentCall.setBackgroundColor(Color.WHITE);
                btnGroup.setBackgroundColor(Color.GRAY);
                CustomerGroupFragment customerGroupFragment = new CustomerGroupFragment();
                transaction.replace(R.id.fragment_customer_container, customerGroupFragment);
                transaction.commit();
                break;
        }
    }

    private void initView() {
        btnContact = (TextView) view.findViewById(R.id.btnContact);
        btnRecentCall = (TextView) view.findViewById(R.id.btnRecentCall);
        btnGroup = (TextView) view.findViewById(R.id.btnGroup);
    }

    private void setButtonListener(){
        btnContact.setOnClickListener(this);
        btnRecentCall.setOnClickListener(this);
        btnGroup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnContact :
                callFragment(CONTACTFRAGMENT);
                break;
            case R.id.btnRecentCall :
                callFragment(CALLHISTORYFRAGMENT);
                break;
            case R.id.btnGroup :
                callFragment(GROUPFRAGMNET);
                break;
        }
    }
}
