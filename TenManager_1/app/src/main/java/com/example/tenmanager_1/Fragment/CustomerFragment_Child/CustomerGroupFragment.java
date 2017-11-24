package com.example.tenmanager_1.Fragment.CustomerFragment_Child;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tenmanager_1.CustomerGroup.AddCustomerGroupDialog;
import com.example.tenmanager_1.Data.ContactGroupVO;
import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.Fragment.CustomerFragment;
import com.example.tenmanager_1.GroupView;
import com.example.tenmanager_1.R;
import com.example.tenmanager_1.repositories.ContactDataSource;
import com.example.tenmanager_1.repositories.impl.ContactRepository;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerGroupFragment extends Fragment {
    private final String TAG = CustomerGroupFragment.class.getSimpleName();
    View view;
    Realm realm;
    FloatingActionButton btnAddGroup;
    AddCustomerGroupDialog dialog;
    LinearLayout llCategory;
    ArrayList<GroupView> arCategory;
    ContactDataSource contactDataSource;
    ListView itemizedListView;
    ItemizedListAdapter adapter;

    public CustomerGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_customer_group, container, false);

        contactDataSource = new ContactRepository();
        arCategory = new ArrayList<>();
        initView();
        setAddButton();
        createMenu(contactDataSource.getContactGroupList());
        reloadGroupList(0);

        return view;
    }

    private void setAdapterButtonListener(){
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
    }

    @Override
    public void onResume() {
        super.onResume();
        llCategory.removeAllViews();
        arCategory.clear();
        createMenu(contactDataSource.getContactGroupList());
    }

    private void initView() {
        realm = Realm.getDefaultInstance();
        btnAddGroup = (FloatingActionButton) view.findViewById(R.id.btnAddGroup);
        llCategory = (LinearLayout) view.findViewById(R.id.llCategory);
        itemizedListView = (ListView) view.findViewById(R.id.itemizedListView);
    }

    private void setAddButton(){
        btnAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new AddCustomerGroupDialog(getActivity());
                dialog.show();
            }
        });
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

    public void updateMenu(final int position) {

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
        Log.i(TAG, "arContact =========== " + arContact);
        adapter = new ItemizedListAdapter(getContext(), arContact);
        itemizedListView.setAdapter(adapter);
        setAdapterButtonListener();
    }
}



/*
    private void setTextView(){
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int size = Math.round(20 * dm.density);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_VERTICAL);
        layoutParams.rightMargin = size;

        TextView tv = new TextView(getContext());
        tv.setLayoutParams(layoutParams);
        tv.setText("new Text");
        //tv.setGravity(Gravity.CENTER);
        tv.setGravity(Gravity.CENTER_VERTICAL);

        layout.addView(tv);

    }
*/
