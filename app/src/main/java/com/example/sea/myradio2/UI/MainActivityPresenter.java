package com.example.sea.myradio2.UI;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.sea.myradio2.Controller.AsyncJsoupTesting;
import com.example.sea.myradio2.Media.ServiceRadio;
import com.example.sea.myradio2.R;

import java.util.List;
import java.util.Map;

import static android.content.Context.BIND_AUTO_CREATE;

public class MainActivityPresenter
{
    MainActivity mainActivity;
    MyApplication app;
    Map<String, Boolean> playerState;
    FragmentRadio radioFrag;
    FragmentManager fm;
    BottomNavigationView mBottomNavigationView;
    boolean nowInAnnounceTab;
    AsyncJsoupTesting jt;
    MediaControllerCompat mediaController;

    public void onCreate(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        initGlobalFields();
        initFirstScreen();
        initNavMenu();
        makeBinding();
    }

    public void onStop() {}

    public void onDestroy() {}

    public void onReady(List<Map<String, String>> list, int currentProgramPosition) {
        FragmentRecSchedule fragmentRS = new FragmentRecSchedule();
        if (nowInAnnounceTab)
            fm.beginTransaction().replace(R.id.frag_container, fragmentRS).commit();
    }

    private void initGlobalFields() {
        app = ((MyApplication)mainActivity.getApplicationContext());
        playerState = app.playerState;
    }

    private void makeBinding() {
        mainActivity.bindService(new Intent(mainActivity, ServiceRadio.class),
                new MyServiceConnection(mainActivity), BIND_AUTO_CREATE);
    }

    private void initFirstScreen() {
        radioFrag = new FragmentRadio();
        fm = mainActivity.getFragmentManager();
        fm.beginTransaction().replace(R.id.frag_container, radioFrag).commit();
    }


    private void initNavMenu() {
        mBottomNavigationView = (BottomNavigationView) mainActivity.findViewById(R.id.navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case (R.id.menu_radio):
                        fm.beginTransaction().replace(R.id.frag_container, radioFrag).commit();
                        nowInAnnounceTab = false;
                        break;
                    case (R.id.menu_announce):
                        Toast.makeText(mainActivity, "you pressed 2nd button", Toast.LENGTH_SHORT).show();
                        FragmentEmptyList fragmentEL = new FragmentEmptyList();
                        fm.beginTransaction().replace(R.id.frag_container, fragmentEL).commit();
                        jt = new AsyncJsoupTesting(mainActivity);
                        jt.execute();
                        nowInAnnounceTab = true;
                        break;
                    case (R.id.menu_refs):
                        nowInAnnounceTab = false;
//                        fm.beginTransaction().remove(radioFrag).commit();
//                        jt = new AsyncJsoupTesting(MainActivity.this);
//                        jt.execute();
//                        Toast.makeText(MainActivity.this, "see Logs", Toast.LENGTH_LONG).show();
                        break;
                    case (R.id.menu_else):
                        Toast.makeText(mainActivity, "it's 4nd button", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }
}
