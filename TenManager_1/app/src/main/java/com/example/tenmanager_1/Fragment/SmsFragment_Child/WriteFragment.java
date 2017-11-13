package com.example.tenmanager_1.Fragment.SmsFragment_Child;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tenmanager_1.AddSmsActivity;
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
    WriteAdapter adapter;

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
        //setListViewClickListener();

        return view;
    }

    private void init() {
        btnAddContent = (Button) view.findViewById(R.id.btnAddContent);
        btnDelete = (Button) view.findViewById(R.id.btnDelete);
        //btnUpdate = (Button) view.findViewById(R.id.btnUpdate);
        btnStore = (Button) view.findViewById(R.id.btnStore);
        storedSmsListView = (ListView) view.findViewById(R.id.storedSmsListView);
        RealmResults<WriteSmsVO> datas = realm.where(WriteSmsVO.class).findAll().sort("id", Sort.ASCENDING);
        adapter = new WriteAdapter(datas, getContext());

        adapter.setBtnUpClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                WriteSmsVO upWriteSmsVO = adapter.getItem(position);

                int beforeIndex = position-1;
                WriteSmsVO downWriteSmsVO = adapter.getItem(beforeIndex);
                long tempDownId = downWriteSmsVO.getId();
                Boolean tempDownChecked = adapter.getMapSelected().get(beforeIndex);

                realm.beginTransaction();
                downWriteSmsVO.setId(upWriteSmsVO.getId());
                upWriteSmsVO.setId(tempDownId);
                realm.commitTransaction();
                adapter.getMapSelected().put(beforeIndex, adapter.getMapSelected().get(position));
                adapter.getMapSelected().put(position, tempDownChecked);

//                datas = realm.where(WriteSmsVO.class).findAll().sort("id", Sort.ASCENDING);
//                adapter.setDatas(datas);
                adapter.notifyDataSetChanged();
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
                Boolean tempUpChecked = adapter.getMapSelected().get(afterIndex);

                realm.beginTransaction();
                upWriteSmsVO.setId(downWriteSmsVO.getId());
                downWriteSmsVO.setId(tempUpId);
                realm.commitTransaction();
                adapter.getMapSelected().put(afterIndex, adapter.getMapSelected().get(position));
                adapter.getMapSelected().put(position, tempUpChecked);

                //adapter.setDatas(datas);
                adapter.notifyDataSetChanged();
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
                intent.putExtra("flag",1);
                startActivityForResult(intent, REQUESTCODE_STORE);
                break;

            case R.id.btnDelete :
                doDelete();
                break;

/*            case R.id.btnUpdate :
                doUpdate();
                break;*/

            case R.id.btnStore :
                break;
        }
    }


    private void doDelete() {
        ArrayList<WriteSmsVO> checkedSmsList = adapter.getKey(true);
        Log.i(TAG, "checkedSmsList ========== : "+ checkedSmsList);
        //final RealmResults<WriteSmsVO> results = realm.where(WriteSmsVO.class).findAll().sort("id");

        realm.beginTransaction();
        for(int i=0; i<checkedSmsList.size(); i++){
            WriteSmsVO writeSmsVO = checkedSmsList.get(i);
            Log.i(TAG, "result.get(CheckedSmsList.get(i) : " + checkedSmsList.get(i) + "==============" + checkedSmsList.get(i));

            writeSmsVO.deleteFromRealm();
        }
        realm.commitTransaction();
        adapter.initSelectedMap();
        adapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();

//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//
//            }
//        });

    }

    public void setListView(){
        storedSmsListView.setAdapter(adapter);
    }

/*    public void setListViewClickListener(){
        storedSmsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pos = (String)storedSmsListView.getItemAtPosition(position);
                //Toast.makeText(getContext(), pos+"번", Toast.LENGTH_SHORT).show();
                Log.i("WriteFragment", "position ============" + pos);
            }
        });
    }*/

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