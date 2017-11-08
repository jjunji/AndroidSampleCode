package com.example.tenmanager_1.ContactUtil;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tenmanager_1.Data.ContactData;
import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.R;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by 전지훈 on 2017-11-08.
 */

public class ContactAdapter extends BaseAdapter{

    //Context context;
    //ArrayList<ContactData> datas = new ArrayList<>();
    RealmResults<ContactVO> datas;
//    Context context;
    LayoutInflater layoutInflater;

    public ContactAdapter(Context context, RealmResults<ContactVO> datas) {
        this.datas = datas;
//        this.context = context;
//        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater = LayoutInflater.from(context);
    }

 /*   public ContactAdapter(RealmResults<ContactVO> datas, Context context) {
        this.datas = datas;
        //this.context = context;
    }*/

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
//        final Context context = parent.getContext();
        final ContactViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new ContactViewHolder();

            convertView = layoutInflater.inflate(R.layout.item_contact, null);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.txtPhoneNumber = (TextView) convertView.findViewById(R.id.txtPhoneNumber);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ContactViewHolder) convertView.getTag();
        }

        ContactVO contactVO = getItem(position);
        Log.i("test", "contactVO : " + contactVO.toString());

        viewHolder.txtName.setText(contactVO.getName());
        viewHolder.txtPhoneNumber.setText(contactVO.getTel());

        return convertView;
    }
}
