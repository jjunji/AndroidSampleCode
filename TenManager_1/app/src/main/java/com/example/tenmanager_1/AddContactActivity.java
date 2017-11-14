package com.example.tenmanager_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tenmanager_1.Data.AddContactVO;
import com.example.tenmanager_1.Data.WriteSmsVO;

import io.realm.Realm;

public class AddContactActivity extends AppCompatActivity {
    private final String TAG = AddContactActivity.class.getSimpleName();
    Realm realm;
    Button btnSave;
    EditText etName, etCall1, etCall2, etPhoneNumber, etAddress, etMemo, etCallMemo;
    Spinner spinner;
    int flag = 0;
    private final int ADDCONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        init();
        checkIntent(flag);
        setBtnSave();
    }

    private void init() {
        realm = Realm.getDefaultInstance();
        btnSave = (Button) findViewById(R.id.btnSave);
        etName = (EditText) findViewById(R.id.etName);
        etCall1 = (EditText) findViewById(R.id.etCall1);
        etCall2 = (EditText) findViewById(R.id.etCall2);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNum);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etMemo = (EditText) findViewById(R.id.etMemo);
        etCallMemo = (EditText) findViewById(R.id.etCallMemo);
        spinner = (Spinner) findViewById(R.id.spinner);

        flag = getIntent().getExtras().getInt("flag");  // 출처 판별.
    }

    public void setBtnSave(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == ADDCONTACT){
                    doStore(realm);
                    // 데이터를 넣을 필요가 없으니 putExtra는 필요없고 종료메서드 호출 후 돌아갔을 때 setNotifychanged 만 되게끔 하면 될텐데..
                    Intent intent = getIntent();
                    intent.putExtra("test", true);
                    Log.i(TAG, realm.where(AddContactVO.class).findAll().toString());
                    Toast.makeText(AddContactActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    public void doStore(Realm realm){
        // 트랜잭션을 통해 데이터 영속화.
        realm.beginTransaction();
        AddContactVO aco = realm.createObject(AddContactVO.class);

        Number maxid = realm.where(AddContactVO.class).max("id");
        int id = 1; // 빈 데이터베이스면 index 1로 시작.
        if(maxid != null){ // 비어있지 않으면 마지막 index + 1
            id = maxid.intValue()+1;
        }

        aco.setId(id);
        //wso.setTitle(etTitle.getText().toString());
        //wso.setContent(etContent.getText().toString());
        aco.setName(etName.getText().toString());
        aco.setCall1(etCall1.getText().toString());
        aco.setCall2(etCall2.getText().toString());
        aco.setPhoneNumber(etPhoneNumber.getText().toString());
        aco.setAddress(etAddress.getText().toString());
        aco.setMemo(etMemo.getText().toString());
        aco.setCallMemo(etCallMemo.getText().toString());

        realm.commitTransaction();
    }

    private void checkIntent(int flag){
        switch (flag){
            case ADDCONTACT:
                break;
        }
    }
}
