package com.example.tenmanager_1.Fragment.ContactFragment_Child;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tenmanager_1.ContactUtil.ContactAdapter;
import com.example.tenmanager_1.ContactUtil.IndexableListView;
import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.R;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class Child_ContactFragment extends Fragment {
    Realm realm = Realm.getDefaultInstance();
    //Context context;
    View view;
    //ListView customerListView;
    IndexableListView customerListView2;
    //ArrayList<ContactData> datas;
    //RealmResults<ContactVO> datas;
    RealmResults<ContactVO> datas;  // = realm.where(ContactVO.class).findAll();
//    //ContactAdapter adapter = new ContactAdapter(datas, getActivity());
    ContactAdapter adapter;

    public Child_ContactFragment() {
        // Required empty public constructor
    }

/*    public static Child_ContactFragment newInstance(Context mContext) {
        Bundle args = new Bundle();
        Child_ContactFragment fragment = new Child_ContactFragment();
        fragment.context = mContext;
        fragment.setArguments(args);
        return fragment;
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_child__contact, container, false);

        init();
        setListView();

        return view;
    }

    private void init() {
        customerListView2 = (IndexableListView) view.findViewById(R.id.customerListView);
        datas = realm.where(ContactVO.class).findAll();
        adapter = new ContactAdapter(getContext(), datas);
        //ContactAdapter adapter = new ContactAdapter(datas, getActivity());
    }

    private void setListView(){
        customerListView2.setAdapter(adapter);
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
