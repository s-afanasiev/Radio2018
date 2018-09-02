package com.example.sea.myradio2.Media;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import com.example.sea.myradio2.R;
import com.example.sea.myradio2.UI.MyApplication;
import com.example.sea.myradio2.notifications.MyNotificator;
import java.util.Map;

public class ServiceRadio extends Service {

    private final String TAG = "MY_LOG";
    MyApplication app;
    Map<String, Boolean> playerState;
    MyMediaManager mm;
    MyNotificator notificator;

    @Override
    public void onCreate() {
        super.onCreate();
        mm = new MyMediaManager(this);
        mm.makeRadio();
        notificator = new MyNotificator(this);
        startForeground(777, notificator.makeNotification());
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mm.handleMediaButtonReceiver();
        //return Service.START_STICKY;
//        return START_REDELIVER_INTENT;
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // Ресурсы освобождать обязательно
        mm.releaseRadio();
        mm = null;
        notificator.closeNotification();
        notificator = null;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return new PlayerServiceBinder();
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
    }


    public class PlayerServiceBinder extends Binder {
        public MediaSessionCompat.Token getMediaSessionToken() {
            return mm.getSessionToken();
        }
    }

}

