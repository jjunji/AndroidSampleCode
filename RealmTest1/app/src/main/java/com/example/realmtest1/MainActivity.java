package com.example.realmtest1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realmtest1.Data.ContactData;
import com.example.realmtest1.Data.ContactVO;
import com.example.realmtest1.Loader.ContactLoader;

import org.w3c.dom.Text;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = MainActivity.class.getSimpleName();

    Realm realm;
    Button btnCopy;
    TextView txtCopy;
    ContactLoader cLoader;

    ArrayList<ContactData> datas = new ArrayList<>();
    RealmResults<ContactVO> customerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        init();
        setBtnListener();

        //doCopy(realm);
    }

    private  void doDeleteContactAll(){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Log.i("test", "delete Start");
                realm.delete(ContactVO.class);
                printAllContact();
            }
        });

//
//        final RealmResults<ContactVO> results =realm.where(ContactVO.class).findAll();
//
//         realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                if(results.size() >0){
//                    for(ContactVO contactVO : results){
////                        realm.deleteAll();
//                        realm.delete(ContactVO.class);
//                    }
//                }
//            }
//        });
    }

    private void doCopy(Realm realm) {
        realm.beginTransaction();
        Log.i("test", "copy Start");
        for(int i=0; i<datas.size(); i++){
            String tel = datas.get(i).getTel();

            ContactVO contactVO = realm.where(ContactVO.class).equalTo("tel", tel).findFirst();
            if(contactVO == null){
                ContactVO cv = realm.createObject(ContactVO.class);
                cv.setName(datas.get(i).getName());
                cv.setTel(tel);
                Log.i("test", "insert data is : "+cv.toString());
            }
        }
        realm.commitTransaction();

        printAllContact();

//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {

//            }
//        });
    }

    private void init() {
        //realm = Realm.getInstance(this); // Realm 인스턴스 얻기
        btnCopy = (Button) findViewById(R.id.btnCopy);
        txtCopy = (TextView) findViewById(R.id.txtCopy);
        cLoader = new ContactLoader(this);
        datas = (ArrayList<ContactData>) cLoader.getContacts();
        customerList = getCustomerList();

        Log.i(TAG, ">>>>>   userList.size :  " + customerList.size()); // :0
        //data = realm.createObject(ContactData.class);
    }

/*    private void insertCustomerData() {
        realm.beginTransaction();
        ContactVO cv = realm.createObject(ContactVO.class);
        for(int i=0; i<datas.size(); i++){
            cv.setName(datas.get(i).getName());
            cv.setTel(datas.get(i).getTel());
        }
        realm.commitTransaction();
    }*/

    private void setBtnListener() {
        btnCopy.setOnClickListener(this);
        findViewById(R.id.btnDelete).setOnClickListener(this);
    }

    private RealmResults<ContactVO> getCustomerList(){
        return realm.where(ContactVO.class).findAll();
    }

    private void printAllContact(){
        RealmResults<ContactVO>  results = realm.where(ContactVO.class).findAll();

        for(ContactVO contactVO :  results){
            Log.i("test", "printAllContact data is : "+contactVO.toString());
        }

    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnCopy){
            Toast.makeText(MainActivity.this, "Test", Toast.LENGTH_SHORT).show();
            //insertCustomerData();
            doCopy(realm);
            Log.i(TAG, ">>>>>   userList.size :  " + customerList.size()); // :1
        }
        else if(v.getId() == R.id.btnDelete){
            doDeleteContactAll();
        }
    }
}
