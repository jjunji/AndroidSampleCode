package com.example.tenmanager_1.CustomerGroup;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tenmanager_1.Data.ContactGroupVO;
import com.example.tenmanager_1.R;
import io.realm.Realm;


public class SettingGroupActivity extends AppCompatActivity {
    Realm realm;
    EditText etSearch;
    Button btnAddGroup;
    ListView groupListView;
    GroupListAdapter adapter;
    UpdateGroupDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_group);

        initView();
        realm = Realm.getDefaultInstance();
        adapter = new GroupListAdapter();
        setListView();
        setButton();
        setAdapterButton();
    }

    private void initView() {
        etSearch = (EditText) findViewById(R.id.etSearch);
        btnAddGroup = (Button) findViewById(R.id.btnAddGroup);
        groupListView = (ListView) findViewById(R.id.groupListView);
    }

    private void setListView(){
        groupListView.setAdapter(adapter);
//        groupListView.setFastScrollEnabled(true);
    }

    private void setButton(){
        btnAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etSearch.getText().toString().equals("")){
                    Toast.makeText(SettingGroupActivity.this, "추가할 그룹명을 작성하세요", Toast.LENGTH_SHORT).show();
                }else{
                    addGroup();
                    Toast.makeText(SettingGroupActivity.this, "추가 되었습니다.", Toast.LENGTH_SHORT).show();
                    etSearch.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void addGroup(){
        String groupName = etSearch.getText().toString();

        realm.beginTransaction();

        Number maxid = realm.where(ContactGroupVO.class).max("id");
        long id = 1;
        if(maxid != null){
            id = maxid.intValue()+1;
        }

        ContactGroupVO cgo =realm.createObject(ContactGroupVO.class, id);
        cgo.setName(groupName);

        realm.commitTransaction();
    }

    private void setAdapterButton(){
        adapter.setBtnUpdateListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                ContactGroupVO contactGroupVO = adapter.getItem(position);
                Long id = contactGroupVO.getId();

                dialog = new UpdateGroupDialog(SettingGroupActivity.this, id);
                dialog.show();
            }
        });

        adapter.setBtnDeleteListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = (int) v.getTag();
                ContactGroupVO contactGroupVO = adapter.getItem(position);
                final Long id = contactGroupVO.getId();

                AlertDialog.Builder builder = new AlertDialog.Builder(SettingGroupActivity.this);
                builder.setTitle("삭제하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override // final RealmResults<ContactGroupVO> results = realm.where(ContactGroupVO.class).findAll();
                    public void onClick(DialogInterface dialog, int which) {
                        realm.beginTransaction();
                        ContactGroupVO cgo = realm.where(ContactGroupVO.class).equalTo("id", id).findFirst();
                        cgo.deleteFromRealm();
                        realm.commitTransaction();
                        Toast.makeText(SettingGroupActivity.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                dialog.show();    // 알림창 띄우기
            }
        });
    }
}
