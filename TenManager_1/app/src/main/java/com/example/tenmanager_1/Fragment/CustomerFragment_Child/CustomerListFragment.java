package com.example.tenmanager_1.Fragment.CustomerFragment_Child;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tenmanager_1.CustomerUtil.CustomerAdapter;
import com.example.tenmanager_1.CustomerUtil.IndexableCustomerListView;
import com.example.tenmanager_1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerListFragment extends Fragment {
    View view;
    //ListView customerListView;
    IndexableCustomerListView indexableCustomerListView;
    CustomerAdapter adapter;


    public CustomerListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_customer_contact, container, false);
        init();
        setListView();
        return view;
    }

    private void init() {
        //customerListView = (ListView) view.findViewById(R.id.c_customerListView);
        indexableCustomerListView = (IndexableCustomerListView) view.findViewById(R.id.c_customerListView);
        adapter = new CustomerAdapter(getContext());
    }

    private void setListView() {
        indexableCustomerListView.setAdapter(adapter);
        indexableCustomerListView.setFastScrollEnabled(true);
    }

}
