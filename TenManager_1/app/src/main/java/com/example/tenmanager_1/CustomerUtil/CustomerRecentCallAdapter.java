package com.example.tenmanager_1.CustomerUtil;

import android.content.Context;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tenmanager_1.Data.CallHistoryData;
import com.example.tenmanager_1.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by 전지훈 on 2017-11-14.
 */

public class CustomerRecentCallAdapter extends BaseAdapter{
    Context context;
    ArrayList<CallHistoryData> datas;
    LayoutInflater inflater;
    private View.OnClickListener callButtonClickListener;
    private View.OnClickListener smsButtonClickListener;
    private View.OnClickListener addContactHolderClickListener;

    public View.OnClickListener getAddContactHolderClickListener() {
        return addContactHolderClickListener;
    }

    public void setAddContactHolderClickListener(View.OnClickListener addContactHolderClickListener) {
        this.addContactHolderClickListener = addContactHolderClickListener;
    }

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

    public CustomerRecentCallAdapter(Context context, ArrayList<CallHistoryData> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public CallHistoryData getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CustomerRecentCallViewHolder holder;

        if(convertView == null){
            holder = new CustomerRecentCallViewHolder();

            convertView = inflater.inflate(R.layout.item_customer_recentcall, null);
            holder.imgType = (ImageView) convertView.findViewById(R.id.imgType);
            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holder.txtNumber = (TextView) convertView.findViewById(R.id.txtNumber);
            holder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            holder.btnCall = (ImageView) convertView.findViewById(R.id.btnCall);
            holder.btnSend = (ImageView) convertView.findViewById(R.id.btnSend);

            convertView.setTag(holder);
        }else{
            holder = (CustomerRecentCallViewHolder) convertView.getTag();
    }

        //
        CallHistoryData callHistoryData = getItem(position);

        holder.txtName.setText(callHistoryData.getName());
        holder.txtNumber.setText(callHistoryData.getTel());
        holder.txtDate.setText(callHistoryData.getDate());

        holder.setTag(position);
        if(addContactHolderClickListener != null){
            convertView.setOnClickListener(addContactHolderClickListener);
        }

        holder.btnCall.setTag(position);
        if(callButtonClickListener != null){
            holder.btnCall.setOnClickListener(callButtonClickListener);
        }

        holder.btnSend.setTag(position);
        if(smsButtonClickListener != null){
            holder.btnSend.setOnClickListener(smsButtonClickListener);
    }

        return convertView;
    }
}
