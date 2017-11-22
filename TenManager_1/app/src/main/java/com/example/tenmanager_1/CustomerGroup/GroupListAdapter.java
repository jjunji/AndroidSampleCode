package com.example.tenmanager_1.CustomerGroup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tenmanager_1.Data.ContactGroupVO;
import com.example.tenmanager_1.MyApplication;
import com.example.tenmanager_1.R;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by 전지훈 on 2017-11-22.
 */

public class GroupListAdapter extends BaseAdapter{
    Realm realm;
    RealmResults<ContactGroupVO> datas;
    LayoutInflater layoutInflater;

    private View.OnClickListener btnUpdateListener;
    private View.OnClickListener btnDeleteListener;

    public View.OnClickListener getBtnUpdateListener() {
        return btnUpdateListener;
    }

    public void setBtnUpdateListener(View.OnClickListener btnUpdateListener) {
        this.btnUpdateListener = btnUpdateListener;
    }

    public View.OnClickListener getBtnDeleteListener() {
        return btnDeleteListener;
    }

    public void setBtnDeleteListener(View.OnClickListener btnDeleteListener) {
        this.btnDeleteListener = btnDeleteListener;
    }

    public GroupListAdapter() {
        realm = Realm.getDefaultInstance();
        datas = realm.where(ContactGroupVO.class).findAll().sort("id");
        layoutInflater = LayoutInflater.from(MyApplication.getInstance());
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public ContactGroupVO getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final GroupListViewHolder holder;

        if(convertView == null){
            holder = new GroupListViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_grouplist_setting, null);
            holder.txtGroupName = (TextView) convertView.findViewById(R.id.txtGroupName);
            holder.btnUpdate = (ImageView) convertView.findViewById(R.id.btnUpdate);
            holder.btnDelete = (ImageView) convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);
        }else{
            holder = (GroupListViewHolder) convertView.getTag();
        }

        holder.txtGroupName.setText(datas.get(position).getName());

        if(position == 0 || position == 1){
            holder.btnDelete.setVisibility(View.INVISIBLE);
            holder.btnUpdate.setVisibility(View.INVISIBLE);
        }
        else{
            holder.btnDelete.setVisibility(View.VISIBLE);
            holder.btnUpdate.setVisibility(View.VISIBLE);
        }

        holder.btnUpdate.setTag(position);
        if(btnUpdateListener != null){
            holder.btnUpdate.setOnClickListener(btnUpdateListener);
        }

        holder.btnDelete.setTag(position);
        if(btnDeleteListener != null){
            holder.btnDelete.setOnClickListener(btnDeleteListener);
        }

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
