package com.example.test.CallHistory;

import android.content.Context;
import android.support.v4.view.LayoutInflaterFactory;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.Data.CallHistoryData;
import com.example.test.R;

import java.util.ArrayList;

/**
 * Created by 김혜정 on 2017-11-06.
 */

public class CallHistoryAdapter extends BaseAdapter{

    ArrayList<CallHistoryData> datas = new ArrayList<>();
    Context context;

    public CallHistoryAdapter(ArrayList<CallHistoryData> datas, Context context) {
        this.datas = datas;
        this.context = context;
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
        final CallHistoryViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new CallHistoryViewHolder();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_history, null);

            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.txtPhoneNumber = (TextView) convertView.findViewById(R.id.txtPhoneNumber);
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.txtType);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (CallHistoryViewHolder) convertView.getTag();
        }
        viewHolder.txtName.setText(datas.get(position).getName());
        viewHolder.txtPhoneNumber.setText(datas.get(position).getTel());
        viewHolder.txtDate.setText(datas.get(position).getDate());
        viewHolder.txtType.setText(datas.get(position).getType());

        return convertView;
    }
}
