package com.example.test_3;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.test_3.Data.ContactData;

import java.util.List;

import static android.content.ContentValues.TAG;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.Holder> implements SectionIndexer  {

    List<ContactData> datas;
    private String mSections = "#ㄱㄴㄷㄹㅁㅂㅅㅇㅈㅊㅋㅌㅍㅎ";

    public ContactAdapter(List<ContactData> datas) {
        this.datas = datas;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        final ContactData data = datas.get(position);

        // 2. 데이터를 세팅
        holder.setTextName(data.getName());
        holder.setTextPhoneNumber(data.getTel());

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    // 1 ok
    @Override
    public Object[] getSections() {
        String[] sections = new String[mSections.length()];
        for (int i = 0; i < mSections.length(); i++)
            sections[i] = String.valueOf(mSections.charAt(i));
        return sections;
    }

    // 2  test..
    @Override
    public int getPositionForSection(int section) {
        // If there is no item for current section, previous section will be selected
        for (int i = section; i >= 0; i--) {
            // j : 연락처를 한바퀴 돌면서 검사
            for (int j = 0; j < datas.size(); j++) {
                if (i == 0) {
                    // For numeric section
                    for (int k = 0; k <= 9; k++) {
                        if (StringMatcher.match(String.valueOf(datas.get(j).getName().charAt(0)), String.valueOf(k)))
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

    //3 ok
    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textPhoneNumber;

        /* Holder 생성자 */
        public Holder(View v) {
            super(v);

            textName = (TextView) itemView.findViewById(R.id.txtName);
            textPhoneNumber = (TextView) itemView.findViewById(R.id.txtPhoneNumber);
        }


        public void setTextName(String name) {
            textName.setText(name);
        }

        public void setTextPhoneNumber(String phoneNumber){
            textPhoneNumber.setText(phoneNumber);
        }
    }

}
