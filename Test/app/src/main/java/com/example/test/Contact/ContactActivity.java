package com.example.test.Contact;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.test.Data.ContactData;
import com.example.test.Loader.ContactLoader;
import com.example.test.R;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends Activity {
    private IndexableListView mListView;
    private ContactAdapter adapter;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        ContactLoader CLoader = new ContactLoader(this);
        // datas : 연락처 데이터
        List<ContactData> datas = CLoader.getContacts();

        mListView = (IndexableListView) findViewById(R.id.listview);
        //final ContentAdapter adapter = new ContentAdapter((ArrayList<ContactData>) datas, getApplicationContext());
        adapter = new ContactAdapter((ArrayList<ContactData>) datas, getApplicationContext());

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

/*        adapter.setCallHistoryButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                ContactData contactData = adapter.getItem(position);
                startActivity(new Intent);
            }
        });*/




        mListView.setAdapter(adapter);
        mListView.setFastScrollEnabled(true);
    }
}