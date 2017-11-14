package com.example.tenmanager_1.CustomerUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.tenmanager_1.ContactUtil.StringMatcher;
import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.FindContactActivity;
import com.example.tenmanager_1.R;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by 전지훈 on 2017-11-14.
 */

public class CustomerAdapter extends BaseAdapter implements SectionIndexer {
    Context context;
    Realm realm;
    RealmResults<ContactVO> results;
    LayoutInflater inflater;
    private String mSections = "#ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍㅎ";

    public CustomerAdapter(Context context) {
        this.context = context;
        realm = Realm.getDefaultInstance();
        results = realm.where(ContactVO.class).findAll().sort("id");
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public ContactVO getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CustomerViewHolder holder;

        if(convertView == null){
            holder = new CustomerViewHolder();

            convertView = inflater.inflate(R.layout.item_customerlist,null);
            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holder.txtPhoneNumber = (TextView) convertView.findViewById(R.id.txtPhoneNumber);
            //holder.btnCall = (Button) convertView.findViewById(R.id.btnCall);
            convertView.setTag(holder);
        }else{
            holder = (CustomerViewHolder) convertView.getTag();
        }

        holder.txtName.setText(results.get(position).getName());
        holder.txtPhoneNumber.setText(results.get(position).getTel());

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
                        if (StringMatcher.match(String.valueOf(results.get(j).getName().charAt(0)), String.valueOf(k)))
                            //Log.i("MainActivity", "getItem(j)========" + getItem(j) + ",  " + getItem(j).charAt(0));
                            return j;
                    }
                } else {
                    if (StringMatcher.match(String.valueOf(results.get(j).getName().charAt(0)), String.valueOf(mSections.charAt(i))))
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
