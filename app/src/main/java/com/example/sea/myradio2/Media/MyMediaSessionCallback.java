package com.example.sea.myradio2.Media;

import android.content.Context;
import android.media.AudioManager;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import com.example.sea.myradio2.UI.MyApplication;
import java.util.Map;

public class MyMediaSessionCallback extends MediaSessionCompat.Callback
{
    private Map<String, Boolean> playerState;
    private MyMediaPlayer mplayer;
    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;
    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;
    private final String TAG = "MediaSessionCallback";

    public MyMediaSessionCallback(Context context, MyMediaPlayer mp, MediaSessionCompat ms) {
        mplayer = mp;
        mediaSession = ms;

        MyApplication app = ((MyApplication) context.getApplicationContext());
        playerState = app.playerState;

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE
                        | PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                        | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS);

        audioFocusChangeListener =
                new AudioManager.OnAudioFocusChangeListener() {
                    @Override
                    public void onAudioFocusChange(int focusChange) {
                        switch (focusChange) {
                            case AudioManager.AUDIOFOCUS_GAIN:
                                onPlay();
                                break;
                            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                                onPause();
                                break;
                            default:
                                // Фокус совсем отняли.
                                onPause();
                                break;
                        }
                    }
                };
    }

    @Override
    public void onPlay() {
        Log.d(TAG, "----------> мы должны попасть сюда");
        if (playerState.get("playerIsPrepared")) {
            Log.d(TAG, "onPlay: playerIsPrepared");
            mplayer.start();
            playerState.put("firstPressedFlag", true);

        } else if (!playerState.get("playerIsPrepared")) {
            if (playerState.get("firstPressedFlag")) {
                Log.d(TAG, "onPlay: firstPressedFlag");
                playerState.put("firstPressedFlag", false);
                mplayer.doPreparing();
                Log.d(TAG, "onPlay: Calling mplayer.doPreparing()");
            }
        }

        int audioFocusResult = audioManager.requestAudioFocus(
                audioFocusChangeListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);
        if (audioFocusResult != AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
            return;

        // делаем активной текущую сессию (имеется ввиду, что сессий может быть несколько)
        mediaSession.setActive(true);
        mediaSession.setPlaybackState(stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                        PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1).build());
    }

    @Override
    public void onPause() {
        if (mplayer != null)
            mplayer.pause();

        audioManager.abandonAudioFocus(audioFocusChangeListener);
        // не факт, что нужно выполнять следующую строку
        mediaSession.setActive(false);

        mediaSession.setPlaybackState(stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1).build());
    }

}
