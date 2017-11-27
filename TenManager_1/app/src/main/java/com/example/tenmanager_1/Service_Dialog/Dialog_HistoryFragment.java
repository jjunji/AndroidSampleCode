package com.example.tenmanager_1.Service_Dialog;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tenmanager_1.Data.CallHistoryData;
import com.example.tenmanager_1.Loader.CallHistoryLoader;
import com.example.tenmanager_1.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Dialog_HistoryFragment extends Fragment {
    private final String TAG = Dialog_HistoryFragment.class.getSimpleName();
    View view;
    ListView callHistoryListView;
    Dialog_historyAdapter adapter;
    CallHistoryLoader loader;
    List<CallHistoryData> datas;
    //HashMap<String, CallHistoryData> hashMap = new HashMap<>();
    //HashMap<String, ArrayList<CallHistoryData>> hashMap = new HashMap<>();
    String phoneNumber;
    //List<CallHistoryData> matchingNumberInfo;
    ArrayList<CallHistoryData> matchingNumberInfo = new ArrayList<>();


    public Dialog_HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dialog__history, container, false);

        initView();
        init();
        getMatchingCall();
        setListView();

        return view;
    }

    private void init() {
        phoneNumber = getArguments().getString("phoneNumber");
        loader = new CallHistoryLoader(getContext());
        datas = new ArrayList<>();
        datas = loader.getContacts();
        //adapter = new Dialog_historyAdapter();
    }

    private void initView() {
        callHistoryListView = (ListView) view.findViewById(R.id.callHistoryListView);
    }

/*    private List<CallHistoryData> getCallinfo(){
        datas = loader.getContacts();

        return datas;
    }*/

    private void getMatchingCall(){
        //ArrayList<CallHistoryData> matchingNumberInfo = new ArrayList<>();

        for(int i=0; i<datas.size(); i++){
            //hashMap.put(datas.get(i).getTel(), datas.get(i));
            if (datas.get(i).getTel().equals(phoneNumber)){
                matchingNumberInfo.add(datas.get(i));
            }
        }
        //hashMap.put(phoneNumber, matchingNumberInfo);
        //hashMap.get(phoneNumber);
        //Log.i(TAG, "hashMapinfo ===========: "+ hashMap.get(phoneNumber).toString());
    }

    private void setListView(){
        //adapter = new Dialog_historyAdapter(getActivity(), matchingNumberInfo);
        adapter = new Dialog_historyAdapter(getActivity(), matchingNumberInfo);
        callHistoryListView.setAdapter(adapter);
    }

}
