package com.example.tenmanager_1;

import android.content.Intent;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tenmanager_1.Data.ContactGroupVO;
import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.Data.SmsGroupVO;
import com.example.tenmanager_1.Data.SmsVO;
import com.example.tenmanager_1.repositories.impl.SmsRepository;
import com.example.tenmanager_1.repositories.service.SmsDataSource;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class AddSmsActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = AddSmsActivity.class.getSimpleName();
    private final int ADDSMS = 1;
    private final int UPDATE = 2;
    int flag = 0;  // WriteAdapter(리스트 아이템 클릭시 호출된건지), WriteFragment(문자내용 추가하기 버튼 눌러서 호출된건지 판별)
    EditText etTitle, etContent;
    Button btnCancel, btnStore;
    Spinner seperateSpinner;
    List<String> spinner_items;
    ArrayAdapter<String> spinner_adapter;
    SmsDataSource smsDataSource;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sms);

        realm = Realm.getDefaultInstance();
        initView();
        init();
        checkIntent(flag);
        setButtonClickListener();
    }

    private void initView() {
        etTitle = (EditText) findViewById(R.id.etTitle);
        etContent = (EditText) findViewById(R.id.etContent);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnStore = (Button) findViewById(R.id.btnStore);
        seperateSpinner = (Spinner) findViewById(R.id.seperateSpinner);
        flag = getIntent().getExtras().getInt("flag");  // 출처 판별.
    }

    private void init() {
        smsDataSource = new SmsRepository();
        spinner_items = new ArrayList<>();
/*        spinner_items.add("저장문자");
        spinner_items.add("대표안내문자");
        spinner_items.add("매물홍보문자");*/

        for(int i=0; i<smsDataSource.getSmsGroupList().size(); i++){
            spinner_items.add(smsDataSource.getSmsGroupList().get(i).getName());
        }

        spinner_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinner_items);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seperateSpinner.setAdapter(spinner_adapter);
    }

    private void checkIntent(int flag){
        switch (flag){
            case ADDSMS:
                etTitle.setText("");
                etContent.setText("");
                break;

            case UPDATE:
                updateSms();
                break;
        }
    }

    // 저장된 저장문자 수정하기.
    private void updateSms() {
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        String content = bundle.getString("content");
        etTitle.setText(title);
        etContent.setText(content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // 공통으로 사용.
            case R.id.btnCancel :
                finish();
                break;

            // 호출 flag에 따라 각각에 맞는 이벤트 처리.
            case R.id.btnStore :
                if(flag == ADDSMS){
                    doStore(realm);
                    // 데이터를 넣을 필요가 없으니 putExtra는 필요없고 종료메서드 호출 후 돌아갔을 때 setNotifychanged 만 되게끔 하면 될텐데..
                    Intent intent = getIntent();
                    intent.putExtra("test", true);
                    //Log.i(TAG, "store write date ========== "+ realm.where(WriteSmsVO.class).findAll());
                    Toast.makeText(AddSmsActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                }else if(flag == UPDATE){
                    Toast.makeText(this, "수정되었습니다.", Toast.LENGTH_SHORT).show();
                    doStore2(realm);
                    Intent intent = new Intent();
                    intent.putExtra("test2", true);
                    setResult(RESULT_OK, intent);
                    finish();
                }
        }
    }

    public void setButtonClickListener(){
        btnCancel.setOnClickListener(this);
        btnStore.setOnClickListener(this);
    }

    public void doStore(Realm realm){
        // 트랜잭션을 통해 데이터 영속화.
        realm.beginTransaction();

        Number maxid = realm.where(SmsVO.class).max("id");
        int id = 1; // 빈 데이터베이스면 index 1로 시작.
        if(maxid != null){ // 비어있지 않으면 마지막 index + 1
            id = maxid.intValue()+1;
        }

        SmsVO svo = realm.createObject(SmsVO.class, id); // 관리 객체 생성.
        //wso.setId(id);
        svo.setRegdate(System.currentTimeMillis());
        svo.setTitle(etTitle.getText().toString());
        svo.setContent(etContent.getText().toString());
        svo.setRegdate(System.currentTimeMillis());

        int selectedGroupPosition = seperateSpinner.getSelectedItemPosition();  // 누른 스피너 위치
        SmsGroupVO selectedGroupVO = realm.where(SmsGroupVO.class).findAll().get(selectedGroupPosition);
        svo.setGroup(selectedGroupVO);
        Log.i(TAG, "selectedGroup =========== " + selectedGroupVO);

        realm.commitTransaction();
    }

    private void doStore2(Realm realm) {
        realm.beginTransaction();
        Bundle bundle = getIntent().getExtras();
        long id = bundle.getLong("id"); // 리스트 항목에 해당하는 DB의 id값.
        SmsVO svo = realm.where(SmsVO.class).equalTo("id", id).findFirst();  // TODO: 2017-11-13  findFirst() :
        svo.setTitle(etTitle.getText().toString());
        svo.setContent(etContent.getText().toString());

        int selectedGroupPosition = seperateSpinner.getSelectedItemPosition();  // 누른 스피너 위치
        SmsGroupVO selectedGroupVO = realm.where(SmsGroupVO.class).findAll().get(selectedGroupPosition);
        svo.setGroup(selectedGroupVO);

        realm.commitTransaction();
    }

}
