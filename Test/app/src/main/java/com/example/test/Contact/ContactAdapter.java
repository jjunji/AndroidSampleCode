package com.example.test.Contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.test.Data.ContactData;
import com.example.test.R;

import java.util.ArrayList;

/**
 * Created by 김혜정 on 2017-11-06.
 */

public class ContactAdapter extends BaseAdapter implements SectionIndexer {
    private View.OnClickListener callButtonClickListener;
    private View.OnClickListener smsButtonClickListener;
    private View.OnClickListener CallHistoryButtonClickListener;

    private String mSections = "#ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍㅎ";

    ArrayList<ContactData> datas;
    Context context;


    public void setCallButtonClicklistener(View.OnClickListener callButtonClicklistener) {
        this.callButtonClickListener = callButtonClicklistener;
    }

    public void setSmsButtonClicklistener(View.OnClickListener smsButtonClicklistener) {
        this.smsButtonClickListener = smsButtonClicklistener;
    }

    public void setCallHistoryButtonClickListener(View.OnClickListener CallHistoryButtonClickListener){
        this.CallHistoryButtonClickListener = CallHistoryButtonClickListener;
    }


    public ContactAdapter(ArrayList<ContactData> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    /* 리스트 0 부터 getCount(연락처 수) 만큼 돌면서 누른 자음에 해당하는 첫 이름을 검색한다
       ex) ㄴ -> ㄴ으로 시작하는 이름을 발견하면 멈춤.
    */
    @Override
    public int getPositionForSection(int section) {
        // If there is no item for current section, previous section will be selected
        for (int i = section; i >= 0; i--) {
            for (int j = 0; j < getCount(); j++) {
                //Log.i("MainActivity", "section=========" + section);
                //Log.i("MainActivity", "getItem=========" + String.valueOf(datas.get(j).getName().charAt(0)));
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

    @Override
    public Object[] getSections() {
        String[] sections = new String[mSections.length()];
        for (int i = 0; i < mSections.length(); i++)
            sections[i] = String.valueOf(mSections.charAt(i));
        return sections;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public ContactData getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ContactViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new ContactViewHolder();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_contact, null);

            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.txtPhoneNumber = (TextView) convertView.findViewById(R.id.txtPhoneNumber);
            viewHolder.btnCall = (Button) convertView.findViewById(R.id.btnCall);
            viewHolder.btnSns = (Button) convertView.findViewById(R.id.btnSms);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ContactViewHolder) convertView.getTag();
        }

        viewHolder.txtName.setText(datas.get(position).getName());
        viewHolder.txtPhoneNumber.setText(datas.get(position).getTel());

        if(callButtonClickListener != null){
            viewHolder.btnCall.setTag(position);
            viewHolder.btnCall.setOnClickListener(callButtonClickListener);
        }
        if(smsButtonClickListener != null){
            viewHolder.btnSns.setTag(position);
            viewHolder.btnSns.setOnClickListener(smsButtonClickListener);
        }

        return convertView;
    }
}