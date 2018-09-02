package com.example.sea.myradio2.UI;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.sea.myradio2.Controller.AdapterSchedule;
import com.example.sea.myradio2.Controller.AsyncJsoupTesting;
import com.example.sea.myradio2.R;
import com.example.sea.myradio2.Media.Readyness;
import com.example.sea.myradio2.Media.ServiceRadio;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Readyness {

    private static final String TAG = "MY_LOG";

    AsyncJsoupTesting jt;
    FragmentManager fm;
    Fragment radioFrag;
    List<Map<String, String>> mScheduleList;
    public AdapterSchedule mAdapterSchedule;
    BottomNavigationView mBottomNavigationView;
    ServiceRadio.PlayerServiceBinder playerServiceBinder;
    MediaControllerCompat mediaController;
    static MainHandler mainHandler;
    MainActivityPresenter actPresenter;

//================================================================
//======Implementation============================================
//================================================================

    static final class MainHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_nav_menu);
        actPresenter = new MainActivityPresenter();
        actPresenter.onCreate(this);
    }


    @Override
    public void onReady(List<Map<String, String>> list, int currentProgramPosition) {
        actPresenter.onReady(list, currentProgramPosition);
    }


    // Методы onSaveInstanceState и onRestoreInstanceState не рекомендуются разработчиками,
    // так как не всегда срабатывают. Вместо этого рекомендуют сохранять в методе onPause()
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) { Log.d(TAG, "onRestoreInstanceState: "); }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        actPresenter.onStop();
        Log.d(TAG, "Activity is onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        actPresenter.onDestroy();
        Log.d(TAG, "Activity is onDestroy: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Activity is onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Activity is onResume: ");
    }

}