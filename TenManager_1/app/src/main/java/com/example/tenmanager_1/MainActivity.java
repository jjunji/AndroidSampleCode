package com.example.tenmanager_1;

import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.tenmanager_1.Data.ContactData;
import com.example.tenmanager_1.Data.ContactVO;
import com.example.tenmanager_1.Fragment.AlarmFragment;
import com.example.tenmanager_1.Fragment.CustomerFragment;
import com.example.tenmanager_1.Fragment.HomeFragment;
import com.example.tenmanager_1.Fragment.MarketFragment;
import com.example.tenmanager_1.Fragment.SmsFragment;
import com.example.tenmanager_1.Loader.ContactLoader;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    private final String TAG = MainActivity.class.getSimpleName();

    Realm realm;
    ArrayList<ContactData> datas = new ArrayList<>();
    ContactLoader cLoader = new ContactLoader(this);
    //RealmResults<ContactVO> customerList;

    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;
    private final int FRAGMENT3 = 3;
    private final int FRAGMENT4 = 4;
    private final int FRAGMENT5 = 5;

    ImageView btnHome, btnAlarm, btnSms, btnCustomer, btnMarket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();  // Realm 인스턴스를 얻는다.

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // -----------------------------------------------------------------------------

        init();
        setButtonListener();
        callFragment(FRAGMENT1);
        doCopyContact(realm);

    }

    // 초기화
    private void init() {
        btnHome = (ImageView) findViewById(R.id.btnHome);
        btnAlarm = (ImageView) findViewById(R.id.btnAlarm);
        btnSms = (ImageView) findViewById(R.id.btnSms);
        btnCustomer = (ImageView) findViewById(R.id.btnCustomer);
        btnMarket = (ImageView) findViewById(R.id.btnMarket);
        datas = (ArrayList<ContactData>) cLoader.getContacts();
    }

    // 버튼 리스너 등록
    private void setButtonListener(){
        btnHome.setOnClickListener(this);
        btnAlarm.setOnClickListener(this);
        btnSms.setOnClickListener(this);
        btnCustomer.setOnClickListener(this);
        btnMarket.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnHome :
                callFragment(FRAGMENT1);
                break;
            case R.id.btnAlarm :
                callFragment(FRAGMENT2);
                break;
            case R.id.btnSms :
                callFragment(FRAGMENT3);
                break;
            case R.id.btnCustomer :
                callFragment(FRAGMENT4);
                break;
            case R.id.btnMarket :
                callFragment(FRAGMENT5);
                break;
        }
    }

    public void callFragment(int fragmentNo){
        // 프래그먼트 사용을 위해 트랜잭션 객체 선언
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (fragmentNo){
            case 1:
                HomeFragment homeFragment = new HomeFragment();
                transaction.replace(R.id.fragment_container, homeFragment);
                transaction.commit();
                break;

            case 2:
                AlarmFragment alarmFragment = new AlarmFragment();
                transaction.replace(R.id.fragment_container, alarmFragment);
                transaction.commit();
                break;

            case 3:
                SmsFragment smsFragment = new SmsFragment();
                transaction.replace(R.id.fragment_container, smsFragment);
                transaction.commit();
                break;

            case 4:
                CustomerFragment customerFragment = new CustomerFragment();
                transaction.replace(R.id.fragment_container, customerFragment);
                transaction.commit();
                break;

            case 5:
                MarketFragment marketFragment = new MarketFragment();
                transaction.replace(R.id.fragment_container, marketFragment);
                transaction.commit();
                break;

/*            case 6:
                ContactFragment contactFragment = new ContactFragment();
                transaction.replace(R.id.fragment_container, contactFragment);
                transaction.commit();
                break;*/
        }
    }

    public void doCopyContact(Realm realm){
        realm.beginTransaction(); //
        //Log.i(TAG, "copy start");

        for(int i = 0; i<datas.size(); i++){ // 주소록의 사이즈만큼 돌면서
            String tel = datas.get(i).getTel();
            ContactVO contactVO = realm.where(ContactVO.class).equalTo("tel", tel).findFirst();  // 번호가 기존 데이터에 있는지 검사(중복 검사)

            // 중복되는 것이 없다면 추가.
            if(contactVO == null){
                ContactVO cv = realm.createObject(ContactVO.class); // cv : 새로운 객체 생성.
                cv.setName(datas.get(i).getName());
                cv.setTel(tel);
                //Log.i(TAG, "insert data is : "+cv.toString());
            }
        }
        realm.commitTransaction();
        printAllContact();
    }

    private void printAllContact() {
        RealmResults<ContactVO>  results = realm.where(ContactVO.class).findAll();

        for(ContactVO contactVO :  results){
           /* Log.i("test", "printAllContact data is : "+contactVO.toString());
            Log.i(TAG, "position data : " + contactVO.getName());*/
        }
    }


    // ----------------------------------------------

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}