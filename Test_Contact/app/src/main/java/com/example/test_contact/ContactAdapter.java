package com.example.test_contact;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.test_contact.Data.ContactData;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by 김혜정 on 2017-11-01.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.Holder> implements SectionIndexer {

    List<ContactData> datas;

    public ContactAdapter(List<ContactData> datas) {
        this.datas = datas;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        final ContactData data = datas.get(position);

        // 2. 데이터를 세팅
        holder.setTextName(data.getName());
        holder.setTextPhoneNumber(data.getTel());

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public Object[] getSections() {
        String[] chars = new String[SideSelector.HANGUL.length];
        for (int i = 0; i < SideSelector.HANGUL.length; i++) {
            chars[i] = String.valueOf(SideSelector.HANGUL[i]);
            //Log.i("ContactAdapter", "chars[i]=======" + chars[i]);
        }

        return chars;
    }

    @Override
    public int getPositionForSection(int i) {
        Log.d(TAG, "getPositionForSection " + i);
        //return (int) (getCount() * ((float)i/(float)getSections().length));
        Log.i("ContactAdapter", "getSections.length=======" + getSections().length);
        return (int) (getItemCount() * ((float)i/(float)getSections().length));
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textPhoneNumber;

        /* Holder 생성자 */
        public Holder(View v) {
            super(v);

            textName = (TextView) itemView.findViewById(R.id.txtName);
            textPhoneNumber = (TextView) itemView.findViewById(R.id.txtPhoneNumber);
        }


        public void setTextName(String name) {
            textName.setText(name);
        }

        public void setTextPhoneNumber(String phoneNumber){
            textPhoneNumber.setText(phoneNumber);
        }
    }

}
