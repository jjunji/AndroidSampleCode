package com.example.tenmanager_1.Fragment.SmsFragment_Child;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.text.LoginFilter;
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
import com.example.tenmanager_1.SmsFragmentUtil.PromoteSmsViewHolder;
import com.example.tenmanager_1.SmsFragmentUtil.StoredSmsAdapter;
import com.example.tenmanager_1.SmsFragmentUtil.StoredSmsViewHolder;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static android.app.Activity.RESULT_OK;

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
        setAdapterItemClickListener();
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

    private void setAdapterItemClickListener() {
        // 저장문자 목록(라디오) 선택에 따라 제목,내용 변화
        adapter.setRadioButtonClickListener(new View.OnClickListener() {
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

        adapter.setHolderClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PromoteSmsViewHolder viewHolder = (PromoteSmsViewHolder) v.getTag();
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
                    txtTitle.setText(storedSmsResults.get(position).getTitle());
                    txtContent.setText(storedSmsResults.get(position).getContent());
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
                    //sendSMS();
                    //sendMMS();
                    sendMMS2();
                    txtResultName.setText("");  // 문자 전송 후 텍스트뷰 초기화
                    ar.clear(); // 문자 전송 후 리스트 초기화 (안하면 텍스트뷰는 비어있지만 버튼 클릭시 전에 보낸 번호로 계속 보냄)
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == REQUESTCODE_PROMOTE){
                ar = (ArrayList<SelectedDataModel>) data.getSerializableExtra("listObject");

                String resultName = "";
                for(int i=0; i<ar.size(); i++){
                    if(i<ar.size()-1){
                        resultName = resultName + (ar.get(i).getName() + ", ");
                    }else{
                        resultName = resultName + (ar.get(i).getName());
                    }

                }
                txtResultName.setText(resultName);
            }
        }
    }

    private void sendSMS() {
        ProgressDialog dialog = ProgressDialog.show(getActivity(), "타이틀", "문자 전송중입니다.", true);
        SmsManager sms = SmsManager.getDefault();
        try{
            for (int i = 0; i < ar.size(); i++) {
                sms.sendTextMessage(ar.get(i).getPhoneNumber(), null, txtContent.getText().toString(), null, null);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        dialog.dismiss();
        Toast.makeText(getActivity(), "문자가 전송되었습니다.", Toast.LENGTH_SHORT).show();
        //PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, SmsSender.class), 0);
    }

    private void sendMMS(){
        ProgressDialog dialog = ProgressDialog.show(getActivity(), "타이틀", "문자 전송중입니다.", true);
        /*String strAttachUrl = "file://"+ Environment.getExternalStorageDirectory()+"/test.jpg";
        Uri uri = Uri.parse("file://"+ Environment.getExternalStorageDirectory()+"/test.jpg");
        Log.e("strAttachUrl: ", strAttachUrl);
        Log.e("imagePath: ", uri.getPath());*/
        try{
            for (int i = 0; i < ar.size(); i++) {
                Intent sendMMSIntent = new Intent(Intent.ACTION_SEND);
                //sendMMSIntent.setClassName("com.example.tenmanager_1", "com.example.tenmanager_1.Fragment.SmsFragment_Child.PromoteFragment");
                sendMMSIntent.putExtra("address", ar.get(i).getPhoneNumber());
                sendMMSIntent.putExtra("sms_body", txtContent.getText().toString());
                // sendMMSIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(strAttachUrl));
                // sendMMSIntent.setType("image/jpg");
                //sendMMSIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(sendMMSIntent);
                startActivity(Intent.createChooser(sendMMSIntent, "send"));
            }
            Toast.makeText(getActivity(), "문자가 전송되었습니다.", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
        dialog.dismiss();
    }

    private void sendMMS2(){
        SmsManager sms = SmsManager.getDefault();
        ProgressDialog dialog = ProgressDialog.show(getActivity(), "타이틀", "문자 전송중입니다.", true);
        try{
            for (int i = 0; i < ar.size(); i++) {
                ArrayList<String> parts = sms.divideMessage(txtContent.getText().toString());
                sms.sendMultipartTextMessage(ar.get(i).getPhoneNumber(), null, parts, null, null);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        dialog.dismiss();
        Toast.makeText(getActivity(), "문자가 전송되었습니다.", Toast.LENGTH_SHORT).show();
    }

}
