package com.example.tenmanager_1;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.Fragment.FindContactFragment.ContactFragment;
import com.example.tenmanager_1.Fragment.FindContactFragment.GroupFragment;
import com.example.tenmanager_1.Fragment.FindContactFragment.RecentCallFragment;

import java.util.ArrayList;

public class FindContactActivity extends AppCompatActivity implements View.OnClickListener{
    private final int FRAGMENT_CONTACT = 1;
    private final int FRAGMENT_CALLHISTORY = 2;
    private final int FRAGMENT_GROUP = 3;

    private  int currentFragment = 0;

    TextView btnContact, btnRecentCall, btnGroup;
    //ArrayList<ContactVO> checkedContactResult;  //

    ArrayList<ContactVO> checkedList;

    ContactFragment contactFragment = new ContactFragment();  // 연락처탭
    RecentCallFragment recentCallFragment = new RecentCallFragment(); // 최근통화목록탭
    GroupFragment groupFragment = new GroupFragment(); // 그룹탭

    EditText editTxtSearch;

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
        }
    }

    public void callFragment(int fragmentNo) {
        currentFragment = fragmentNo;
        // 프래그먼트 사용을 위해 트랜잭션 객체 선언
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (fragmentNo) {
            case 1:
                transaction.replace(R.id.fragment_contact_container, contactFragment);
                transaction.commit();
                break;

            case 2:
                transaction.replace(R.id.fragment_contact_container, recentCallFragment);
                transaction.commit();
                break;

            case 3:
                transaction.replace(R.id.fragment_contact_container, groupFragment);
                transaction.commit();
                break;
        }
    }

    public void selectedContact(ArrayList<ContactVO> list){
        ArrayList arIndex = new ArrayList();

        for(ContactVO contactVO : list){
            arIndex.add(new Long(contactVO.getId()));
//            Log.i("test", "selected contact : " + contactVO.toString());   // 체크한 contactVO 담겨있음.
        }

        Intent intent = getIntent();  // Sms프래그먼트로 부터 받은 intent
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

    /*public void search(String charText){
        contactFragment.doListClear();
    }*/

}