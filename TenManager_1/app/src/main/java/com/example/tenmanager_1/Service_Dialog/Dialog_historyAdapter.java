package com.example.tenmanager_1.Service_Dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tenmanager_1.Data.CallHistoryData;
import com.example.tenmanager_1.R;

import java.util.ArrayList;

/**
 * Created by 전지훈 on 2017-11-27.
 */

public class Dialog_historyAdapter extends BaseAdapter{
    private static final int ITEM_VIEW_TYPE_RECEIVE = 0 ;
    private static final int ITEM_VIEW_TYPE_TRANSMIT= 1 ;
    private static final int ITEM_VIEW_TYPE_MAX = 2 ;

    Context context;
    LayoutInflater inflater;

    //List<CallHistoryData> datas = new ArrayList<>();
    //HashMap<String, ArrayList<CallHistoryData>> datas = new HashMap<>();
    ArrayList<CallHistoryData> datas = new ArrayList<>();

    //private ArrayList<Dialog_historyHolder> listViewItemList = new ArrayList<Dialog_historyHolder>() ;

    public Dialog_historyAdapter(Context context, ArrayList<CallHistoryData> matchingNumberInfo) {
        this.context = context;
        this.datas = matchingNumberInfo;
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

    // TODO: 2017-11-27  리스트뷰에 들어가는 아이템이 두개 이상인 경우
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //final Dialog_historyHolder holder;
        final Dialog_historyHolder_receive receiveHolder;
        final Dialog_historyHolder_transmit transmitHolder;
        final Dialog_historyHolder_callMemo callMemoHolder;

        // 통화내역 타입이 1일 경우(수신)
        if(datas.get(position).getType().equals("수신") || datas.get(position).getType().equals("부재중")){
            if(convertView == null){
                receiveHolder = new Dialog_historyHolder_receive();

                convertView = inflater.inflate(R.layout.item_service_dialog_callreceive, null);
                receiveHolder.txtReceive = (TextView) convertView.findViewById(R.id.textView);
                receiveHolder.txtReceiveTime = (TextView) convertView.findViewById(R.id.txtReceiveTime);
                receiveHolder.imgReceive = (ImageView) convertView.findViewById(R.id.imgReceive);
                convertView.setTag(receiveHolder);
            }
            else{
                if(convertView.getTag() instanceof Dialog_historyHolder_receive){
                    receiveHolder = (Dialog_historyHolder_receive) convertView.getTag();
                } else{
                    receiveHolder = new Dialog_historyHolder_receive();

                    convertView = inflater.inflate(R.layout.item_service_dialog_callreceive, null);
                    receiveHolder.txtReceive = (TextView) convertView.findViewById(R.id.textView);
                    receiveHolder.txtReceiveTime = (TextView) convertView.findViewById(R.id.txtReceiveTime);
                    receiveHolder.imgReceive = (ImageView) convertView.findViewById(R.id.imgReceive);
                    convertView.setTag(receiveHolder);
                }
            }

            receiveHolder.txtReceive.setText("수신");
            receiveHolder.txtReceiveTime.setText(datas.get(position).getDate());

            //return convertView;

           // 통화내역 타입이 2일 경우(발신)
        }else if(datas.get(position).getType().equals("발신")) {
            if (convertView == null) {
                transmitHolder = new Dialog_historyHolder_transmit();

                convertView = inflater.inflate(R.layout.item_service_dialog_calltransmit, null);
                transmitHolder.txtTransmit = (TextView) convertView.findViewById(R.id.txtTransmit);
                transmitHolder.txtTransmitTime = (TextView) convertView.findViewById(R.id.txtTransmitDate);
                transmitHolder.imgTransmit = (ImageView) convertView.findViewById(R.id.imgReceive);
                convertView.setTag(transmitHolder);
            } else {
                if (convertView.getTag() instanceof Dialog_historyHolder_transmit) {
                    transmitHolder = (Dialog_historyHolder_transmit) convertView.getTag();
                } else {
                    transmitHolder = new Dialog_historyHolder_transmit();

                    convertView = inflater.inflate(R.layout.item_service_dialog_calltransmit, null);
                    transmitHolder.txtTransmit = (TextView) convertView.findViewById(R.id.txtTransmit);
                    transmitHolder.txtTransmitTime = (TextView) convertView.findViewById(R.id.txtTransmitDate);
                    transmitHolder.imgTransmit = (ImageView) convertView.findViewById(R.id.imgReceive);
                    convertView.setTag(transmitHolder);
                }
            }

            transmitHolder.txtTransmit.setText("발신");
            transmitHolder.txtTransmitTime.setText(datas.get(position).getDate());
        }


        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_VIEW_TYPE_MAX;
    }

 /*   @Override
    public int getItemViewType(int position) {
        return datas.get(position).
    }*/

}
