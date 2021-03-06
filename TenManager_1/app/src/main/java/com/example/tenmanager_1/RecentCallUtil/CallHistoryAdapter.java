package com.example.tenmanager_1.RecentCallUtil;

import android.content.Context;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.tenmanager_1.Data.CallHistoryData;
import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.Fragment.FindContactFragment.checkBoxChangeListener;
import com.example.tenmanager_1.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CallHistoryAdapter extends BaseAdapter{
    checkBoxChangeListener cl;
    List<CallHistoryData> datas = new ArrayList<>();
    Context context;
    private HashMap<CallHistoryData, Boolean> mapSelected;
    LayoutInflater layoutInflater;

    public CallHistoryAdapter(List<CallHistoryData> datas, Context context, HashMap<CallHistoryData, Boolean> mapSelected, checkBoxChangeListener cl) {
        layoutInflater = LayoutInflater.from(context);
        this.datas = datas;
        this.context = context;
        this.mapSelected = mapSelected;
        this.cl = cl;
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

            convertView = layoutInflater.inflate(R.layout.item_recentcall, null);
            viewHolder.txtPhoneNumber = (TextView) convertView.findViewById(R.id.txtPhoneNumber);
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            viewHolder.callCheckBox = (CheckBox) convertView.findViewById(R.id.callCheckBox);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (CallHistoryViewHolder) convertView.getTag();
        }

        CallHistoryData callHistoryData = getItem(position);

        viewHolder.txtPhoneNumber.setText(callHistoryData.getTel());
        viewHolder.txtDate.setText(callHistoryData.getDate());
        viewHolder.callCheckBox.setTag(position);

        Boolean isCheck = mapSelected.get(callHistoryData);  // 처음에는 다 false 겠지
        //Log.i("test", "isCheck :" + isCheck);

        viewHolder.callCheckBox.setChecked(isCheck);
        viewHolder.callCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int index = (int) buttonView.getTag();
                CallHistoryData data = getItem(index);
                if (isChecked) {
                    mapSelected.put(data, true);
                    String name = data.getName();
                    cl.callbackChecked(name);
                } else {
                    mapSelected.put(data, false);
                    String name = data.getName();
                    cl.callbackUnChecked(name);
                }
            }
        });

        return convertView;
    }

    public static ArrayList<CallHistoryData> getKey(HashMap<CallHistoryData, Boolean> map, boolean value){
        ArrayList<CallHistoryData> list = new ArrayList<>();

        for(CallHistoryData li : map.keySet()){
            if(map.get(li).equals(value)){
                list.add(li);
            }
        }
        return list;
    }
}