package com.example.tenmanager_1;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tenmanager_1.Data.CallHistoryData;
import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.Fragment.FindContactFragment.ContactFragment;
import com.example.tenmanager_1.Fragment.FindContactFragment.GroupFragment;
import com.example.tenmanager_1.Fragment.FindContactFragment.RecentCallFragment;
import com.example.tenmanager_1.Fragment.FindContactFragment.SelectedDataModel;
import com.example.tenmanager_1.Fragment.FindContactFragment.checkBoxChangeListener;

import java.util.ArrayList;

public class FindContactActivity extends AppCompatActivity implements View.OnClickListener, checkBoxChangeListener {
    private final String TAG = FindContactActivity.class.getSimpleName();
    private final int FRAGMENT_CONTACT = 1;
    private final int FRAGMENT_CALLHISTORY = 2;
    private final int FRAGMENT_GROUP = 3;

    private  int currentFragment = 0;

    TextView btnContact, btnRecentCall, btnGroup;

    ContactFragment contactFragment = new ContactFragment();  // 연락처탭
    RecentCallFragment recentCallFragment = new RecentCallFragment(); // 최근통화목록탭
    GroupFragment groupFragment = new GroupFragment(); // 그룹탭

    GroupFragment groupFragmentManager;
    ContactFragment contactFragmentManager;
    RecentCallFragment recentCallFragmentManager;

    EditText editTxtSearch;
    Button btnComplete;
    TextView txtSelectedNumber;
    TextView txtSelectedName;

    ArrayList<SelectedDataModel> selectedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_contact);
        initView();
        setButtonClickListener();
        setTextChangeListener();
        setFragmentManager();
        callFragment(FRAGMENT_CONTACT);
    }

    private void initView() {
        btnContact = (TextView) findViewById(R.id.btnContact);
        btnRecentCall = (TextView) findViewById(R.id.btnRecentCall);
        btnGroup = (TextView) findViewById(R.id.btnGroup);
        editTxtSearch = (EditText) findViewById(R.id.editTxtSearch);
        btnComplete = (Button) findViewById(R.id.btnComplete);
        txtSelectedName = (TextView) findViewById(R.id.txtSelectedName);
        txtSelectedNumber = (TextView) findViewById(R.id.txtSelectedNumber);
    }

    private void setFragmentManager(){
        contactFragmentManager = (ContactFragment) getSupportFragmentManager().findFragmentByTag("CONTACT_FRAGMENT");
        recentCallFragmentManager = (RecentCallFragment) getSupportFragmentManager().findFragmentByTag("RECENT_CALL_FRAGMENT");
        groupFragmentManager = (GroupFragment) getSupportFragmentManager().findFragmentByTag("GROUP_FRAGMENT");
        selectedList = new ArrayList<>();
    }

/*    public void setSelectedText(){
        adapter.setOnMyItemCheckedChanged(new ContactAdapter.OnMyItemCheckedChanged() {
            @Override
            public void onItemCheckedChanged(ContactVO data, boolean isChecked) {
                txtSelectedName.setText(data.getName());
            }
        });
    }*/

    public void setTextChangeListener(){
        editTxtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(currentFragment == FRAGMENT_CONTACT){
                    contactFragment.search(editTxtSearch.getText().toString());
                }
                else if(currentFragment == FRAGMENT_CALLHISTORY){

                }
            }
        });
        //checkedContactResult = new ArrayList<>();
        doSearch();
    }

    public void setButtonClickListener(){
        btnContact.setOnClickListener(this);
        btnRecentCall.setOnClickListener(this);
        btnGroup.setOnClickListener(this);
        btnComplete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnContact :
                callFragment(FRAGMENT_CONTACT);
                break;

            case R.id.btnRecentCall :
                callFragment(FRAGMENT_CALLHISTORY);
                break;

            case R.id.btnGroup :
                callFragment(FRAGMENT_GROUP);
                break;
            case R.id.btnComplete :
                getAllSelectedList();
                break;
        }
    }

    public void callFragment(int fragmentNo) {
        currentFragment = fragmentNo;
        // 프래그먼트 사용을 위해 트랜잭션 객체 선언
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (fragmentNo) {
            case 1:
                transaction.replace(R.id.fragment_contact_container, contactFragment, "CONTACT_FRAGMENT");
                transaction.commit();
                break;

            case 2:
                transaction.replace(R.id.fragment_contact_container, recentCallFragment, "RECENT_CALL_FRAGMENT");
                transaction.commit();
                break;

            case 3:
                transaction.replace(R.id.fragment_contact_container, groupFragment, "GROUP_FRAGMENT");
                transaction.commit();
                break;
        }
    }

    public void getAllSelectedList(){
        ArrayList<ContactVO> list = contactFragment.getCheckedContactList();
        for(int i= 0; i<list.size(); i++){
            SelectedDataModel model = new SelectedDataModel();
            model.setName(list.get(i).getName());
            model.setPhoneNumber(list.get(i).getCellPhone());
            selectedList.add(model);
        }
        ArrayList<CallHistoryData> callList = recentCallFragment.getCheckedCallHistoryList();
        for(int i= 0; i<callList.size(); i++){
            SelectedDataModel model = new SelectedDataModel();
            if(callList.get(i).getName() != null){
                model.setName(callList.get(i).getName());
            }else{
                model.setName(callList.get(i).getTel());
            }

            model.setPhoneNumber(callList.get(i).getTel());
            selectedList.add(model);
        }
        ArrayList<ContactVO> groupList = groupFragment.getCheckedGroupList();
        for(int i=0; i<groupList.size(); i++){
            SelectedDataModel model = new SelectedDataModel();
            model.setName(groupList.get(i).getName());
            model.setPhoneNumber(groupList.get(i).getCellPhone());
            selectedList.add(model);
        }

        Intent intent = getIntent();
        intent.putExtra("listObject", selectedList);
        setResult(RESULT_OK, intent);

        for(int i=0; i<selectedList.size(); i++){
            Log.i(TAG, "All List ================== " + selectedList.get(i).getName());
        }

        finish();
    }

    public void doSearch(){
        editTxtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = editTxtSearch.getText().toString();
                //search(text);
                contactFragment.search(text);
            }
        });
    }

/*    String resultName = "";
    @Override
    public void callbackMethod(String name) {
        if(!(resultName.equals(""))){
            resultName = resultName + name;
        }else{
            resultName = name;
        }
        txtSelectedName.setText(resultName);
    }

    @Override
    public void callbakcMethod() {
        txtSelectedName.setText("");
    }*/

    ArrayList<String> resultName = new ArrayList<>();
    @Override
    public void callbackChecked(String name) {
        String result="";
        resultName.add(name);
        for(int i=0; i<resultName.size(); i++){
            if(i<resultName.size()-1){
                result = result + resultName.get(i) + ", ";
            }else{
                result = result + resultName.get(i);
            }
        }
        txtSelectedName.setText("("+result+")");
        txtSelectedNumber.setText(resultName.size()+""); // +""공백문자열 넣어서 String으로 캐스팅하지 않으면 에러 발생. 이유 알기..// TODO: 2017-11-28  
    }

    @Override
    public void callbackUnChecked(String name) {
        String result="";
        resultName.remove(name);
        for(int i=0; i<resultName.size(); i++){
            if(i<resultName.size()-1){
                result = result + resultName.get(i) + ", ";
            }else{
                result = result + resultName.get(i);
            }
        }
        txtSelectedName.setText("("+result+")");
        txtSelectedNumber.setText(resultName.size()+"");
    }
}