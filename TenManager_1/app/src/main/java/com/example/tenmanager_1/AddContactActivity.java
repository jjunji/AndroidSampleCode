package com.example.tenmanager_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tenmanager_1.Data.ContactGroupVO;
import com.example.tenmanager_1.Data.ContactVO;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class AddContactActivity extends AppCompatActivity {
    private final String TAG = AddContactActivity.class.getSimpleName();

    Realm realm;
    Button btnSave;
    EditText etName, etCall1, etCall2, etPhoneNumber, etAddress, etMemo, etCallMemo;
    Spinner spinner;
    int flag = 0;
    private final int ADDCONTACT = 1;
    private final int UPDATE = 2;
    private final int FROMBLANK = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        init();
        checkIntent(flag);
        setBtnSave();
        setSpinner();
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

    public void setBtnSave() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == ADDCONTACT) {
                    if (!TextUtils.isEmpty(etName.getText()) && !TextUtils.isEmpty(etPhoneNumber.getText())) {
                        doStore(realm);
                        //doContactStore(realm);
                        Intent intent = getIntent();
                        intent.putExtra("test", true);
                        Log.i(TAG, realm.where(ContactVO.class).findAll().toString());
                        Toast.makeText(AddContactActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                } else if (flag == UPDATE) {
                    updateContact();
                    Intent intent = getIntent();
                    intent.putExtra("test", true);
                    Toast.makeText(AddContactActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK, intent);
                    finish();
                } else if (flag == FROMBLANK) {
                    doStore(realm);
                    Toast.makeText(AddContactActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void updateContact() {
        realm.beginTransaction();

        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("id"); // 리스트 항목에 해당하는 DB의 id값.
        ContactVO cvo = realm.where(ContactVO.class).equalTo("id", id).findFirst();
        cvo.setName(etName.getText().toString());
        cvo.setCellPhone(etPhoneNumber.getText().toString());
        cvo.setTel1(etCall1.getText().toString());
        cvo.setTel2(etCall2.getText().toString());
        cvo.setAddress(etAddress.getText().toString());
        cvo.setMemo(etMemo.getText().toString());

//        cvo.setCallMemo(etCallMemo.getText().toString());

        realm.commitTransaction();
    }

    public void doStore(Realm realm) {
        // 트랜잭션을 통해 데이터 영속화.
        realm.beginTransaction();
        //AddContactVO aco = realm.createObject(AddContactVO.class);

        Number maxid = realm.where(ContactVO.class).max("id");
        int id = 1; // 빈 데이터베이스면 index 1로 시작.
        if (maxid != null) { // 비어있지 않으면 마지막 index + 1
            id = maxid.intValue() + 1;
        }

        ContactVO cvo = realm.createObject(ContactVO.class, id);

        //cvo.setId(id);
        cvo.setName(etName.getText().toString());
        cvo.setTel1(etCall1.getText().toString());
        cvo.setTel2(etCall2.getText().toString());
        cvo.setCellPhone(etPhoneNumber.getText().toString());
        cvo.setAddress(etAddress.getText().toString());
        cvo.setMemo(etMemo.getText().toString());
//        cvo.setCallMemo(etCallMemo.getText().toString());

        int selectedGroupPosition = spinner.getSelectedItemPosition();  // 누른 스피너 위치
        ContactGroupVO selectedGroupVO = realm.where(ContactGroupVO.class).findAll().get(selectedGroupPosition);  // 누른 스피너에 해당하는 그룹

        cvo.setGroup(selectedGroupVO);

        realm.commitTransaction();
    }

    private void checkIntent(int flag) {
        Bundle bundle;
        String phoneNumber;

        switch (flag) {
            case ADDCONTACT:
                etName.setText("");
                etPhoneNumber.setText("");
                etCall1.setText("");
                etCall2.setText("");
                etAddress.setText("");
                etMemo.setText("");
                etCallMemo.setText("");
                break;

            case UPDATE:
                bundle = getIntent().getExtras();
                String name = bundle.getString("name");
                String call1 = bundle.getString("call1");
                String call2 = bundle.getString("call2");
                phoneNumber = bundle.getString("phoneNumber");
                String address = bundle.getString("address");
                String memo = bundle.getString("memo");
                String callMemo = bundle.getString("callMemo");
                //
                etName.setText(name);
                etCall1.setText(call1);
                etCall2.setText(call2);
                etPhoneNumber.setText(phoneNumber);
                etAddress.setText(address);
                etMemo.setText(memo);
                etCallMemo.setText(callMemo);
                break;

            case FROMBLANK:
                bundle = getIntent().getExtras();
                etName.setText("");
                phoneNumber = bundle.getString("phoneNumber");
                etPhoneNumber.setText(phoneNumber);
                etCall1.setText("");
                etCall2.setText("");
                etAddress.setText("");
                etMemo.setText("");
                etCallMemo.setText("");
        }
    }


    private void setSpinner() {
        RealmResults<ContactGroupVO> gro = realm.where(ContactGroupVO.class).findAll();
        List<String> spinner_items = new ArrayList<>();

        //스피너와 리스트를 연결하기 위해 사용되는 어댑터
        ArrayAdapter<String> spinner_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinner_items);
        for (int i = 0; i < gro.size(); i++) {
            spinner_items.add(gro.get(i).getName());
        }
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinner_adapter);
    }
}
