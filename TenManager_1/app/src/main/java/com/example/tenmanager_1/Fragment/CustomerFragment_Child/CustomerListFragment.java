package com.example.tenmanager_1.Fragment.CustomerFragment_Child;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.telecom.Call;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tenmanager_1.AddContactActivity;
import com.example.tenmanager_1.CustomerUtil.CustomerAdapter;
import com.example.tenmanager_1.CustomerUtil.CustomerViewHolder;
import com.example.tenmanager_1.CustomerUtil.IndexableCustomerListView;
import com.example.tenmanager_1.Data.CallHistoryVO;
import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.R;

import java.io.Console;
import java.io.Serializable;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerListFragment extends Fragment implements View.OnClickListener{
    private final String TAG = CustomerListFragment.class.getSimpleName();
    Realm realm;
    View view;
    //ListView customerListView;
    IndexableCustomerListView indexableCustomerListView;
    CustomerAdapter adapter;
    TextView txtSearch;

    //ArrayList<ContactVO> checkedContactResult;
    ArrayList<ContactVO> datas;
    RealmResults<ContactVO> datas2;

    FloatingActionButton fab;

    private final int REQUESTCODE_STORE = 1;
    private final int REQESTCODE_UPDATE = 2;

    public CustomerListFragment() {
        realm = Realm.getDefaultInstance();

        datas2 = realm.where(ContactVO.class).findAllSorted("name", Sort.ASCENDING);

        datas = new ArrayList<>();

        for(ContactVO contactVO : datas2){
            datas.add(contactVO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_customer_contact, container, false);
        init();
        setFabClickListener();
        setListView();
        return view;
    }

    private void init() {
        fab = (FloatingActionButton) view.findViewById(R.id.fab_addContact);
        //customerListView = (ListView) view.findViewById(R.id.c_customerListView);
        indexableCustomerListView = (IndexableCustomerListView) view.findViewById(R.id.c_customerListView);
        adapter = new CustomerAdapter(getContext(), datas);
        txtSearch = (TextView) view.findViewById(R.id.txtSearch);
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                search(txtSearch.getText().toString());
            }
        });
        //checkedContactResult = new ArrayList<>();
        //doSearch();

        adapter.setCallButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                ContactVO contactData = adapter.getItem(position);
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:"+ contactData.getCellPhone())));
            }
        });

        adapter.setSmsButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                ContactVO contactData = adapter.getItem(position);
                startActivity(new Intent("android.intent.action.SENDTO", Uri.parse("sms:"+contactData.getCellPhone())));
            }
        });

        adapter.setHolderClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerViewHolder holder = (CustomerViewHolder) v.getTag();
                int position = holder.getTag();
                ContactVO contactVO = adapter.getItem(position);
                //Log.i("Adapter", String.valueOf(adapter.getItemId()));
                Intent intent = new Intent(getContext(), AddContactActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("flag", 2);
                // putExtra 여러개 사용할 때 bundle에 담아서 보냄.
                bundle.putLong("id", contactVO.getId());
                bundle.putString("name", contactVO.getName());
                bundle.putString("call1", contactVO.getTel1());
                bundle.putString("call2", contactVO.getTel2());
                bundle.putString("phoneNumber", contactVO.getCellPhone());
                bundle.putString("address", contactVO.getAddress());
                bundle.putString("memo", contactVO.getMemo());

                // result : 통화내역VO 에서 방금 통화한 번호와 같고, 타입이 3(콜메모)인 데이터
                RealmResults<CallHistoryVO> results = realm.where(CallHistoryVO.class).equalTo("contactVO.cellPhone", contactVO.getCellPhone()).equalTo("type",3).findAll();

                StringBuilder totalMemo = new StringBuilder();

                if(results != null){
                    for(int i=0; i<results.size(); i++){
                        totalMemo.append(results.get(i).getCallMemo()+"\n");
                    }
                }else{
                    totalMemo.append("");
                }

                bundle.putString("callMemo", totalMemo.toString());

                intent.putExtras(bundle);
                startActivityForResult(intent, REQESTCODE_UPDATE);
            }
        });
    }

    private void setListView() {
        indexableCustomerListView.setAdapter(adapter);
        indexableCustomerListView.setFastScrollEnabled(true);
    }


    public void search(String charText) {
        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        //list.clear();
        datas.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            for(ContactVO contactVO : datas2){
                datas.add(contactVO);
            }
        }
        // 문자 입력을 할때..
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < datas2.size(); i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (datas2.get(i).getName().toLowerCase().contains(charText) || datas2.get(i).getCellPhone().toLowerCase().contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    datas.add(datas2.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == REQUESTCODE_STORE) {
                datas2 = realm.where(ContactVO.class).findAllSorted("name", Sort.ASCENDING);

                datas = new ArrayList<>();

                for(ContactVO contactVO : datas2){
                    datas.add(contactVO);
                }
                adapter.setDatas(datas);
                adapter.notifyDataSetChanged();
            }else if (requestCode == REQESTCODE_UPDATE){
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_addContact :
                Intent intent = new Intent(getContext(), AddContactActivity.class);
                intent.putExtra("flag",1);
                startActivityForResult(intent, REQUESTCODE_STORE);
                break;
        }
    }

    public void setFabClickListener(){
        fab.setOnClickListener(this);
    }
}
