package com.example.test_3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.test_3.Data.ContactData;
import com.example.test_3.Loader.ContactLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.R.attr.onClick;

public class MainActivity extends Activity{
    private IndexableListView mListView;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContactLoader CLoader = new ContactLoader(this);
        // datas : 연락처 데이터
        List<ContactData> datas = CLoader.getContacts();

        mListView = (IndexableListView) findViewById(R.id.listview);
        final ContentAdapter adapter = new ContentAdapter((ArrayList<ContactData>) datas, getApplicationContext());
        adapter.setCallButtonClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                ContactData contactData = adapter.getItem(position);
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:"+ contactData.getTel())));
            }
        });
        adapter.setSmsButtonClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                ContactData contactData = adapter.getItem(position);
                startActivity(new Intent("android.intent.action.SENDTO", Uri.parse("sms:"+contactData.getTel())));
            }
        });
//        adapter.setSmsButtonClicklistener(new ContentAdapter.ContractButtonListener() {
//            @Override
//            public void onClickSMS(ContactData contactData) {
//
//            }
//        });

        mListView.setAdapter(adapter);
        mListView.setFastScrollEnabled(true);
    }

    private class ContentAdapter extends BaseAdapter implements SectionIndexer{
        private View.OnClickListener callButtonClicklistener;
        private View.OnClickListener smsButtonClicklistener;

//        private ContractButtonListener smsButtonClicklistener;
        private String mSections = "#ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍㅎ";

        ArrayList<ContactData> datas;
        Context context;


//        interface ContractButtonListener {
//            void onClickSMS(ContactData contactData);
//        }

        public void setCallButtonClicklistener(View.OnClickListener callButtonClicklistener) {
            this.callButtonClicklistener = callButtonClicklistener;
        }

        public void setSmsButtonClicklistener(View.OnClickListener smsButtonClicklistener) {
            this.smsButtonClicklistener = smsButtonClicklistener;
        }


//        public void setSmsButtonClicklistener(ContractButtonListener smsButtonClicklistener) {
//            this.smsButtonClicklistener = smsButtonClicklistener;
//        }

        public ContentAdapter(ArrayList<ContactData> datas, Context context) {
            this.datas = datas;
            this.context = context;
        }

        @Override
        public int getPositionForSection(int section) {
            // If there is no item for current section, previous section will be selected
            for (int i = section; i >= 0; i--) {
                for (int j = 0; j < getCount(); j++) {
                    Log.i("MainActivity", "getCount=========" + getCount());
                    Log.i("MainActivity", "getItem=========" + String.valueOf(datas.get(j).getName().charAt(0)));
                    if (i == 0) {
                        // For numeric section
                        for (int k = 0; k <= 9; k++) {
                            if (StringMatcher.match(String.valueOf(datas.get(j).getName().charAt(0)), String.valueOf(k)))
                                //Log.i("MainActivity", "getItem(j)========" + getItem(j) + ",  " + getItem(j).charAt(0));
                                return j;
                        }
                    } else {
                        if (StringMatcher.match(String.valueOf(datas.get(j).getName().charAt(0)), String.valueOf(mSections.charAt(i))))
                            return j;
                    }
                }
            }
            return 0;
        }

        @Override
        public int getSectionForPosition(int position) {
            return position;
        }

        @Override
        public Object[] getSections() {
            String[] sections = new String[mSections.length()];
            for (int i = 0; i < mSections.length(); i++)
                sections[i] = String.valueOf(mSections.charAt(i));
            return sections;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public ContactData getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final ContactViewHolder viewHolder;

            if(convertView == null){
                viewHolder = new ContactViewHolder();

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_contact, null);

                viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
                viewHolder.txtPhoneNumber = (TextView) convertView.findViewById(R.id.txtPhoneNumber);
                viewHolder.btnCall = (Button) convertView.findViewById(R.id.btnCall);
                viewHolder.btnSns = (Button) convertView.findViewById(R.id.btnSms);

                convertView.setTag(viewHolder);
            }
            else{
                viewHolder = (ContactViewHolder) convertView.getTag();
            }

            viewHolder.txtName.setText(datas.get(position).getName());
            viewHolder.txtPhoneNumber.setText(datas.get(position).getTel());

            if(callButtonClicklistener != null){
                viewHolder.btnCall.setTag(position);
                viewHolder.btnCall.setOnClickListener(callButtonClicklistener);
            }
            if(smsButtonClicklistener != null){
                viewHolder.btnSns.setTag(position);
                viewHolder.btnSns.setOnClickListener(smsButtonClicklistener);
            }
            //viewHolder.btnCall.setOnClickListener(listener);
//            viewHolder.btnSns.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(smsButtonClicklistener != null){
//                        smsButtonClicklistener.onClickSMS(getItem(position));
//                    }
////                    startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:"+ datas.get(position).getTel())));
//                }
//            });

            return convertView;
        }

/*        View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btnCall :
                        startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:"+datas.get(position).getTel())));
                        break;
                }
            }
        };*/
    }
}