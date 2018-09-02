package com.example.sea.myradio2.Media;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;

import com.example.sea.myradio2.UI.MainActivity;
import com.example.sea.myradio2.UI.MyApplication;

import java.util.Map;

public class MyMediaManager implements IMyMediaManager {

    MyMediaPlayer mplayer;
    MediaSessionCompat mediaSession;
    MyMediaSessionCallback mediaSessionCallback;

    Context context;
    MyApplication app;
    Map<String, Boolean> playerState;

    public MyMediaManager(Context context) {
        this.context = context;
    }

    @Override
    public void makeRadio() {
        initGlobalFields();
        initPlayer();
        initMediaSession();
        createMediaButtonIntent();
    }

    @Override
    public void releaseRadio() {
        mediaSession.release();
        mediaSession = null;
    }

    @Override
    public void handleMediaButtonReceiver() {
        MediaButtonReceiver.handleIntent(mediaSession, createMediaButtonIntent());
    }

    public MediaSessionCompat.Token getSessionToken(){
        return mediaSession.getSessionToken();
    }


    private void initGlobalFields() {
        app = ((MyApplication) context.getApplicationContext());
        playerState = app.playerState;
    }

    private void initPlayer() {
        mplayer = MyMediaPlayer.getInstance();
        mplayer.setToPrepareListener(playerState);
    }

    private void initMediaSession() {
        mediaSession = new MediaSessionCompat(context, "PlayerService");
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSessionCallback = new MyMediaSessionCallback(context, mplayer, mediaSession);
        mediaSession.setCallback(mediaSessionCallback);
        mediaSession.setSessionActivity(PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0));
    }


    private Intent createMediaButtonIntent() {
        Intent mediaButtonIntent = new Intent(
                Intent.ACTION_MEDIA_BUTTON, null, context, MediaButtonReceiver.class);
        mediaSession.setMediaButtonReceiver(
                PendingIntent.getBroadcast(context, 0, mediaButtonIntent, 0));

        return mediaButtonIntent;
    }


}
