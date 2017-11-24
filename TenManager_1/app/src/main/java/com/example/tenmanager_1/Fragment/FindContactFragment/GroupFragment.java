package com.example.tenmanager_1.Fragment.FindContactFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.tenmanager_1.Data.ContactGroupVO;
import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.GroupView;
import com.example.tenmanager_1.R;
import com.example.tenmanager_1.repositories.service.ContactDataSource;
import com.example.tenmanager_1.repositories.impl.ContactRepository;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment {
    Realm realm;
    ArrayList<ContactVO> datas;
    RealmResults<ContactVO> results;
    View view;
    LinearLayout llCategory;
    ArrayList<GroupView> arCategory;
    ContactDataSource contactDataSource;
    GroupFragmentAdapter adapter;
    ListView groupListView;
    private HashMap<ContactVO, Boolean> mapSelected;

    public GroupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_group, container, false);

        initView();
        init();

        createMenu(contactDataSource.getContactGroupList());

        return view;
    }

    private void createMenu(RealmResults<ContactGroupVO> arGroup) {
        for (int i = 0; i < arGroup.size(); i++) {
            GroupView headerMenu = new GroupView(getContext(), llCategory, arGroup.get(i).getName(), i);
            headerMenu.setOnClickListner(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GroupView headerMenu = (GroupView) v.getTag();
                    updateMenu(headerMenu.getIndex());
                }
            });
            if (i == 0) {
                headerMenu.setSelected(true);
            } else {
                headerMenu.setSelected(false);
            }

            llCategory.addView(headerMenu.getView());
            arCategory.add(headerMenu);
        }
    }

    private void updateMenu(final int position) {
        for (int i = 0; i < arCategory.size(); i++) {
            GroupView headerMenu = arCategory.get(i);
            boolean isSeleted = (i == position);
            headerMenu.setSelected(isSeleted);
        }
        reloadGroupList(position);
    }

    private void reloadGroupList(int position) {
        RealmResults<ContactGroupVO> arGroup = contactDataSource.getContactGroupList();
        ContactGroupVO contactGroupVO = arGroup.get(position);
        Log.i("test", "selected group :"+contactGroupVO.toString());

        matchingContactByGroup(contactGroupVO);

    }

    private void matchingContactByGroup(ContactGroupVO cgvo){
        RealmResults<ContactVO> arContact = realm.where(ContactVO.class).equalTo("group.id", cgvo.getId()).findAll();
        adapter = new GroupFragmentAdapter(getContext(), arContact, mapSelected);
        groupListView.setAdapter(adapter);
    }

    private void initView() {
        groupListView = (ListView) view.findViewById(R.id.groupListView);
        llCategory = (LinearLayout) view.findViewById(R.id.llCategory);
    }

    private void init(){
        datas = new ArrayList<>();
        realm = Realm.getDefaultInstance();
        results = realm.where(ContactVO.class).findAll();
        mapSelected = new HashMap<>();
        contactDataSource = new ContactRepository();
        arCategory = new ArrayList<>();

        for(ContactVO contactVO : results){
            datas.add(contactVO);
        }

        for(ContactVO contactVO : datas){
            mapSelected.put(contactVO, false);
        }

        reloadGroupList(0);

        //adapter = new GroupFragmentAdapter(getActivity(), datas, mapSelected);
    }

    private void setListView(){
        groupListView.setAdapter(adapter);
    }

}
