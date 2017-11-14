package com.example.tenmanager_1.Fragment.SmsFragment_Child;


import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
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
import android.widget.Toast;

import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.Data.WriteSmsVO;
import com.example.tenmanager_1.FindContactActivity;
import com.example.tenmanager_1.R;
import com.example.tenmanager_1.StoredSmsUtil.StoredSmsAdapter;
import com.example.tenmanager_1.StoredSmsUtil.StoredSmsViewHolder;

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

    RealmResults<WriteSmsVO> storedSmsResults;
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

    private  final int REQUESTCODE = 1;

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
        storedSmsResults = realm.where(WriteSmsVO.class).findAll().sort("id", Sort.ASCENDING);
        initView();
        setListItem();
        return view;
    }

    private void initView() {
        txtResultName = (TextView) view.findViewById(R.id.txtResultName);
        btnContactSearch = (Button) view.findViewById(R.id.btnContactSearch);
        btnContactSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FindContactActivity.class);
                startActivityForResult(intent, REQUESTCODE);
            }
        });
        btnSend = (Button) view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contactResultList.size() < 1){
                    Toast.makeText(getContext(), "연락처를 추가하세요~", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    sendSms();
                }
            }
        });
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtContent = (TextView) view.findViewById(R.id.txtContent);
        txtItemTitle = (TextView) view.findViewById(R.id.txtItemTitle);
        txtItemContent = (TextView) view.findViewById(R.id.txtContent);
        storedItemListView = (ListView) view.findViewById(R.id.storedItemListView);

        if (storedSmsResults.size() != 0){
            txtTitle.setText(storedSmsResults.get(0).getTitle());
            txtContent.setText(storedSmsResults.get(0).getContent());
        }else{
            txtTitle.setText("");
            txtContent.setText("");
        }


        adapter.setHolderClickListener(new View.OnClickListener() {
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
                    txtTitle.setText(storedSmsResults.get(position).getTitle());
                    txtContent.setText(storedSmsResults.get(position).getContent());
                    if(mSelectedRB != null && radioBtn != mSelectedRB){
                        mSelectedRB = radioBtn;
                    }
                }

                adapter.notifyDataSetChanged();
            }
        });
        contactResultList = new ArrayList<>();
    }

    public void setListItem(){
        storedItemListView.setAdapter(adapter);
        storedItemListView.setFastScrollEnabled(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            if(requestCode == REQUESTCODE){
                ArrayList<Integer> ar = data.getIntegerArrayListExtra("listObject"); // 체크박스 누른 포지션
//                Integer arIds[] = new Integer[];

                Integer arList[] = new Integer[ar.size()]; // arList 배열 선언

                for(int i=0; i<arList.length; i++){
                    arList[i] = ar.get(i); // arList 에는 체크박스 누른 포지션이 담긴다.
                }
                //Realm realm = Realm.getDefaultInstance();
                contactResults = realm.where(ContactVO.class).in("id", arList).findAll();

                Log.i("test","results==============" + contactResults);

                String resultName = "";
                for(int i=0; i<arList.length; i++){
                    //resultName += results.get(i).getName();
                    resultName = resultName + (contactResults.get(i).getName() + "  /");
                    contactResultList.add(contactResults.get(i).getTel());
                }
                txtResultName.setText(resultName);
            }
        }
    }

    public void sendSms(){
        String address = "";
        for(int i=0; i<contactResultList.size(); i++){
            address = address + contactResultList.get(i) + ";";
        }
        Log.i(TAG, address);
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + address));
        intent.putExtra("sms_body", txtContent.getText().toString());
        startActivity(intent);

    }

}