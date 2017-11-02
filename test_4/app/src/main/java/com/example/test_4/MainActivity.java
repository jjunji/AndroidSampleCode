package com.example.test_4;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.test_4.Data.ContactData;
import com.example.test_4.Loader.ContactLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private ArrayList<String> mItems;
    private IndexableListView mListView;

    //RecyclerView listView;
    IndexableListView listView2;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String mSections = "#ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍㅎ";

        //mItems = new ArrayList<String>();
        listView2 = (IndexableListView) findViewById(R.id.listView);

        ContactLoader CLoader = new ContactLoader(this);
        // datas : 연락처 데이터
        List<ContactData> datas = CLoader.getContacts();

        ContactAdapter adapter = new ContactAdapter(datas, mSections);

        listView2.setAdapter(adapter);
        listView2.setFastScrollEnabled(true);
        listView2.setLayoutManager(new LinearLayoutManager(this));

        //Collections.sort(mItems);

/*        ContentAdapter adapter = new ContentAdapter(this,
                android.R.layout.simple_list_item_1, mItems);

        mListView = (IndexableListView) findViewById(R.id.listview);
        mListView.setAdapter(adapter);
        mListView.setFastScrollEnabled(true);*/




    }

   /* private class ContentAdapter extends ArrayAdapter<String> implements SectionIndexer {

        private String mSections = "#ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍㅎ";

        public ContentAdapter(Context context, int textViewResourceId,
                              List<String> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public int getPositionForSection(int section) {
            // If there is no item for current section, previous section will be selected
            for (int i = section; i >= 0; i--) {
                for (int j = 0; j < getCount(); j++) {
                    if (i == 0) {
                        // For numeric section
                        for (int k = 0; k <= 9; k++) {
                            if (StringMatcher.match(String.valueOf(getItem(j).charAt(0)), String.valueOf(k)))
                                return j;
                        }
                    } else {
                        if (StringMatcher.match(String.valueOf(getItem(j).charAt(0)), String.valueOf(mSections.charAt(i))))
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

*//*        @Override
        public Object[] getSections() {
            String[] sections = new String[mSections.length()];
            for (int i = 0; i < mSections.length(); i++)
                sections[i] = String.valueOf(mSections.charAt(i));
            return sections;
        }*//*
    }*/
}
