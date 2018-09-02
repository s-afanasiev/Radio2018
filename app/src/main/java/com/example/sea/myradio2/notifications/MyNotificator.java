package com.example.sea.myradio2.notifications;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.app.Notification;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.PlaybackStateCompat;

import com.example.sea.myradio2.UI.MainActivity;

public class MyNotificator
{

    //     Уникальный индификатор вашего уведомления в пределах класса
    public static final int NOTIFY_ID = 1;
    private NotificationManagerCompat mNotificationManager;
    public static final int DEFAULT_NOTIFICATION_ID = 101;
    public Notification mNotification;
    private Context context;

    public MyNotificator(Context context) {
        this.context = context;
    }

    public void mySendNotification() {
        //======================================================
        // Создаем экземпляр менеджера уведомлений
        //1.------------------------------------------------

        Intent mainIntent = new Intent(context, MainActivity.class);
        //----- Несколько пробных Интентов для кнопок управления Медиа из Уведомления
        Intent btnPrevIntent = new Intent(context, MainActivity.class);
        Intent nextPrevIntent = new Intent(context, MainActivity.class);
        Intent pausePrevIntent = new Intent(context, MainActivity.class);
        PendingIntent prevPendingIntent = PendingIntent.getActivity(context, 0, btnPrevIntent, 0);
        PendingIntent nextPendingIntent = PendingIntent.getActivity(context, 0, nextPrevIntent, 0);
        PendingIntent pausePendingIntent = PendingIntent.getActivity(context, 0, pausePrevIntent, 0);
        //2.------------------------------------------------
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(mainIntent);
        PendingIntent resultPendingIntent
                = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        //3.------------------------------------------------
        //Создаём и наполняем Билдер, где первые 3 компонента должны быть обязательно
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(android.R.drawable.alert_light_frame)
                .setContentTitle("Заголовок: радио")
                .setContentText("Текст уведомления: радио")
                .setContentIntent(resultPendingIntent)
                .setTicker("Тикер")
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.radio_sputnik))
//                .setShowCancelButton(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setColor(NotificationCompat.COLOR_DEFAULT)
                .addAction(R.drawable.img_play, "play", pausePendingIntent)
                .addAction(R.drawable.btn_next, "next", nextPendingIntent)
                .addAction(R.drawable.btn_prev, "prev", prevPendingIntent);


        // Ненужно для радио - отображает цифру справа от текста уведомления
//            int numMessages = 0;
//            mBuilder.setNumber(++numMessages);
        //3.5.------------------------------------------------
        //Вставка в Билдер некоторых элементов, связанных со стилями - никак не отображается
//        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
//        String[] events = new String[6];
        // Sets a title for the Inbox in expanded layout
//        inboxStyle.setBigContentTitle("BigContentTitle:");
        // Moves events into the expanded layout
//        for (int i=0; i < events.length; i++) {
//            inboxStyle.addLine(events[i]);
//        }
//        mBuilder.setStyle(inboxStyle);

        //3.6---------------------------------------------------
        // Еще надбавки в билдер для мультимедиа кнопок в Уведомлении Notification

//        mBuilder.addAction(R.drawable.btn_prev, "Previous", prevPendingIntent) // #0
//                .addAction(R.drawable.img_pause, "Pause", pausePendingIntent)  // #1
//                .addAction(R.drawable.btn_next, "Next", nextPendingIntent)     // #2
//                // Apply the media style template
//                .setStyle(new android.support.v4.app.NotificationCompat.MediaStyle()
//                                .setShowActionsInCompactView(1 /* #1: pause button */);
//                                .setMediaSession(mMediaSession.getSessionToken()));
        //--------


        //4.------------------------------------------------
        //Последние действия, когда Билдер будет "нафарширован"
//        NotificationCompat mNotification = mBuilder.build();
//        NotificationManagerCompat mNotificationManager = (NotificationManagerCompat) getSystemService(Context.NOTIFICATION_SERVICE);
//        startForeground(DEFAULT_NOTIFICATION_ID, mNotification);
    }

    public Notification makeNotification() {

        //These three lines makes Notification to open main activity after clicking on it
        Intent notificationIntent = new Intent(context, MainActivity.class);
        // выяснить, какой смысл несут следующие две строки, если это уже прописано в Манифесте
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentIntent(contentIntent)
                .setOngoing(true)   //Если true, то Can't be swiped out
                .setSmallIcon(R.mipmap.ic_launcher)
                //.setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.large))   // большая картинка
                .setTicker(Ticker)
                .setContentTitle(Title) //Заголовок
                .setContentText(Text) // Текст уведомления
                .setWhen(System.currentTimeMillis());

        if (android.os.Build.VERSION.SDK_INT <= 15) {
            mNotification = builder.getNotification(); // API-15 and lower
        } else {
            mNotification = builder.build();
        }

//        startForeground(DEFAULT_NOTIFICATION_ID, notification);
        return notification;
    }

    public void closeNotification() {}

    NotificationCompat.Builder makeCachedBuilder() {
        if (mCachedBuilder == null) {
            createCachedBuilder();
        }
        return mCachedBuilder;
    }

    Notification buildPlayPause(boolean play) {
        if (play) {
            return makeCachedBuilder().setIconPlay.build();
        } else {
            return makeCachedBuilder().setIconPause.build();
        }
    }

    void makeMediaNotification(boolean play) {
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notif = buildPlayPause(play);
        mNotificationManager.notify(NOTIFY_ID, notif);
    }

    void createCachedBuilder() {
        Intent mainIntent = new Intent(context, MainActivity.class);
        android.support.v4.app.TaskStackBuilder stackBuilder = android.support.v4.app.TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(mainIntent);
        PendingIntent resultPendingIntent  = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mCachedBuilder = new NotificationCompat.Builder(context);
        mCachedBuilder.setSmallIcon(R.drawable.btn_next)
                .setContentTitle("Заголовок: радио")
                .setContentText("Текст уведомления: радио")
                .setContentIntent(resultPendingIntent)
                .setTicker("Тикер")
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.radio_rossii))
                .setPriority(Notification.PRIORITY_HIGH)
                .setShowWhen(false)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))


        mCachedBuilder.addAction(
                new NotificationCompat.Action(
                        android.R.drawable.ic_media_previous,
                        "prev", MediaButtonReceiver.buildMediaButtonPendingIntent(
                        context, PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS)));

        mCachedBuilder.setStyle(new NotificationCompat.MediaStyle())
                .setMediaSession(mySession))
        .setMediaSession(mediaSession.getSessionToken())
                .setShowCancelButton(true)
                .setCancelButtonIntent(
                        MediaButtonReceiver.buildMediaButtonPendingIntent(
                                context, PlaybackStateCompat.ACTION_STOP))
                .setShowActionsInCompactView(1);
        NotificationCompat.Builder builder = MediaStyleHelper.from(this, mediaSession);
        mCachedBuilder.addAction(new NotificationCompat.Action(
                android.R.drawable.ic_media_previous, getString(R.string.previous),
                MediaButtonReceiver.buildMediaButtonPendingIntent(this, PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS)));
    }
}
