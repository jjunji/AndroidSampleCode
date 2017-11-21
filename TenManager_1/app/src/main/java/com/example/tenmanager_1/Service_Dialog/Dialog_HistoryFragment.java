package com.example.tenmanager_1.Service_Dialog;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tenmanager_1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Dialog_HistoryFragment extends Fragment {


    public Dialog_HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialog__history, container, false);
    }

}
