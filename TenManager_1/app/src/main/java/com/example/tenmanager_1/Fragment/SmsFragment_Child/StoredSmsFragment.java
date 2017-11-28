package com.example.tenmanager_1.Fragment.SmsFragment_Child;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
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
import android.widget.Toast;
import com.example.tenmanager_1.Data.ContactVO;

import com.example.tenmanager_1.Data.SmsVO;
import com.example.tenmanager_1.FindContactActivity;
import com.example.tenmanager_1.Fragment.FindContactFragment.SelectedDataModel;
import com.example.tenmanager_1.R;
import com.example.tenmanager_1.StoredSmsUtil.StoredSmsAdapter;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoredSmsFragment extends Fragment {
    private final String TAG = StoredSmsFragment.class.getSimpleName();

    RealmResults<SmsVO> storedSmsResults;
    RealmResults<ContactVO> contactResults;
    Realm realm;
    View view;
    TextView txtResultName;
    TextView txtTitle, txtContent;
    TextView txtItemTitle, txtItemContent;
    Button btnContactSearch;
    Button btnSend;
    ListView storedItemListView;
    StoredSmsAdapter adapter;
    ArrayList<String> contactResultList;

    ArrayList<SelectedDataModel> ar;

    private final int REQUESTCODE = 1;

    private RadioButton mSelectedRB;
    private int mSelectedPosition = -1;

    public StoredSmsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_stored_sms, container, false);
        adapter = new StoredSmsAdapter(getContext());
        realm = Realm.getDefaultInstance();
        storedSmsResults = realm.where(SmsVO.class).findAll().sort("regdate", Sort.ASCENDING);
        initView();
        setButtonClickListener();
        setHolderClickListener();
        setListItem();
        return view;
    }

    private void initView() {
        txtResultName = (TextView) view.findViewById(R.id.txtResultName);
        btnContactSearch = (Button) view.findViewById(R.id.btnContactSearch);
        btnSend = (Button) view.findViewById(R.id.btnSend);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtContent = (TextView) view.findViewById(R.id.txtContent);
        txtItemTitle = (TextView) view.findViewById(R.id.txtItemTitle);
        txtItemContent = (TextView) view.findViewById(R.id.txtContent);
        storedItemListView = (ListView) view.findViewById(R.id.storedItemListView);

        if (storedSmsResults.size() != 0) {
            txtTitle.setText(storedSmsResults.get(0).getTitle());
            txtContent.setText(storedSmsResults.get(0).getContent());
        } else {
            txtTitle.setText("");
            txtContent.setText("");
        }
        contactResultList = new ArrayList<>();
    }

    private void setButtonClickListener(){
        // 저장문자를 보낼 연락처 검색 액티비티로 이동.
        btnContactSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FindContactActivity.class);
                startActivityForResult(intent, REQUESTCODE);  // 연락처 검색 액티비티를 종료할 때 액티비티에서 가지고 있던 데이터를 받아온다.
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ar.size() < 1) {
                    Toast.makeText(getContext(), "연락처를 추가하세요~", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    //sendSms();
                    sendSMS();
                }
            }
        });
    }

    private void setHolderClickListener(){
        // 저장문자 목록(라디오) 선택에 따라 제목,내용 변화
        adapter.setHolderClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();  // 누른 포지션.
                RadioButton radioBtn = (RadioButton) v;
                Log.i(TAG, "positioin =======" + position);

                if (position != mSelectedPosition && mSelectedRB != null) {
                    mSelectedRB.setChecked(false);
                }

                mSelectedPosition = position;
                mSelectedRB = (RadioButton) v;

                if (mSelectedPosition != position) {
                    radioBtn.setChecked(false);
                } else {
                    radioBtn.setChecked(true);
                    txtTitle.setText(storedSmsResults.get(position).getTitle());
                    txtContent.setText(storedSmsResults.get(position).getContent());
                    if (mSelectedRB != null && radioBtn != mSelectedRB) {
                        mSelectedRB = radioBtn;
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void setListItem() {
        storedItemListView.setAdapter(adapter);
        storedItemListView.setFastScrollEnabled(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == REQUESTCODE) {
                //ArrayList<Long> ar = (ArrayList<Long>) data.getSerializableExtra("listObject");

                ar = (ArrayList<SelectedDataModel>) data.getSerializableExtra("listObject");

                //Long arList[] = new Long[ar.size()]; // arList 배열 선언

/*                for (int i = 0; i < arList.length; i++) {
                    arList[i] = ar.get(i); // arList 에는 체크박스 누른 포지션이 담긴다.
                }
                contactResults = realm.where(ContactVO.class).in("id", arList).findAll();

                Log.i("test", "results==============" + contactResults);*/
/*
                String resultName = "";
                for (int i = 0; i < arList.length; i++) {
                    //resultName += results.get(i).getName();
                    resultName = resultName + (contactResults.get(i).getName() + "  /");
                    contactResultList.add(contactResults.get(i).getCellPhone());
                }
                txtResultName.setText(resultName);*/
                String resultName = "";
                for(int i=0; i<ar.size(); i++){
                    resultName = resultName + (ar.get(i).getName() + "  /");
                }
                txtResultName.setText(resultName);
            }
        }
    }

    // 즉시 메시지 전송 (다중 선택 안됨 -> 반복문으로 처리)
/*    private void sendSMS() {
        ProgressDialog dialog = ProgressDialog.show(getActivity(), "타이틀", "문자 전송중입니다.", true);
        SmsManager sms = SmsManager.getDefault();
        for (int i = 0; i < contactResultList.size(); i++) {
            //address = address + contactResultList.get(i) + ";";
            sms.sendTextMessage(contactResultList.get(i), null, txtContent.getText().toString(), null, null);
        }
        dialog.dismiss();
        Toast.makeText(getActivity(), "문자가 전송되었습니다.", Toast.LENGTH_SHORT).show();
        //PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, SmsSender.class), 0);
    }*/

    private void sendSMS() {
        ProgressDialog dialog = ProgressDialog.show(getActivity(), "타이틀", "문자 전송중입니다.", true);
        SmsManager sms = SmsManager.getDefault();
        for (int i = 0; i < ar.size(); i++) {
            //address = address + contactResultList.get(i) + ";";
            sms.sendTextMessage(ar.get(i).getPhoneNumber(), null, txtContent.getText().toString(), null, null);
        }
        dialog.dismiss();
        Toast.makeText(getActivity(), "문자가 전송되었습니다.", Toast.LENGTH_SHORT).show();
        //PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, SmsSender.class), 0);
    }

    // 메시지 창으로 이동 후 전송
    public void sendSms() {
        String address = "";
        for (int i = 0; i < contactResultList.size(); i++) {
            address = address + contactResultList.get(i) + ";";
        }
        Log.i(TAG, address);
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + address));
        intent.putExtra("sms_body", txtContent.getText().toString());
        startActivity(intent);
    }
}