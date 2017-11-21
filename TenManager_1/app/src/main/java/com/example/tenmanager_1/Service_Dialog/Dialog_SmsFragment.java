package com.example.tenmanager_1.Service_Dialog;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.tenmanager_1.Data.WriteSmsVO;
import com.example.tenmanager_1.R;
import com.example.tenmanager_1.StoredSmsUtil.StoredSmsAdapter;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * A simple {@link Fragment} subclass.
 */
public class Dialog_SmsFragment extends Fragment {
    private final String TAG = Dialog_SmsFragment.class.getSimpleName();
    Realm realm;
    RealmResults<WriteSmsVO> storedSmsResults;
    View view;
    ListView storedSmsListView;
    Button btnSend;
    TextView txtContent;
    StoredSmsAdapter storedSmsAdapter;
    private RadioButton mSelectedRB;
    private int mSelectedPosition = -1;


    public Dialog_SmsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dialog__sms, container, false);
        realm = Realm.getDefaultInstance();
        storedSmsResults = realm.where(WriteSmsVO.class).findAll().sort("id", Sort.ASCENDING);
        init();
        setListView();
        setRadionBtnClickListener();

        return view;
    }

    private void init() {
        storedSmsListView = (ListView) view.findViewById(R.id.storedSmsList);
        btnSend = (Button) view.findViewById(R.id.btnSend);
        txtContent = (TextView) view.findViewById(R.id.txtContent);
    }

    private void setListView(){
        storedSmsAdapter = new StoredSmsAdapter(getContext());
        storedSmsListView.setAdapter(storedSmsAdapter);
        storedSmsListView.setFastScrollEnabled(true);
    }

    private void setRadionBtnClickListener() {
        storedSmsAdapter.setHolderClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();  // 누른 포지션.
                RadioButton radioBtn = (RadioButton) v;
                Log.i(TAG, "positioin ======="+position);

                if(position != mSelectedPosition && mSelectedRB != null){
                    mSelectedRB.setChecked(false);
                }

                mSelectedPosition = position;
                mSelectedRB = (RadioButton)v;

                if(mSelectedPosition != position){
                    radioBtn.setChecked(false);
                }else{
                    radioBtn.setChecked(true);
                    txtContent.setText(storedSmsResults.get(position).getContent());
                    //txtTitle.setText(storedSmsResults.get(position).getTitle());
                    //txtContent.setText(storedSmsResults.get(position).getContent());
                    if(mSelectedRB != null && radioBtn != mSelectedRB){
                        mSelectedRB = radioBtn;
                    }
                }
                storedSmsAdapter.notifyDataSetChanged();
            }
        });
        //contactResultList = new ArrayList<>();
    }

}
