package com.example.tenmanager_1.Fragment.ContactFragment_Child;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tenmanager_1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Child_RecentCallFragment extends Fragment {


    public Child_RecentCallFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_child__recent_call, container, false);
    }

}
