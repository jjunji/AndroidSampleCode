package com.example.tenmanager_1.Fragment.SmsFragment_Child;


import android.app.ProgressDialog;
import android.content.Intent;
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

import com.example.tenmanager_1.Data.SmsVO;
import com.example.tenmanager_1.FindContactActivity;
import com.example.tenmanager_1.Fragment.FindContactFragment.SelectedDataModel;
import com.example.tenmanager_1.R;
import com.example.tenmanager_1.SmsFragmentUtil.PromoteSmsAdapter;
import com.example.tenmanager_1.SmsFragmentUtil.StoredSmsAdapter;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * A simple {@link Fragment} subclass.
 */
public class PromoteFragment extends Fragment {
    private final String TAG = PromoteFragment.class.getSimpleName();
    View view;
    RealmResults<SmsVO> storedSmsResults;
    Realm realm;
    TextView txtResultName;
    TextView txtTitle, txtContent;
    TextView txtItemTitle, txtItemContent;
    Button btnContactSearch;
    Button btnSend;
    Button btnSearchPlace;
    ListView storedPromoteListView;
    PromoteSmsAdapter adapter;
    ArrayList<SelectedDataModel> ar;

    private RadioButton mSelectedRB;
    private int mSelectedPosition = -1;

    private final int REQUESTCODE_PROMOTE = 2;

    public PromoteFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_promote, container, false);

        init();
        initView();
        setListItem();
        setHolderClickListener();
        setButtonClickListener();

        return view;
    }

    private void init() {
        realm = Realm.getDefaultInstance();
        adapter = new PromoteSmsAdapter(getContext());
        storedSmsResults = realm.where(SmsVO.class).equalTo("group.id",3).findAllSorted("regdate", Sort.ASCENDING);
    }

    private void initView() {
        txtResultName = (TextView) view.findViewById(R.id.txtResultName);
        btnContactSearch = (Button) view.findViewById(R.id.btnContactSearch);
        btnSend = (Button) view.findViewById(R.id.btnSend);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtContent = (TextView) view.findViewById(R.id.txtContent);
        txtItemTitle = (TextView) view.findViewById(R.id.txtItemTitle);
        txtItemContent = (TextView) view.findViewById(R.id.txtContent);
        storedPromoteListView = (ListView) view.findViewById(R.id.storedPromoteListView);
        btnSearchPlace = (Button) view.findViewById(R.id.btnSearchPlace);

        if (storedSmsResults.size() != 0) {
            txtTitle.setText(storedSmsResults.get(0).getTitle());
            txtContent.setText(storedSmsResults.get(0).getContent());
        } else {
            txtTitle.setText("");
            txtContent.setText("");
        }

    }

    private void setListItem() {
        storedPromoteListView.setAdapter(adapter);
        storedPromoteListView.setFastScrollEnabled(true);
    }

    private void setHolderClickListener() {
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

    private void setButtonClickListener() {
        // 저장문자를 보낼 연락처 검색 액티비티로 이동.
        btnContactSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FindContactActivity.class);
                startActivityForResult(intent, REQUESTCODE_PROMOTE);  // 연락처 검색 액티비티를 종료할 때 액티비티에서 가지고 있던 데이터를 받아온다.
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ar.size() < 1) {
                    Toast.makeText(getContext(), "연락처를 추가하세요~", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    sendSMS();
                    txtResultName.setText("");  // 문자 전송 후 텍스트뷰 초기화
                    ar.clear(); // 문자 전송 후 리스트 초기화 (안하면 텍스트뷰는 비어있지만 버튼 클릭시 전에 보낸 번호로 계속 보냄)
                }
            }
        });
    }

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

}
