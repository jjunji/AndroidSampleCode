package com.example.tenmanager_1.CustomerGroup;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tenmanager_1.Data.ContactGroupVO;
import com.example.tenmanager_1.R;

import io.realm.Realm;

public class UpdateGroupDialog extends Dialog {
    Realm realm;
    EditText etUpdate;
    Button btnOk;
    GroupListAdapter adapter;
    Long selectedId;

    public UpdateGroupDialog(@NonNull Context context, long id) {
        super(context);
        this.selectedId = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = realm.getDefaultInstance();
        setContentView(R.layout.activity_update_group_dialog);
        //Intent intent = new Intent();
       // selectedId = intent.getLongExtra("id", 0); // TODO: 2017-11-22 디폴트값 꼭 설정해야하는지
        etUpdate = (EditText) findViewById(R.id.etUpdate);
        btnOk = (Button) findViewById(R.id.btnOk);
        adapter = new GroupListAdapter();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUpdate();
                Toast.makeText(getContext(), "수정되었습니다.", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
                dismiss();
            }
        });

    }

    private void doUpdate(){
        String groupName = etUpdate.getText().toString();
        ContactGroupVO cgo = realm.where(ContactGroupVO.class).equalTo("id", selectedId).findFirst();
        realm.beginTransaction();
        cgo.setName(groupName);
        realm.commitTransaction();
    }
}
