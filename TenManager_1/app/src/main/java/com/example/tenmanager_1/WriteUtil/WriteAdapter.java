package com.example.tenmanager_1.WriteUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tenmanager_1.Data.SmsGroupVO;
import com.example.tenmanager_1.Data.SmsVO;
import com.example.tenmanager_1.R;


import java.util.ArrayList;
import java.util.HashMap;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmResults;


public class WriteAdapter extends BaseAdapter{
    Context context;
    //ArrayList<WriteSmsVO> datas = new ArrayList<>();
    RealmResults<SmsVO> datas;  // DB에 있는 저장문자 정보
    LayoutInflater layoutInflater;
    private HashMap<Integer, Boolean> mapSelected;

    private View.OnClickListener btnUpClickListener;
    private View.OnClickListener btnDownClickListener;

    private View.OnClickListener holderClickListener;

    public WriteAdapter(RealmResults<SmsVO> datas, Context context) {

        layoutInflater = LayoutInflater.from(context);
        this.context = context;

//        mapSelected = new HashMap<>();
        setDatas(datas);
        initSelectedMap();

        // HashMap 초기화 -> 처음에 다 false를 주고 시작.
        this.mapSelected = mapSelected;
    }

    public void setDatas(RealmResults<SmsVO> datas) {
       this.datas = datas;
       this.datas.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<SmsVO>>() {
           @Override
           public void onChange(RealmResults<SmsVO> smsVOs, OrderedCollectionChangeSet changeSet) {
               notifyDataSetChanged();
           }
       });

        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public SmsVO getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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

        final SmsVO writeSmsVO = getItem(position);

        viewHolder.txtTitle.setText(writeSmsVO.getTitle());
        viewHolder.txtContent.setText(writeSmsVO.getContent());
        viewHolder.smsCheckBox.setTag(position);

        // 여기
/*        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Adapter", String.valueOf(getItemId(position)));
                Intent intent = new Intent(context, AddSmsActivity.class);
*//*                intent.putExtra("flag", 2);
                intent.putExtra("smsObject", writeSmsVO);*//*
                Bundle bundle = new Bundle();
                bundle.putInt("flag", 2);
                // bundle.putSerializable("smsObject", writeSmsVO); // Realm 객체 전달 불가.
                // putExtra 여러개 사용할 때 bundle에 담아서.
                bundle.putString("title", writeSmsVO.getTitle());
                bundle.putString("content", writeSmsVO.getContent());
                bundle.putLong("id", writeSmsVO.getId());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });*/

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

        // 뷰 홀더에 Tag를 달고, write프래그먼에서 Tag를 꺼내쓰기 위해 WriteViewHolder에 getter & setter를 만들었다.
        viewHolder.setTag(position);
        if(holderClickListener != null){
            convertView.setOnClickListener(holderClickListener);
        }

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

/*    private View.OnClickListener clickDetail(final WriteSmsVO wso){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Adapter", "onClick: ============="+wso.getTitle());
            }
        };
    }*/

    public ArrayList<SmsVO> getKey( boolean value){
        ArrayList<SmsVO> list = new ArrayList<>();

/*        for(WriteSmsVO li : map.keySet()){
            if(map.get(li).equals(value)){
                list.add(li);
            }
        }*/

          for(int i=0; i<mapSelected.size(); i++){
            if(mapSelected.get(i).equals(value)){
                list.add(getItem(i));
            }
        }

        return list;
    }

/*    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }*/

    @Override
    public void notifyDataSetChanged() {
//        for(int i=0; i<mapSelected.size(); i++){
//            if(mapSelected.get(i) == null){
//                mapSelected.put(i, false);
//            }
//        }
        super.notifyDataSetChanged();
    }

    public HashMap<Integer, Boolean> getMapSelected() {
        return mapSelected;
    }

    public void setMapSelected(HashMap<Integer, Boolean> mapSelected) {
        this.mapSelected = mapSelected;
    }

    public void initSelectedMap(){
        HashMap<Integer, Boolean> mapSelected = new HashMap<Integer, Boolean>();
        for(int i=0;  i<datas.size(); i++){
            mapSelected.put(i, false);  // sms 저장문자 데이터베이스의 크기만큼 writeSmsVo(키) 를 false(값)로 설정.
        }
        this.mapSelected = mapSelected;
        this.notifyDataSetChanged();
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

    public View.OnClickListener getHolderClickListener() {
        return holderClickListener;
    }

    public void setHolderClickListener(View.OnClickListener holderClickListener) {
        this.holderClickListener = holderClickListener;
    }

}
