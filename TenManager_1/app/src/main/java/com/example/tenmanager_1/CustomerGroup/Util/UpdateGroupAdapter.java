package com.example.tenmanager_1.CustomerGroup.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.tenmanager_1.ContactUtil.StringMatcher;
import com.example.tenmanager_1.Data.ContactGroupVO;
import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.R;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmResults;

/**
 * Created by 전지훈 on 2017-11-23.
 */

public class UpdateGroupAdapter extends BaseAdapter{

    Context context;
    ArrayList<ContactVO> datas;
    LayoutInflater layoutInflater;
    private String mSections = "#ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍㅎ";
    private HashMap<ContactVO, Boolean> mapSelected;

    public UpdateGroupAdapter(Context context, ArrayList<ContactVO> datas, HashMap<ContactVO, Boolean> mapSelected) {
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;

        // HashMap 초기화 -> 처음에 다 false를 주고 시작.
        this.mapSelected = mapSelected;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
//        final Context context = parent.getContext();
        final UpdateGroupHolder viewHolder;

        if (convertView == null) {
            viewHolder = new UpdateGroupHolder();

            convertView = layoutInflater.inflate(R.layout.item_by_group, null);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.txtPhoneNumber = (TextView) convertView.findViewById(R.id.txtPhoneNumber);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            viewHolder.txtGroup = (TextView) convertView.findViewById(R.id.txtGroup);
            //viewHolder.txtGroup = (TextView) convertView.findViewById(R.id.txtGroup);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (UpdateGroupHolder) convertView.getTag();
        }

        ContactVO contactVO = getItem(position);
        //Log.i("test", "contactVO : " + contactVO.toString());

        viewHolder.txtName.setText(contactVO.getName());
        viewHolder.txtPhoneNumber.setText(contactVO.getCellPhone());
        viewHolder.checkBox.setTag(position);
        viewHolder.txtGroup.setText(contactVO.getGroup().getName());

        Boolean isCheck = mapSelected.get(contactVO);  // 처음에는 다 false 겠지
        //Log.i("test", "isCheck :" + isCheck);

        viewHolder.checkBox.setChecked(isCheck);
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int index = (int) buttonView.getTag();
                ContactVO contact = getItem(index);
                //Log.i("test", "change contact :" + contact.toString());
                if (isChecked) {
                    mapSelected.put(contact, true);
                } else {
                    mapSelected.put(contact, false);
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

/*    @Override
    public Object[] getSections() {
        String[] sections = new String[mSections.length()];
        for (int i = 0; i < mSections.length(); i++)
            sections[i] = String.valueOf(mSections.charAt(i));
        return sections;
    }

    @Override
    public int getPositionForSection(int section) {
        // If there is no item for current section, previous section will be selected
        for (int i = section; i >= 0; i--) {
            for (int j = 0; j < getCount(); j++) {
                if (i == 0) {
                    // For numeric section
                    for (int k = 0; k <= 9; k++) {
                        if (StringMatcher.match(String.valueOf(datas.get(j).getName().charAt(0)), String.valueOf(k)))
                            //Log.i("MainActivity", "getItem(j)========" + getItem(j) + ",  " + getItem(j).charAt(0));
                            return j;
                    }
                } else {
                    if (StringMatcher.match(String.valueOf(datas.get(j).getName().charAt(0)), String.valueOf(mSections.charAt(i))))
                        return j;
                }
            }
        }
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        return position;
    }*/
}
