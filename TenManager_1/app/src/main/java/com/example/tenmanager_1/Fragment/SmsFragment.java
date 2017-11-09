package com.example.tenmanager_1.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tenmanager_1.FindContactActivity;
import com.example.tenmanager_1.MainActivity;
import com.example.tenmanager_1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SmsFragment extends Fragment {
    View view;
    Button btnContactSearch;
    private  final int REQUESTCODE = 1;

    public SmsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sms, container, false);

        initView();

        return view;
    }

/*    private void initView() {
        btnContactSearch = (Button) view.findViewById(R.id.btnContactSearch);
        btnContactSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FindContactActivity.class);
                startActivity(intent);
            }
        });
    }*/

    private void initView() {
        btnContactSearch = (Button) view.findViewById(R.id.btnContactSearch);
        btnContactSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FindContactActivity.class);
                startActivityForResult(intent, REQUESTCODE);
            }
        });
    }

}
