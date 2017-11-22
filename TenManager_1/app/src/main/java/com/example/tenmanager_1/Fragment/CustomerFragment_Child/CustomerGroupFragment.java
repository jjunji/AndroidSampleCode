package com.example.tenmanager_1.Fragment.CustomerFragment_Child;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tenmanager_1.CustomerGroup.AddCustomerGroupDialog;
import com.example.tenmanager_1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerGroupFragment extends Fragment {
    View view;
    FloatingActionButton btnAddGroup;
    AddCustomerGroupDialog dialog;


    public CustomerGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_customer_group, container, false);

        initView();
        setAddButton();

        return view;
    }

    private void initView() {
        btnAddGroup = (FloatingActionButton) view.findViewById(R.id.btnAddGroup);
    }

    private void setAddButton(){
        btnAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new AddCustomerGroupDialog(getActivity());
                dialog.show();
            }
        });
    }

}
