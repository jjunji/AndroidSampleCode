package com.example.tenmanager_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
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

import com.example.tenmanager_1.Fragment.AlarmFragment;
import com.example.tenmanager_1.Fragment.CustomerFragment;
import com.example.tenmanager_1.Fragment.HomeFragment;
import com.example.tenmanager_1.Fragment.MarketFragment;
import com.example.tenmanager_1.Fragment.SmsFragment;
import com.example.tenmanager_1.Service_Dialog.CallingService;
import com.example.tenmanager_1.repositories.ContactDataSource;
import com.example.tenmanager_1.repositories.impl.ContactRepository;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    private final String TAG = MainActivity.class.getSimpleName();



//    RealmResults<ContactVO> customerList;

    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;
    private final int FRAGMENT3 = 3;
    private final int FRAGMENT4 = 4;
    private final int FRAGMENT5 = 5;

    ImageView btnHome, btnAlarm, btnSms, btnCustomer, btnMarket;

    ContactDataSource contactDataSource = new ContactRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
        Boolean isInit = prefs.getBoolean("init", true); // 저장된 값이 없으면 true , 있으면 false -> 있으면 카피메서드 실행 안함.
        if(isInit){
            contactDataSource.contactCopyFromDevice();
        }

        init();
        setButtonListener();
        callFragment(FRAGMENT1);


        Intent intent = new Intent(this, CallingService.class);
        startService(intent);
    }

    // 초기화
    private void init() {
        btnHome = (ImageView) findViewById(R.id.btnHome);
        btnAlarm = (ImageView) findViewById(R.id.btnAlarm);
        btnSms = (ImageView) findViewById(R.id.btnSms);
        btnCustomer = (ImageView) findViewById(R.id.btnCustomer);
        btnMarket = (ImageView) findViewById(R.id.btnMarket);

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