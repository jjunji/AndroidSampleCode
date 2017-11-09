package com.example.tenmanager_1.Fragment.FindContactFragment;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.tenmanager_1.ContactUtil.ContactAdapter;
import com.example.tenmanager_1.ContactUtil.IndexableListView;
import com.example.tenmanager_1.Data.ContactData;
import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.FindContactActivity;
import com.example.tenmanager_1.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment implements View.OnClickListener {
    Realm realm;
    View view;
    IndexableListView customerListView;
    RealmResults<ContactVO> datas;
    ContactAdapter adapter;
    private HashMap<ContactVO, Boolean> mapSelected;
    private HashMap<ContactVO, Boolean> mapselected2;
    ArrayList<ContactVO> list;

    // TODO: 2017-11-09 프래그먼트 생성자는 항상 비워두는 것이 아닌가? ->
    public ContactFragment() {
        // Required empty public constructor
        realm = Realm.getDefaultInstance();
        datas = realm.where(ContactVO.class).findAll();
        mapSelected = new HashMap<>();
        mapselected2 = new HashMap<>();

        for(ContactVO contactVO : datas){
            mapSelected.put(contactVO, false);  // 연락처(datas) 길이만큼 contactVo(키) 를 false(값)로 설정.
            mapselected2.put(contactVO, false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_child__contact, container, false);

        init();
        setListView();

        return view;
    }

    private void init() {
        customerListView = (IndexableListView) view.findViewById(R.id.customerListView);
        //customerListView2.requestDisallowInterceptTouchEvent(true);
        adapter = new ContactAdapter(getContext(), datas, mapSelected);
        view.findViewById(R.id.btnComfirm).setOnClickListener(this);
    }

    private void setListView(){
        customerListView.setAdapter(adapter);
        customerListView.setFastScrollEnabled(true);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnComfirm){
            //선택 된 리스트 가져오기
            // HashMap 또는 ArrayList
            list = adapter.getKey(mapSelected, true);
            //Log.i("ContactFragment", "Test ==========" + adapter.getKey(mapSelected, true));
            Log.i("ContactFragment", "Test ==========" + list.size());

            FindContactActivity activity = (FindContactActivity) getActivity();
            activity.selectedContact(list);
        }
    }

}

// TODO: 2017-11-08
/*
1.
MainActivity -> 프래그먼트 -> 자식프래그먼트 구조에서
Context를 잘못 참조하는 오류 발생.
(프래그먼트에서도 context는 액티비티것을 가짐.)

2.
뷰홀더가 없을 경우 재생산 하는 부분에서
setTag() 를 안해서 오류 발생. -> setTag, getTag 역할 알기.

3.
디버깅할 때 변수에 오른쪽 마우스 - Evaluate Expression 을 하여
해당 변수가 호출 할 수 있는 메소드나, 가진 데이터를 확인 할 수 있다.
 */
