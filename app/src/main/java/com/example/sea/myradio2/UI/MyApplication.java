package com.example.sea.myradio2.UI;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class MyApplication extends Application {
    public final String TAG = "MY_LOG";

    public Map<String, Boolean> playerState;

    @Override
    public void onCreate() {
        super.onCreate();
        playerState = new HashMap<>();
        //Плеер подготовлен для запуска
        playerState.put("playerIsPrepared", false);
        // Кнопка Play уже была нажата, после инстанциирования stream плеера.
        // Это значит, что после нажатия кнопки Play, запускается метод prepareAsync плеера
        // Состояние защищает от излишнего вызова prepareAsync,
        // в случае если пользователь будет много раз нажимать Play/Pause
        playerState.put("firstPressedFlag", true);
        // переключатель-индикатор, что сейчас нажал пользователь - Play или Pause
        playerState.put("btnPlayIsPressed", false);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "MyApplication is onTerminate: ");
    }
}
