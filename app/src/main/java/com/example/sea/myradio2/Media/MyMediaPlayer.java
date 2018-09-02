package com.example.sea.myradio2.Media;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;
import java.util.Map;

public class MyMediaPlayer extends MediaPlayer implements MediaPlayer.OnPreparedListener {
    private final String TAG = "MY_LOG";
    String stream64 = "http://icecast.vgtrk.cdnvideo.ru/rrzonam_mp3_64kbps";
    String stream128 = "http://icecast.vgtrk.cdnvideo.ru/rrzonam_mp3_128kbps";
    String stream192 = "http://icecast.vgtrk.cdnvideo.ru/rrzonam_mp3_192kbps";
    Map<String, Boolean> playerState;

    OnPreparedListener listener;
    private static MyMediaPlayer playerInstance;


    private MyMediaPlayer() {
        // реализуем шаблон Singleton
    }

    public static MyMediaPlayer getInstance() {
        if (playerInstance == null) {
            playerInstance = new MyMediaPlayer();
        }
        return playerInstance;
    }

    public void doPreparing() {
            playerInstance.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {playerInstance.setDataSource(stream64);
            } catch (IOException e) {e.printStackTrace();}
        playerInstance.prepareAsync();
        Log.d(TAG, "doPreparing: prepare async");
    }

    public void setToPrepareListener(Map<String, Boolean> playerState) {
        this.playerState = playerState;
        playerInstance.setOnPreparedListener(this);
    }

    @Override
    public void onPrepared(MediaPlayer player) {
        playerState.put("playerIsPrepared", true);
        Log.d(TAG, "onPrepared: playerIsPrepared ");
        if (playerState.get("btnPlayIsPressed")) {
            player.start();
        }
    }
}

