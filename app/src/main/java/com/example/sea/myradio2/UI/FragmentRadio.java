package com.example.sea.myradio2.UI;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaControllerCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sea.myradio2.Media.MyMediaPlayer;
import com.example.sea.myradio2.R;

import java.util.Map;


public class FragmentRadio extends Fragment implements View.OnClickListener {

    MyMediaPlayer mp;
    private static final String TAG = "MY_LOG";
    ImageButton btn_playPause;
    Intent mIntent;
    MyApplication app;
    Map<String, Boolean> playerState;
    MediaControllerCompat mediaController;
    MainActivity mActivity;

    SharedPreferences sharPref;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (MyApplication) getActivity().getApplicationContext();
        playerState = app.playerState;
        Log.d(TAG, "onCreate: FragmentRadio btnPlayIsPressed = " + playerState.get("btnPlayIsPressed"));
        mActivity = (MainActivity) getActivity();
        mp = MyMediaPlayer.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: FragmentRadio btnPlayIsPressed = " + playerState.get("btnPlayIsPressed"));
        View v = inflater.inflate(R.layout.radio_fragment, null);
        btn_playPause = (ImageButton) v.findViewById(R.id.btn_play_stop);
        btn_playPause.setOnClickListener(this);
//        MainActivity act = (MainActivity)getActivity();

        if (playerState.get("btnPlayIsPressed")) {
            btn_playPause.setImageResource(R.drawable.img_pause);
        } else { btn_playPause.setImageResource(R.drawable.img_play);}
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: FragmentRadio btnPlayIsPressed = " + playerState.get("btnPlayIsPressed"));
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(), "You pressed Play-button", Toast.LENGTH_SHORT).show();
//        mIntent = new Intent(getActivity(), ServiceRadio.class);
//        getActivity().startService(mIntent);
        mediaController = mActivity.mediaController;
        Log.d(TAG, "Fragment onClick: переменная btnPlayIsPressed = " + playerState.get("btnPlayIsPressed"));
        Log.d(TAG, "Fragment onClick: (mediaController == null) = " + (mediaController == null));

        if (playerState.get("btnPlayIsPressed")) {
            if (mediaController != null) mediaController.getTransportControls().pause();
            playerState.put("btnPlayIsPressed", false);
            btn_playPause.setImageResource(R.drawable.img_play);
            Log.d(TAG, "onClick: btn Pause Is Pressed now");
        } else {
            if (mediaController != null) mediaController.getTransportControls().play();
            playerState.put("btnPlayIsPressed", true);
            btn_playPause.setImageResource(R.drawable.img_pause);
            Log.d(TAG, "onClick: btn Play Is Pressed now");
        }
//        mediaController.getTransportControls().stop();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: FragmentRadio");
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
