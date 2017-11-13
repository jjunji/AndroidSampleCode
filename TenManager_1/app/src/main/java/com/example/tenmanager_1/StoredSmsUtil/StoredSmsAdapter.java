package com.example.tenmanager_1.StoredSmsUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.tenmanager_1.Data.WriteSmsVO;
import com.example.tenmanager_1.R;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by 전지훈 on 2017-11-13.
 */

public class StoredSmsAdapter extends BaseAdapter {
    Realm realm;
    Context context;
    LayoutInflater layoutInflater;
    RealmResults<WriteSmsVO> results;

    public StoredSmsAdapter(Context context) {
        this.context = context;
        realm = Realm.getDefaultInstance();
        layoutInflater = LayoutInflater.from(context);
        results = realm.where(WriteSmsVO.class).findAll().sort("id");
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public WriteSmsVO getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final StoredSmsViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new StoredSmsViewHolder();

            convertView = layoutInflater.inflate(R.layout.item_storedsmslist, null);
            viewHolder.txtItemTitle = (TextView) convertView.findViewById(R.id.txtItemTitle);
            viewHolder.radioBtn = (RadioButton) convertView.findViewById(R.id.radioBtn);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (StoredSmsViewHolder) convertView.getTag();
        }

        viewHolder.txtItemTitle.setText(results.get(position).getTitle());

        return convertView;
    }
}

/*
final CallHistoryViewHolder viewHolder;

        Boolean isCheck = mapSelected.get(callHistoryData);  // 처음에는 다 false 겠지
        //Log.i("test", "isCheck :" + isCheck);

        viewHolder.callCheckBox.setChecked(isCheck);
        viewHolder.callCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int index = (int) buttonView.getTag();
                CallHistoryData data = getItem(index);
                //Log.i("test", "change contact :" + contact.toString());
                if (isChecked) {
                    mapSelected.put(data, true);
                } else {
                    mapSelected.put(data, false);
                }
            }
        });

        return convertView;
    }


 */
