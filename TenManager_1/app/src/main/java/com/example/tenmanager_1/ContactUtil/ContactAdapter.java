package com.example.tenmanager_1.ContactUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 전지훈 on 2017-11-08.
 */

public class ContactAdapter extends BaseAdapter implements SectionIndexer {

    Context context;
    ArrayList<ContactVO> datas;
    LayoutInflater layoutInflater;
    private String mSections = "#ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍㅎ";
    private HashMap<ContactVO, Boolean> mapSelected;

    private CheckBox.OnCheckedChangeListener contactCheckListener;

    public CheckBox.OnCheckedChangeListener getContactCheckListener() {
        return contactCheckListener;
    }

    public void setContactCheckListener(CheckBox.OnCheckedChangeListener contactCheckListener) {
        this.contactCheckListener = contactCheckListener;
    }

    public ContactAdapter(Context context, ArrayList<ContactVO> datas, HashMap<ContactVO, Boolean> mapSelected) {
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
        final ContactViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ContactViewHolder();

            convertView = layoutInflater.inflate(R.layout.item_contact, null);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.txtPhoneNumber = (TextView) convertView.findViewById(R.id.txtPhoneNumber);
            viewHolder.contactCheckBox = (CheckBox) convertView.findViewById(R.id.contactCheckBox);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ContactViewHolder) convertView.getTag();
        }

        ContactVO contactVO = getItem(position);
        //Log.i("test", "contactVO : " + contactVO.toString());

        viewHolder.txtName.setText(contactVO.getName());
        viewHolder.txtPhoneNumber.setText(contactVO.getCellPhone());
        viewHolder.contactCheckBox.setTag(position);

        if(contactCheckListener != null){
            viewHolder.contactCheckBox.setOnCheckedChangeListener(contactCheckListener);
        }

        Boolean isCheck = mapSelected.get(contactVO);  // 처음에는 다 false 겠지
        //Log.i("test", "isCheck :" + isCheck);

        // TODO: 2017-11-27 asdf 
        viewHolder.contactCheckBox.setChecked(isCheck);
        viewHolder.contactCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

    @Override
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
    }
}