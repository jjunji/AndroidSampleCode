package com.example.test_3;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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

public class MainActivity extends Activity {
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
        ContentAdapter adapter = new ContentAdapter((ArrayList<ContactData>) datas, getApplicationContext());

        mListView.setAdapter(adapter);
        mListView.setFastScrollEnabled(true);
    }

    private class ContentAdapter extends BaseAdapter implements SectionIndexer {

        private String mSections = "#ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍㅎ";

        ArrayList<ContactData> datas;
        Context context;

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
            return 0;
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
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ContactViewHolder viewHolder;

            if(convertView == null){
                viewHolder = new ContactViewHolder();

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_contact, null);

                viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
                viewHolder.txtPhoneNumber = (TextView) convertView.findViewById(R.id.txtPhoneNumber);
                viewHolder.btnCall = (Button) convertView.findViewById(R.id.btnCall);

                convertView.setTag(viewHolder);
            }
            else{
                viewHolder = (ContactViewHolder) convertView.getTag();
            }

            viewHolder.txtName.setText(datas.get(position).getName());
            viewHolder.txtPhoneNumber.setText(datas.get(position).getTel());


            return convertView;
        }
    }
}