package com.example.tenmanager_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.Data.WriteSmsVO;

import io.realm.Realm;

public class AddSmsActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = AddSmsActivity.class.getSimpleName();
    EditText etTitle, etContent;
    Button btnCancel, btnStore;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sms);

        realm = Realm.getDefaultInstance();
        init();
        setButtonClickListener();
    }

    private void init() {
        etTitle = (EditText) findViewById(R.id.etTitle);
        etContent = (EditText) findViewById(R.id.etContent);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnStore = (Button) findViewById(R.id.btnStore);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCancel :
                finish();
                break;

            case R.id.btnStore :
                doStore(realm);
                // 데이터를 넣을 필요가 없으니 putExtra는 필요없고 종료메서드 호출 후 돌아갔을 때 setNotifychanged 만 되게끔 하면 될텐데..
                Intent intent = getIntent();
                intent.putExtra("test", true);
                Log.i(TAG, "store write date ========== "+ realm.where(WriteSmsVO.class).findAll());
                Toast.makeText(AddSmsActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    public void setButtonClickListener(){
        btnCancel.setOnClickListener(this);
        btnStore.setOnClickListener(this);
    }

    public void doStore(Realm realm){
        // 트랜잭션을 통해 데이터 영속화.
        realm.beginTransaction();
        WriteSmsVO wso = realm.createObject(WriteSmsVO.class); // 관리 객체 생성.

        Number maxid = realm.where(WriteSmsVO.class).max("id");
        int id = 1; // 빈 데이터베이스면 index 1로 시작.
        if(maxid != null){ // 비어있지 않으면 마지막 index + 1
            id = maxid.intValue()+1;
        }

        wso.setId(id);
        wso.setTitle(etTitle.getText().toString());
        wso.setContent(etContent.getText().toString());

        realm.commitTransaction();
    }

}
