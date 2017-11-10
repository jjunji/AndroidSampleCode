package com.example.tenmanager_1.Fragment.SmsFragment_Child;


import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.tenmanager_1.AddSmsActivity;
import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.Data.WriteSmsVO;
import com.example.tenmanager_1.R;
import com.example.tenmanager_1.WriteUtil.WriteAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class WriteFragment extends Fragment implements View.OnClickListener{
    private final String TAG = WriteFragment.class.getSimpleName();
    Realm realm;
    View view;
    Button btnAddContent;  // 문자내용 추가하기
    Button btnDelete, btnUpdate, btnStore; // 리스트 체크 후 삭제, 수정, 저장 버튼
    ListView storedSmsListView;
    WriteAdapter adapter;  //
    RealmResults<WriteSmsVO> datas; // 저장문자 DB 데이터
    private HashMap<Integer, Boolean> mapSelected;
    private HashMap<WriteSmsVO, Boolean> mapSelected2;
    ArrayList<WriteSmsVO> checkedSmsList;

    private final int REQUESTCODE_STORE = 1;

    public WriteFragment() {
        realm = Realm.getDefaultInstance();

//        for(WriteSmsVO writeSmsVO : datas){
//            mapSelected.put(writeSmsVO, false);  // sms 저장문자 데이터베이스의 크기만큼 writeSmsVo(키) 를 false(값)로 설정.
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_write, container, false);

        init();
        setButtonClickListener();
        setListView();

        return view;
    }

    private void init() {
        datas = realm.where(WriteSmsVO.class).findAll().sort("id", Sort.ASCENDING);
        mapSelected = new HashMap<>();
        mapSelected2 = new HashMap<>();

        for(int i=0;  i>datas.size(); i++){
            mapSelected.put(i, false);  // sms 저장문자 데이터베이스의 크기만큼 writeSmsVo(키) 를 false(값)로 설정.
        }

        for(WriteSmsVO so : datas){
            mapSelected2.put(so, false);  // 연락처(datas) 길이만큼 contactVo(키) 를 false(값)로 설정.
        }

        btnAddContent = (Button) view.findViewById(R.id.btnAddContent);
        btnDelete = (Button) view.findViewById(R.id.btnDelete);
        btnUpdate = (Button) view.findViewById(R.id.btnUpdate);
        btnStore = (Button) view.findViewById(R.id.btnStore);
        storedSmsListView = (ListView) view.findViewById(R.id.storedSmsListView);
        adapter = new WriteAdapter(datas, getContext(), mapSelected);

        adapter.setBtnUpClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                WriteSmsVO upWriteSmsVO = adapter.getItem(position);

                int beforeIndex = position-1;
                WriteSmsVO downWriteSmsVO = adapter.getItem(beforeIndex);
                long tempDownId = downWriteSmsVO.getId();
                Boolean tempDownChecked = mapSelected.get(beforeIndex);

                realm.beginTransaction();
                downWriteSmsVO.setId(upWriteSmsVO.getId());
                upWriteSmsVO.setId(tempDownId);
                realm.commitTransaction();
                mapSelected.put(beforeIndex, mapSelected.get(position));
                mapSelected.put(position, tempDownChecked);

                datas = realm.where(WriteSmsVO.class).findAll().sort("id", Sort.ASCENDING);
                adapter.setDatas(datas);
            }
        });

        adapter.setBtnDownClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                WriteSmsVO downWriteSmsVO = adapter.getItem(position);

                int afterIndex = position+1;
                WriteSmsVO upWriteSmsVO = adapter.getItem(afterIndex);
                long tempUpId = upWriteSmsVO.getId();
                Boolean tempUpChecked = mapSelected.get(afterIndex);

                realm.beginTransaction();
                upWriteSmsVO.setId(downWriteSmsVO.getId());
                downWriteSmsVO.setId(tempUpId);
                realm.commitTransaction();
                mapSelected.put(afterIndex, mapSelected.get(position));
                mapSelected.put(position, tempUpChecked);

                adapter.setDatas(datas);
            }
        });
    }


    private void setButtonClickListener(){
        btnAddContent.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAddContent :
                Intent intent = new Intent(getContext(), AddSmsActivity.class);
                //startActivity(intent);
                startActivityForResult(intent, REQUESTCODE_STORE);
                break;

            case R.id.btnDelete :
                doDelete();
                break;

            case R.id.btnUpdate :
                break;

            case R.id.btnStore :
                break;
        }
    }

    private void doDelete() {
        checkedSmsList = adapter.getKey(mapSelected2, true);
        Log.i(TAG, "checkedSmsList ========== : "+ checkedSmsList);
    }

    public void setListView(){
        storedSmsListView.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == REQUESTCODE_STORE){
                adapter.notifyDataSetChanged();
            }
        }
    }
}
