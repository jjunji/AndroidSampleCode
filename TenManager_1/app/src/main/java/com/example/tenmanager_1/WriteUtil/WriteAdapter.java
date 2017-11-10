package com.example.tenmanager_1.WriteUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tenmanager_1.Data.CallHistoryData;
import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.Data.WriteSmsVO;
import com.example.tenmanager_1.R;
import com.example.tenmanager_1.RecentCallUtil.CallHistoryViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.RealmResults;


public class WriteAdapter extends BaseAdapter{

    Context context;
    //ArrayList<WriteSmsVO> datas = new ArrayList<>();
    RealmResults<WriteSmsVO> datas;  // DB에 있는 저장문자 정보
    LayoutInflater layoutInflater;
    private HashMap<Integer, Boolean> mapSelected;

    private View.OnClickListener btnUpClickListener;
    private View.OnClickListener btnDownClickListener;

    public WriteAdapter(RealmResults<WriteSmsVO> datas, Context context, HashMap<Integer, Boolean> mapSelected) {
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;

        // HashMap 초기화 -> 처음에 다 false를 주고 시작.
        this.mapSelected = mapSelected;
    }

    public void setDatas(RealmResults<WriteSmsVO> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public WriteSmsVO getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final WriteViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new WriteViewHolder();

            convertView = layoutInflater.inflate(R.layout.item_storedsms, null);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            viewHolder.txtContent = (TextView) convertView.findViewById(R.id.txtContent);
            viewHolder.btnUp = (ImageView) convertView.findViewById(R.id.btnUp);
            viewHolder.btnDown = (ImageView) convertView.findViewById(R.id.btnDown);
            viewHolder.smsCheckBox = (CheckBox) convertView.findViewById(R.id.smsCheckBox);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (WriteViewHolder) convertView.getTag();
        }

        WriteSmsVO writeSmsVO = getItem(position);

        viewHolder.txtTitle.setText(writeSmsVO.getTitle());
        viewHolder.txtContent.setText(writeSmsVO.getContent());
        viewHolder.smsCheckBox.setTag(position);
        // 업,다운 버튼

        Boolean isCheck = mapSelected.get(position);  // 처음에는 다 false 겠지
        if(isCheck != null){
            viewHolder.smsCheckBox.setChecked(isCheck);
        }
        else{
            viewHolder.smsCheckBox.setChecked(false);
        }
        viewHolder.smsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int index = (int) buttonView.getTag();
                //WriteSmsVO writeSmsVO = getItem(index);
                if (isChecked) {
                    mapSelected.put(index, true);
                } else {
                    mapSelected.put(index, false);
                }
            }
        });

        viewHolder.btnUp.setTag(position);
        if(btnUpClickListener != null){
            viewHolder.btnUp.setOnClickListener(btnUpClickListener);
        }

        viewHolder.btnDown.setTag(position);
        if(btnDownClickListener!= null){
            viewHolder.btnDown.setOnClickListener(btnDownClickListener);
        }

        if(position == 0){
            viewHolder.btnUp.setVisibility(View.INVISIBLE);
        }
        else{
            viewHolder.btnUp.setVisibility(View.VISIBLE);
        }

        if(position == datas.size()-1){
            viewHolder.btnDown.setVisibility(View.INVISIBLE);
        }
        else{
            viewHolder.btnDown.setVisibility(View.VISIBLE);
        }


        return convertView;
    }

    public static ArrayList<WriteSmsVO> getKey(HashMap<WriteSmsVO, Boolean> map, boolean value){
        ArrayList<WriteSmsVO> list = new ArrayList<>();

        for(WriteSmsVO li : map.keySet()){
            if(map.get(li).equals(value)){
                list.add(li);
            }
        }

     /*   for(int i=0; i<map.size(); i++){
            if(map.get(i).equals(value)){

            }
        }*/

        return list;
    }

/*    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }*/

    public HashMap<Integer, Boolean> getMapSelected() {
        return mapSelected;
    }

    public void setMapSelected(HashMap<Integer, Boolean> mapSelected) {
        this.mapSelected = mapSelected;
    }

    public View.OnClickListener getBtnUpClickListener() {
        return btnUpClickListener;
    }

    public void setBtnUpClickListener(View.OnClickListener btnUpClickListener) {
        this.btnUpClickListener = btnUpClickListener;
    }

    public View.OnClickListener getBtnDownClickListener() {
        return btnDownClickListener;
    }

    public void setBtnDownClickListener(View.OnClickListener btnDownClickListener) {
        this.btnDownClickListener = btnDownClickListener;
    }
}
