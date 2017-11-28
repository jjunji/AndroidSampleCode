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

import java.util.ArrayList;

public class FindContactActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = FindContactActivity.class.getSimpleName();
    private final int FRAGMENT_CONTACT = 1;
    private final int FRAGMENT_CALLHISTORY = 2;
    private final int FRAGMENT_GROUP = 3;

    //private final static String FRAGMENT_TAG = "FRAGMENTB_TAG";

    private  int currentFragment = 0;

    TextView btnContact, btnRecentCall, btnGroup;
    //ArrayList<ContactVO> checkedContactResult;

    ArrayList<ContactVO> checkedList;

    ContactFragment contactFragment = new ContactFragment();  // 연락처탭
    RecentCallFragment recentCallFragment = new RecentCallFragment(); // 최근통화목록탭
    GroupFragment groupFragment = new GroupFragment(); // 그룹탭

    EditText editTxtSearch;
    Button btnComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_contact);
        init();
        setButtonClickListener();
        setTextChangeListener();
        callFragment(FRAGMENT_CONTACT);
    }

    private void init() {
        btnContact = (TextView) findViewById(R.id.btnContact);
        btnRecentCall = (TextView) findViewById(R.id.btnRecentCall);
        btnGroup = (TextView) findViewById(R.id.btnGroup);
        editTxtSearch = (EditText) findViewById(R.id.editTxtSearch);
        btnComplete = (Button) findViewById(R.id.btnComplete);
    }

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
                selectedContact();
                //selectedRecentCall();
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

    public void selectedContact2(){
        ArrayList arIndex = new ArrayList();
        ContactFragment contactFragment = (ContactFragment) getSupportFragmentManager().findFragmentByTag("CONTACT_FRAGMENT");
        ArrayList<ContactVO> list = contactFragment.getCheckedContactList();
        Log.i(TAG, "list================= " + list.toString());
        for(ContactVO contactVO : list){
            arIndex.add(new Long(contactVO.getId()));
//            Log.i("test", "selected contact : " + contactVO.toString());   // 체크한 contactVO 담겨있음.
        }
        Intent intent = getIntent();  // Sms프래그먼트로 부터 받은 intent
        intent.putExtra("listObject", arIndex);
        setResult(RESULT_OK, intent);

        finish();
    }

    public void selectedContact(){
        ArrayList<SelectedDataModel> selectedList = new ArrayList<>();
        ContactFragment contactFragment = (ContactFragment) getSupportFragmentManager().findFragmentByTag("CONTACT_FRAGMENT");
        ArrayList<ContactVO> list = contactFragment.getCheckedContactList();
        SelectedDataModel model = new SelectedDataModel();
        for(int i=0; i<list.size(); i++){
            model.setName(list.get(i).getName());
            model.setPhoneNumber(list.get(i).getCellPhone());
            selectedList.add(model);
        }

        Intent intent = getIntent();
        intent.putExtra("listObject", selectedList);
        setResult(RESULT_OK, intent);

        finish();
    }

    public void selectedRecentCall(){
        ArrayList arIndex = new ArrayList();
        RecentCallFragment recentCallFragment = (RecentCallFragment) getSupportFragmentManager().findFragmentByTag("RECENT_CALL_FRAGMENT");
        ArrayList<CallHistoryData> list = recentCallFragment.getCheckedCallHistoryList();
        Log.i(TAG, "call list ============= " + list.toString());
        for(CallHistoryData callHistoryData : list){
            arIndex.add(new String(callHistoryData.getTel()));
        }

        Intent intent = getIntent();
        intent.putExtra("listObject", arIndex);
        setResult(RESULT_OK, intent);

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

}