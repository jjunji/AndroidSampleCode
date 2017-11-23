package com.example.tenmanager_1.CustomerGroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tenmanager_1.CustomerGroup.Util.UpdateGroupAdapter;
import com.example.tenmanager_1.Data.ContactGroupVO;
import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.R.attr.id;

public class UpdateGroupActivity extends AppCompatActivity {
    private final String TAG = UpdateGroupActivity.class.getSimpleName();
    Realm realm;
    ArrayList<ContactVO> datas;
    RealmResults<ContactVO> results;
    ListView listView;
    Spinner spinnerItem, spinnerChangeItem;
    Button btnUpdate;
    UpdateGroupAdapter adapter;
    private HashMap<ContactVO, Boolean> mapSelected;
    ArrayList<ContactVO> checkedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_group);

        init();
        initView();
        setListView();
        setSpinner();
        setButton();
        searchContactByGroup();
    }

    private void init() {
        datas = new ArrayList<>();
        realm = Realm.getDefaultInstance();
        results = realm.where(ContactVO.class).findAll();
        mapSelected = new HashMap<>();

        for(ContactVO contactVO : results){
            datas.add(contactVO);
        }

        for(ContactVO contactVO : datas){
            mapSelected.put(contactVO, false);
        }

        adapter = new UpdateGroupAdapter(this, datas, mapSelected);
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.itemizeListView);
        spinnerItem = (Spinner) findViewById(R.id.spinnerItem);
        spinnerChangeItem = (Spinner) findViewById(R.id.spinnerChangeItem);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
    }

    private void setListView(){
        listView.setAdapter(adapter);
    }

    // 분류 스피너, 변경 스피너 정의
    private void setSpinner(){
        RealmResults<ContactGroupVO> gro = realm.where(ContactGroupVO.class).findAll();
        List<String> spinner_items = new ArrayList<>();

        //스피너와 리스트를 연결하기 위해 사용되는 어댑터
        ArrayAdapter<String> spinner_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinner_items);
        for(int i=0; i<gro.size(); i++){
            spinner_items.add(gro.get(i).getName());
        }
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChangeItem.setAdapter(spinner_adapter);
        spinnerItem.setAdapter(spinner_adapter);

/*        spinnerItem.getSelectedItem();
        spinnerChangeItem.getSelectedItem();*/
    }

    private void setButton(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "checkedItem ======= " + spinnerChangeItem.getSelectedItem());
                Log.i(TAG, "checkList ========= " + checkedList);

                doUpdate();
                Toast.makeText(UpdateGroupActivity.this, "변경되었습니다.", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
        });
    }

    // TODO: 2017-11-23
    private void doUpdate() {
        checkedList = adapter.getKey(mapSelected, true);
        int selectedGroupPosition = spinnerChangeItem.getSelectedItemPosition();  // 누른 스피너 위치
        Log.i("test", "selectedGroupPosition :" + selectedGroupPosition);

        ContactGroupVO selectedGroupVO = realm.where(ContactGroupVO.class).findAll().get(selectedGroupPosition);  // 누른 스피너에 해당하는 그룹
        Log.i(TAG, "selectedGroupVO ========== " + selectedGroupVO);
        if(selectedGroupVO != null){
            realm.beginTransaction();

            for(int i=0; i<checkedList.size(); i++){
                ContactVO contactVO = realm.where(ContactVO.class).equalTo("id", checkedList.get(i).getId()).findFirst();  // 선택된 고객의 연락처
                if(contactVO != null){
                    Log.i(TAG, "contact ========= " + contactVO);
                    contactVO.setGroup(selectedGroupVO);
                }
            }
            realm.commitTransaction();
        }

    }

    // TODO: 2017-11-23
    private void searchContactByGroup(){
        spinnerItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ContactGroupVO selectedGroupVO = realm.where(ContactGroupVO.class).findAll().get(position);
                RealmResults<ContactVO> arContact = realm.where(ContactVO.class).equalTo("group.id", selectedGroupVO.getId()).findAll();
                datas.clear();
                for(ContactVO contactVO : arContact){
                    datas.add(contactVO);
                }
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}