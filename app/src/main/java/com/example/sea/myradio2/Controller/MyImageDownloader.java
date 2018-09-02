package com.example.sea.myradio2.Controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.sea.myradio2.Media.Readyness;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by sea on 29.01.2018.
 */

public class MyImageDownloader implements Readyness {
    int currentProgramPosition;
    List<Map<String, String>> list;
    @Override
    public boolean onReady(List<Map<String, String>> list, int currentProgramPosition) {
        this.currentProgramPosition = currentProgramPosition;
        this.list = list;
        return true;
    }

    private Handler mHandler;
    ThreadPoolExecutor tpe;
    private final static String TAG = "ImageManager";
    String iUrl;

    MyImageDownloader(String iUrl) {
        mHandler = new Handler() {
            public void handleMessage(Message message) {
                Bitmap image = (Bitmap) message.obj;
            }
        };
        this.iUrl = iUrl;
    }

    private Runnable getImageRunnable = new Runnable() {
        public void run() {
            final Bitmap image = downloadImage(iUrl);
            if (image != null) {
                Log.v(TAG, "Got image by URL: " + iUrl);
                mHandler.post(getImageRunnable);
            }
        }
    };

    public static Bitmap downloadImage(String iUrl) {
        Bitmap bitmap = null;
        HttpURLConnection conn = null;
        BufferedInputStream buf_stream = null;
        try {
            Log.v(TAG, "Starting loading image by URL: " + iUrl);
            conn = (HttpURLConnection) new URL(iUrl).openConnection();
            conn.setDoInput(true);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.connect();
            buf_stream = new BufferedInputStream(conn.getInputStream(), 8192);
            bitmap = BitmapFactory.decodeStream(buf_stream);
            buf_stream.close();
            conn.disconnect();
            buf_stream = null;
            conn = null;
        } catch (MalformedURLException ex) {
            Log.e(TAG, "Url parsing was failed: " + iUrl);
        } catch (IOException ex) {
            Log.d(TAG, iUrl + " does not exists");
        } catch (OutOfMemoryError e) {
            Log.w(TAG, "Out of memory!!!");
            return null;
        } finally {
            if (buf_stream != null)
                try {
                    buf_stream.close();
                } catch (IOException ex) {
                }
            if (conn != null)
                conn.disconnect();
        }
        return bitmap;
    }
}
