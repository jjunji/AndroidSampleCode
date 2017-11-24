package com.example.tenmanager_1.Fragment.CustomerFragment_Child;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.tenmanager_1.ContactUtil.StringMatcher;
import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.R;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by 전지훈 on 2017-11-24.
 */

public class ItemizedListAdapter extends BaseAdapter implements SectionIndexer {
    private View.OnClickListener callButtonClickListener;
    private View.OnClickListener smsButtonClickListener;

    public View.OnClickListener getCallButtonClickListener() {
        return callButtonClickListener;
    }

    public void setCallButtonClickListener(View.OnClickListener callButtonClickListener) {
        this.callButtonClickListener = callButtonClickListener;
    }

    public View.OnClickListener getSmsButtonClickListener() {
        return smsButtonClickListener;
    }

    public void setSmsButtonClickListener(View.OnClickListener smsButtonClickListener) {
        this.smsButtonClickListener = smsButtonClickListener;
    }

    Context context;
    RealmResults<ContactVO> datas;
    LayoutInflater inflater;
    private String mSections = "#ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍㅎ";

    public ItemizedListAdapter(Context context, RealmResults<ContactVO> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
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
        final ItemizedListHolder holder;

        if(convertView == null){
            holder = new ItemizedListHolder();
            convertView = inflater.inflate(R.layout.item_customer_group_itemized, null);

            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holder.txtPhoneNumber = (TextView) convertView.findViewById(R.id.txtPhoneNumber);
            holder.btnCall = (ImageView) convertView.findViewById(R.id.btnCall);
            holder.btnSend = (ImageView) convertView.findViewById(R.id.btnSend);
            convertView.setTag(holder);
        }else{
            holder = (ItemizedListHolder) convertView.getTag();
        }

        holder.txtName.setText(datas.get(position).getName());
        holder.txtPhoneNumber.setText(datas.get(position).getCellPhone());

        if (callButtonClickListener != null) {
            holder.btnCall.setTag(position);
            holder.btnCall.setOnClickListener(callButtonClickListener);
        }
        if (smsButtonClickListener != null) {
            holder.btnSend.setTag(position);
            holder.btnSend.setOnClickListener(smsButtonClickListener);
        }

        return convertView;
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
