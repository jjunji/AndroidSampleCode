package com.example.test_contact;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;

import com.example.test_contact.Data.ContactData;
import com.example.test_contact.Loader.ContactLoader;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Tag

    public static final String TAG = MainActivity.class.getCanonicalName();

    RecyclerView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (RecyclerView) findViewById(R.id.listView);

/*        List<String> items = new ArrayList<String>();

        for (char ch : SideSelector.HANGUL) {
            for (int i = 1; i <= 50; i++) {
                items.add(String.valueOf(ch) + "-" + i);
            }
        }*/

        ContactLoader CLoader = new ContactLoader(this);
        List<ContactData> datas = CLoader.getContacts();

        ContactAdapter adapter = new ContactAdapter(datas);

        listView.setAdapter(adapter);

        listView.setLayoutManager(new LinearLayoutManager(this));

        SideSelector sideSelector = (SideSelector) findViewById(R.id.side_selector);
        sideSelector.setListView(listView);

        /*listView.setAdapter(new IndexingArrayAdapter(this, android.R.layout.simple_list_item_1, items));

        SideSelector sideSelector = (SideSelector) findViewById(R.id.side_selector);
        sideSelector.setListView(listView);*/
    }



    public class IndexingArrayAdapter extends ArrayAdapter<String> implements SectionIndexer {
        public IndexingArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
        }

        public Object[] getSections() {
            String[] chars = new String[SideSelector.HANGUL.length];
            for (int i = 0; i < SideSelector.HANGUL.length; i++) {
                chars[i] = String.valueOf(SideSelector.HANGUL[i]);
            }

            return chars;
        }


        public int getPositionForSection(int i) {

            Log.d(TAG, "getPositionForSection " + i);
            return (int) (getCount() * ((float)i/(float)getSections().length));
        }

        public int getSectionForPosition(int i) {
            return 0;
        }
    }


}
