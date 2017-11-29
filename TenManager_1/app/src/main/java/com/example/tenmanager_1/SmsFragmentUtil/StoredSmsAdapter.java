package com.example.tenmanager_1.SmsFragmentUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.tenmanager_1.Data.SmsVO;
import com.example.tenmanager_1.R;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by 전지훈 on 2017-11-13.
 */

public class StoredSmsAdapter extends BaseAdapter {
    Realm realm;
    Context context;
    LayoutInflater layoutInflater;
    RealmResults<SmsVO> results;

    private View.OnClickListener holderClickListener;

    public StoredSmsAdapter(Context context) {
        this.context = context;
        realm = Realm.getDefaultInstance();
        layoutInflater = LayoutInflater.from(context);
        //results = realm.where(SmsVO.class).findAll().sort("regdate", Sort.ASCENDING);
        results = realm.where(SmsVO.class).equalTo("group.id",1).findAllSorted("regdate", Sort.ASCENDING);
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public SmsVO getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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

        viewHolder.radioBtn.setTag(position);  // 뷰홀더 안에 라디오버튼의 포지션.
        if(holderClickListener != null){
            //convertView.setOnClickListener(holderClickListener);
            viewHolder.radioBtn.setOnClickListener(holderClickListener);
        }

        return convertView;
    }

  /*  public void defaultChecked(){
        Vie
    }*/

    public View.OnClickListener getHolderClickListener() {
        return holderClickListener;
    }

    public void setHolderClickListener(View.OnClickListener holderClickListener) {
        this.holderClickListener = holderClickListener;
    }
}

