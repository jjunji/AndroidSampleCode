package com.example.tenmanager_1.CustomerGroup;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tenmanager_1.MyApplication;
import com.example.tenmanager_1.R;
/*
CustomerFragment(고객 프래그먼트) -> CustomerGroupFragment(child) -> dialog 호출.
다이얼로그에서 항목 선택시 각 항목으로 이동 (SettingGroupActivity / UpdateGroupActivity)

 */

public class AddCustomerGroupDialog extends Dialog implements View.OnClickListener{
    TextView txtUpdateGroup, txtSettingGroup;
    Button btnOk;
    Context context;


    public AddCustomerGroupDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer_group_dialog);

        initView();
        setButtonListener();
    }

    private void initView() {
        txtUpdateGroup = (TextView) findViewById(R.id.txtUpdateGroup);
        txtSettingGroup = (TextView) findViewById(R.id.txtSettingGroup);
        btnOk = (Button) findViewById(R.id.btnOk);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtSettingGroup :
                context.startActivity(new Intent(context, SettingGroupActivity.class));
                break;
            case R.id.txtUpdateGroup :
                context.startActivity(new Intent(context, UpdateGroupActivity.class));
                break;
            case R.id.btnOk :
                dismiss();
                break;
        }
    }

    public void setButtonListener(){
        txtSettingGroup.setOnClickListener(this);
        txtUpdateGroup.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }
}
