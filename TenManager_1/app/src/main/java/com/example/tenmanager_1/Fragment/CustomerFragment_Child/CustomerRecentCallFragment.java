package com.example.tenmanager_1.Fragment.CustomerFragment_Child;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tenmanager_1.CustomerUtil.CustomerRecentCallAdapter;
import com.example.tenmanager_1.Data.CallHistoryData;
import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.Loader.CallHistoryLoader;
import com.example.tenmanager_1.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerRecentCallFragment extends Fragment {
    View view;
    ListView customerRecentCallListView;
    CustomerRecentCallAdapter adapter;
    CallHistoryLoader ChLoader;

    public CustomerRecentCallFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_customer_recentcall, container, false);

        init();
        setListView();

        return view;
    }

    private void init() {
        customerRecentCallListView = (ListView) view.findViewById(R.id.customerRecentCallListView);
        ChLoader = new CallHistoryLoader(getActivity());
        adapter = new CustomerRecentCallAdapter(getContext(), (ArrayList<CallHistoryData>) ChLoader.getContacts());
        adapter.setCallButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                CallHistoryData data = adapter.getItem(position);
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:"+ data.getTel())));
            }
        });
        adapter.setSmsButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                CallHistoryData data = adapter.getItem(position);
                startActivity(new Intent("android.intent.action.SENDTO", Uri.parse("sms:"+data.getTel())));
            }
        });
    }

    private void setListView(){
        customerRecentCallListView.setAdapter(adapter);
        customerRecentCallListView.setFastScrollEnabled(true);
    }

}
