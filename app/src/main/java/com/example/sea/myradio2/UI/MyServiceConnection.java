package com.example.sea.myradio2.UI;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.sea.myradio2.Media.ServiceRadio;

import java.util.Map;

public class MyServiceConnection implements ServiceConnection
{
    MyApplication app;
    ServiceRadio.PlayerServiceBinder playerServiceBinder;
    MainActivity mainActivity;
    MediaControllerCompat mediaController;
    Map<String, Boolean> playerState;

    public MyServiceConnection(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        app = (MyApplication) mainActivity.getApplicationContext();
        playerState = app.playerState;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        playerServiceBinder = (ServiceRadio.PlayerServiceBinder) service;
        try {
            mediaController = new MediaControllerCompat(mainActivity,
                    playerServiceBinder.getMediaSessionToken());

            mediaController.registerCallback(
                    new MediaControllerCompat.Callback() {
                        @Override
                        public void onPlaybackStateChanged(PlaybackStateCompat state) {
                            if (state == null)
                                return;
                            boolean playing = (state.getState() == PlaybackStateCompat.STATE_PLAYING);
                            playerState.put("btnPlayIsPressed", playing);
                        }
                    }
            );
        } catch (RemoteException e) {
            mediaController = null;
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        playerServiceBinder = null;
        mediaController = null;
    }

}
