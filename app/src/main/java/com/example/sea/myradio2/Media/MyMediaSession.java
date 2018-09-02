package com.example.sea.myradio2.Media;

import android.content.Context;
import android.support.v4.media.session.MediaSessionCompat;

/**
 * Created by sea on 03.12.2017.
 */

public class MyMediaSession extends MediaSessionCompat {
    public MyMediaSession(Context context, String tag) {
        super(context, tag);
    }

    @Override
    public void setFlags(int flags) {
        super.setFlags(flags);
    }
}
