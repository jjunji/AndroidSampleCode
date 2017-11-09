package com.example.tenmanager_1.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.FindContactActivity;
import com.example.tenmanager_1.MainActivity;
import com.example.tenmanager_1.R;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class SmsFragment extends Fragment {
    View view;
    TextView txtResultName;
    Button btnContactSearch;
    private  final int REQUESTCODE = 1;

    public SmsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sms, container, false);

        initView();

        return view;
    }

    private void initView() {
        txtResultName = (TextView) view.findViewById(R.id.txtResultName);
        btnContactSearch = (Button) view.findViewById(R.id.btnContactSearch);
        btnContactSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FindContactActivity.class);
                startActivityForResult(intent, REQUESTCODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            if(requestCode == REQUESTCODE){
                ArrayList<Integer> ar = data.getIntegerArrayListExtra("listObject"); // 체크박스 누른 포지션
//                Integer arIds[] = new Integer[];

                Integer arList[] = new Integer[ar.size()]; // arList 배열 선언

                for(int i=0; i<arList.length; i++){
                    arList[i] = ar.get(i); // arList 에는 체크박스 누른 포지션이 담긴다.
                }
                Realm realm = Realm.getDefaultInstance();
                RealmResults<ContactVO> results = realm.where(ContactVO.class).in("id", arList).findAll();

                Log.i("test","results==============" + results);

                String resultName = "";
                for(int i=0; i<arList.length; i++){
                    //resultName += results.get(i).getName();
                    resultName = resultName + (results.get(i).getName() + "  /");
                }
                txtResultName.setText(resultName);
            }
        }
    }
}
