package com.example.tenmanager_1.Fragment.FindContactFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.R;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.RealmResults;

/**
 * Created by 전지훈 on 2017-11-23.
 */

public class GroupFragmentAdapter extends BaseAdapter{
    checkBoxChangeListener cl;
    Context context;
    RealmResults<ContactVO> datas;
    LayoutInflater layoutInflater;
    private HashMap<ContactVO, Boolean> mapSelected;

    public GroupFragmentAdapter(Context context, RealmResults<ContactVO> datas, HashMap<ContactVO, Boolean> mapSelected, checkBoxChangeListener cl) {
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        // HashMap 초기화 -> 처음에 다 false를 주고 시작.
        this.mapSelected = mapSelected;
        this.cl = cl;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public ContactVO getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final GroupFragmentHolder viewHolder;

        if (convertView == null) {
            viewHolder = new GroupFragmentHolder();

            convertView = layoutInflater.inflate(R.layout.item_groupfragment_findcontact, null);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.txtPhoneNumber = (TextView) convertView.findViewById(R.id.txtPhoneNumber);
            viewHolder.groupCheckBox = (CheckBox) convertView.findViewById(R.id.callCheckBox);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GroupFragmentHolder) convertView.getTag();
        }

        ContactVO contactVO = getItem(position);
        //Log.i("test", "contactVO : " + contactVO.toString());

        viewHolder.txtName.setText(contactVO.getName());
        viewHolder.txtPhoneNumber.setText(contactVO.getCellPhone());
        viewHolder.groupCheckBox.setTag(position);

        Boolean isCheck = mapSelected.get(contactVO);  // 처음에는 다 false 겠지
        //Log.i("test", "isCheck :" + isCheck);

        viewHolder.groupCheckBox.setChecked(isCheck);
        viewHolder.groupCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int index = (int) buttonView.getTag();
                ContactVO contact = getItem(index);
                //Log.i("test", "change contact :" + contact.toString());
                if (isChecked) {
                    mapSelected.put(contact, true);
                    String name = contact.getName();
                    cl.callbackChecked(name);
                } else {
                    mapSelected.put(contact, false);
                    String name = contact.getName();
                    cl.callbackUnChecked(name);
                }
            }
        });
        return convertView;
    }

    public static ArrayList<ContactVO> getKey(HashMap<ContactVO, Boolean> map, boolean value){
        ArrayList<ContactVO> list = new ArrayList<>();

        for(ContactVO li : map.keySet()){
            if(map.get(li).equals(value)){
                list.add(li);
            }
        }
        return list;
    }

}
