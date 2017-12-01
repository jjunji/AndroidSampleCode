package com.example.tenmanager_1.Service_Dialog;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tenmanager_1.Data.CallHistoryData;
import com.example.tenmanager_1.Data.CallHistoryVO;
import com.example.tenmanager_1.Data.CallMemoVO;
import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.Loader.CallHistoryLoader;
import com.example.tenmanager_1.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * A simple {@link Fragment} subclass.
 */
public class Dialog_HistoryFragment extends Fragment {
    private final String TAG = Dialog_HistoryFragment.class.getSimpleName();
    View view;
    Realm realm;
    ListView callHistoryListView;
    Dialog_historyAdapter adapter;
    MatchingHistoryLoader loader;
    //ArrayList<CallHistoryData> datas;
    //ArrayList<CallHistoryVO> datas;
    RealmResults<CallHistoryVO> datas;
    String phoneNumber;
    EditText etCallMemo;
    Button btnMemoStore;

    public Dialog_HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dialog__history, container, false);
        initView();
        init();
        setListView();

        return view;
    }

    private void initView() {
        etCallMemo = (EditText) view.findViewById(R.id.etCallMemo);
        btnMemoStore = (Button) view.findViewById(R.id.btnMemoStore);
        callHistoryListView = (ListView) view.findViewById(R.id.callHistoryListView);
    }

    private void init() {
        realm = Realm.getDefaultInstance();
        phoneNumber = getArguments().getString("phoneNumber");  // CallingService.class 에서 받은 데이터 (방금 통화한 번호)
        loader = new MatchingHistoryLoader(getContext(), phoneNumber);
        //datas = new ArrayList<>();
        //datas = loader.getContacts();
        //datas = realm.where(CallHistoryVO.class).findAll().sort("date", Sort.DESCENDING);
        datas = realm.where(CallHistoryVO.class).equalTo("contactVO.cellPhone", phoneNumber).findAllSorted("date", Sort.DESCENDING);
        setStoreButtonListener();
    }

    private void setListView(){
        adapter = new Dialog_historyAdapter(getActivity(), datas);
        callHistoryListView.setAdapter(adapter);
    }

    private void setStoreButtonListener(){
        btnMemoStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Number maxid = realm.where(CallHistoryVO.class).max("id");
                int id = 1; // 빈 데이터베이스면 index 1로 시작.
                if (maxid != null) { // 비어있지 않으면 마지막 index + 1
                    id = maxid.intValue() + 1;
                }
                realm.beginTransaction();
                CallHistoryVO callHistoryVO = realm.createObject(CallHistoryVO.class, id);
                //callHistoryVO.setContent(etCallMemo.getText().toString());
                callHistoryVO.setCallMemo(etCallMemo.getText().toString());
                //callMemoVO.setRegdate(System.currentTimeMillis());
                callHistoryVO.setDate(System.currentTimeMillis());
                callHistoryVO.setType(3);
                ContactVO contactVO = realm.where(ContactVO.class).equalTo("cellPhone", phoneNumber).findFirst();
                callHistoryVO.setContactVO(contactVO);
                RealmList<CallHistoryVO> list = new RealmList<CallHistoryVO>();
                list.add(callHistoryVO);
                contactVO.setArCallMemo(list);
                realm.commitTransaction();

                RealmResults<CallHistoryVO> results = realm.where(CallHistoryVO.class).findAll();
                for(int i=0; i<results.size(); i++) {
                    Log.i(TAG, "callHistoryVO ============= " + results.get(i).toString());
                }


                /*Activity activity = (DialogActivity)getActivity();
                activity.finish();*/
            }
        });
    }

}
