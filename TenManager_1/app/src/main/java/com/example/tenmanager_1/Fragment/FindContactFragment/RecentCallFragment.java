package com.example.tenmanager_1.Fragment.FindContactFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telecom.Call;
import android.telephony.CellIdentityGsm;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.tenmanager_1.ContactUtil.ContactAdapter;
import com.example.tenmanager_1.ContactUtil.IndexableListView;
import com.example.tenmanager_1.Data.CallHistoryData;
import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.FindContactActivity;
import com.example.tenmanager_1.Loader.CallHistoryLoader;
import com.example.tenmanager_1.R;
import com.example.tenmanager_1.RecentCallUtil.CallHistoryAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecentCallFragment extends Fragment {
    View view;
    ListView recentCallListView;
    CallHistoryAdapter adapter;
    CallHistoryLoader ChLoader;
    HashMap<CallHistoryData, Boolean> mapSelected;
    //CallHistoryData data;
    List<CallHistoryData> datas;

    public RecentCallFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_child__recent_call, container, false);

        init();
        setListView();
        return view;
    }

    private void init() {
        mapSelected = new HashMap<>();

        ChLoader = new CallHistoryLoader(getContext());
        datas = ChLoader.getContacts();

        for(CallHistoryData data : datas){
            mapSelected.put(data, false);
        }

        recentCallListView = (ListView) view.findViewById(R.id.recentCalListView);
        adapter = new CallHistoryAdapter(datas, getContext(), mapSelected);
    }

    private void setListView() {
        recentCallListView.setAdapter(adapter);
        recentCallListView.setFastScrollEnabled(true);
    }

}