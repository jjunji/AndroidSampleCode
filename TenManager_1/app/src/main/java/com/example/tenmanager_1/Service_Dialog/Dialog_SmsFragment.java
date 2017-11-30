package com.example.tenmanager_1.Service_Dialog;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.tenmanager_1.Data.SmsVO;
import com.example.tenmanager_1.Loader.CallHistoryLoader;
import com.example.tenmanager_1.R;
import com.example.tenmanager_1.SmsFragmentUtil.StoredSmsAdapter;
import com.example.tenmanager_1.SmsFragmentUtil.StoredSmsViewHolder;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * A simple {@link Fragment} subclass.
 */
public class Dialog_SmsFragment extends Fragment {
    private final String TAG = Dialog_SmsFragment.class.getSimpleName();
    Realm realm;
    RealmResults<SmsVO> storedSmsResults;
    View view;
    ListView storedSmsListView;
    Button btnSend;
    TextView txtContent;
    StoredSmsAdapter storedSmsAdapter;
    private RadioButton mSelectedRB;
    private int mSelectedPosition = -1;
    CallHistoryLoader loader;
    SmsManager sms;


    public Dialog_SmsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dialog__sms, container, false);
        realm = Realm.getDefaultInstance();
        storedSmsResults = realm.where(SmsVO.class).findAll().sort("regdate", Sort.ASCENDING);
        init();
        setListView();
        setAdapterItemClickListener();

        return view;
    }

    private void init() {
        sms = SmsManager.getDefault();
        storedSmsListView = (ListView) view.findViewById(R.id.storedSmsList);
        btnSend = (Button) view.findViewById(R.id.btnSend);
        txtContent = (TextView) view.findViewById(R.id.txtContent);
        loader = new CallHistoryLoader(getActivity());
        setSendButtonClickListener();
        Log.i(TAG, "content =========== " + txtContent.getText().toString());
        Log.i(TAG, "getTel ============ " + loader.getContacts().get(0).getTel());
    }

    private void setListView(){
        storedSmsAdapter = new StoredSmsAdapter(getContext());
        storedSmsListView.setAdapter(storedSmsAdapter);
        storedSmsListView.setFastScrollEnabled(true);
    }

    private void setAdapterItemClickListener() {
        storedSmsAdapter.setRadioButtonClickListener(new View.OnClickListener() {
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
                    if(mSelectedRB != null && radioBtn != mSelectedRB){
                        mSelectedRB = radioBtn;
                    }
                }
                storedSmsAdapter.notifyDataSetChanged();
            }
        });

        storedSmsAdapter.setHolderClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoredSmsViewHolder viewHolder = (StoredSmsViewHolder) v.getTag();
                int position = viewHolder.getTag();
                RadioButton radioBtn = viewHolder.radioBtn;

                if (position != mSelectedPosition && mSelectedRB != null) {
                    mSelectedRB.setChecked(false);
                }

                mSelectedPosition = position; // 누른위치
                mSelectedRB = viewHolder.radioBtn; // 누른위치에 해당하는 라디오버튼

                if (mSelectedPosition != position) {
                    radioBtn.setChecked(false);
                } else {
                    radioBtn.setChecked(true);
                    txtContent.setText(storedSmsResults.get(position).getContent());
                }
                storedSmsAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setSendButtonClickListener(){
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogActivity activity = (DialogActivity) getActivity();
                if(txtContent.getText().toString() != null){
                    sms.sendTextMessage(loader.getContacts().get(0).getTel(), null, txtContent.getText().toString(), null, null);
                    activity.finish();
                }else{
                    activity.finish();
                }
            }
        });
    }

}
