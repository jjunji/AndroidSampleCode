package com.example.tenmanager_1.Fragment.SmsFragment_Child;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tenmanager_1.AddSmsActivity;
import com.example.tenmanager_1.Data.ContactGroupVO;
import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.Data.SmsGroupVO;
import com.example.tenmanager_1.Data.SmsVO;
import com.example.tenmanager_1.Fragment.CustomerFragment_Child.ItemizedListAdapter;
import com.example.tenmanager_1.R;
import com.example.tenmanager_1.WriteUtil.WriteAdapter;
import com.example.tenmanager_1.WriteUtil.WriteViewHolder;
import com.example.tenmanager_1.repositories.impl.SmsRepository;
import com.example.tenmanager_1.repositories.service.SmsDataSource;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageSmsFragment extends Fragment implements View.OnClickListener{
    private final String TAG = ManageSmsFragment.class.getSimpleName();
    Realm realm;
    View view;
    Button btnAddContent;  // 문자내용 추가하기
    Button btnDelete, btnStore; // 리스트 체크 후 삭제, 수정, 저장 버튼
    ListView storedSmsListView;
    WriteAdapter adapter;
    Spinner spinner_Group;
    ArrayList<String> spinner_items;
    ArrayAdapter<String> spinner_adapter;
    SmsDataSource smsDataSource;

    private final int REQUESTCODE_STORE = 1;
    private final int REQUESTCODE_UPDATE = 2;

    public ManageSmsFragment() {
        realm = Realm.getDefaultInstance();

//        for(WriteSmsVO writeSmsVO : datas){
//            mapSelected.put(writeSmsVO, false);  // sms 저장문자 데이터베이스의 크기만큼 writeSmsVo(키) 를 false(값)로 설정.
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_write, container, false);
        RealmResults<SmsVO> datas = realm.where(SmsVO.class).findAll().sort("regdate", Sort.ASCENDING);
        adapter = new WriteAdapter(datas, getContext());
        adapter.notifyDataSetChanged();
        init();
        setButtonClickListener();
        //setListView();
        reloadGroupList(0);

        return view;
    }

    private void init() {
        smsDataSource = new SmsRepository();
        spinner_Group = (Spinner) view.findViewById(R.id.spinner_Group);

        spinner_items = new ArrayList<>();
        for(int i=0; i<smsDataSource.getSmsGroupList().size(); i++){
            spinner_items.add(smsDataSource.getSmsGroupList().get(i).getName());
        }

        spinner_adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, spinner_items);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_Group.setAdapter(spinner_adapter);

        spinner_Group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reloadGroupList(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAddContent = (Button) view.findViewById(R.id.btnAddContent);
        btnDelete = (Button) view.findViewById(R.id.btnDelete);
        //btnUpdate = (Button) view.findViewById(R.id.btnUpdate);
        btnStore = (Button) view.findViewById(R.id.btnStore);
        storedSmsListView = (ListView) view.findViewById(R.id.storedSmsListView);
    }

    private void setButtonClickListener(){
        btnAddContent.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    private void setAdapterButtonListener(){
        adapter.setHolderClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteViewHolder holder = (WriteViewHolder) v.getTag();
                int position = holder.getTag();
                SmsVO writeSmsVO = adapter.getItem(position);
                //Log.i("Adapter", String.valueOf(adapter.getItemId()));
                Intent intent = new Intent(getContext(), AddSmsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("flag", 2);
                // bundle.putSerializable("smsObject", writeSmsVO); // Realm 객체 전달 불가.
                // putExtra 여러개 사용할 때 bundle에 담아서.
                bundle.putString("title", writeSmsVO.getTitle());
                bundle.putString("content", writeSmsVO.getContent());
                bundle.putLong("id", writeSmsVO.getId());
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUESTCODE_UPDATE);
            }
        });

        adapter.setBtnUpClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"click/////");
                int position = (int) v.getTag();
                SmsVO upWriteSmsVO = adapter.getItem(position);

                int beforeIndex = position-1;
                SmsVO downWriteSmsVO = adapter.getItem(beforeIndex);

                long regdate = downWriteSmsVO.getRegdate();
                Boolean tempDownChecked = adapter.getMapSelected().get(beforeIndex);

                realm.beginTransaction();
                downWriteSmsVO.setRegdate(upWriteSmsVO.getRegdate());
                upWriteSmsVO.setRegdate(regdate);
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
                SmsVO downWriteSmsVO = adapter.getItem(position);

                int afterIndex = position+1;
                SmsVO upWriteSmsVO = adapter.getItem(afterIndex);
                long tempRegdate = upWriteSmsVO.getRegdate();
                Boolean tempUpChecked = adapter.getMapSelected().get(afterIndex);

                realm.beginTransaction();
                upWriteSmsVO.setRegdate(downWriteSmsVO.getRegdate());
                downWriteSmsVO.setRegdate(tempRegdate);
                realm.commitTransaction();
                adapter.getMapSelected().put(afterIndex, adapter.getMapSelected().get(position));
                adapter.getMapSelected().put(position, tempUpChecked);

                //adapter.setDatas(datas);
                adapter.notifyDataSetChanged();
            }
        });
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

            case R.id.btnStore :
                break;
        }
    }

    private void doDelete() {
        ArrayList<SmsVO> checkedSmsList = adapter.getKey(true);
        Log.i(TAG, "checkedSmsList ========== : "+ checkedSmsList);
        //final RealmResults<WriteSmsVO> results = realm.where(WriteSmsVO.class).findAll().sort("id");

        realm.beginTransaction();
        for(int i=0; i<checkedSmsList.size(); i++){
            SmsVO writeSmsVO = checkedSmsList.get(i);
            Log.i(TAG, "result.get(CheckedSmsList.get(i) : " + checkedSmsList.get(i) + "==============" + checkedSmsList.get(i));

            writeSmsVO.deleteFromRealm();
        }
        realm.commitTransaction();
        adapter.initSelectedMap();
        adapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == REQUESTCODE_STORE){
                adapter.notifyDataSetChanged();
            }else if (requestCode == REQUESTCODE_UPDATE){
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void reloadGroupList(int position) {
        RealmResults<SmsGroupVO> arGroup = smsDataSource.getSmsGroupList();
        SmsGroupVO smsGroupVO = arGroup.get(position);
        Log.i("test", "selected group :"+smsGroupVO.toString());

    matchingContactByGroup(smsGroupVO);
}

private void matchingContactByGroup(SmsGroupVO sgvo){
        RealmResults<SmsVO> arSms = realm.where(SmsVO.class).equalTo("group.id", sgvo.getId()).findAll().sort("regdate", Sort.ASCENDING);
        Log.i(TAG, "arSms =========== " + arSms);
        adapter = new WriteAdapter(arSms, getContext());
        storedSmsListView.setAdapter(adapter);
        setAdapterButtonListener();
        }

        }