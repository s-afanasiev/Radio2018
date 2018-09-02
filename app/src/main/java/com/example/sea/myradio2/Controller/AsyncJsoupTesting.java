package com.example.sea.myradio2.Controller;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.sea.myradio2.Media.Readyness;
import com.example.sea.myradio2.UI.MainActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsyncJsoupTesting extends AsyncTask<Void, Void, Void> {

    private final static String TAG = "MY_LOG";
    private Document doc;

    private Context context;
    private MainActivity mainActivity;
    private List<Map<String, String>> arraySchedule;
    private List<String> pictureLinksList;
    List arrayPics = new ArrayList<Drawable>();
    private boolean passed = true;

    private int currentProgramPosition = 0;


    public AsyncJsoupTesting(Context c) {
        context = c;
        mainActivity = (MainActivity) context;
    }

//    public int getCurrentProgramPosition() {
//        return currentProgramPosition;
//    }


    @Override
    protected void onPostExecute(Void aVoid) {
        Log.d(TAG, "onPostExecute: ");
        if (doc != null) {
            super.onPostExecute(aVoid);
            MyImageDownloader imgDownloader = new MyImageDownloader("ddd");
            Readyness rds[] = {mainActivity, imgDownloader};
            for (int i = 0; i < rds.length; i++) {
                rds[i].onReady(arraySchedule, currentProgramPosition);
            }
            // todo вызываем ещё один AsyncTask для загрузки картинок

        }
        // Todo переделать потом с помощью интерфейса
    }


    @Override
    protected Void doInBackground(Void... params) {

        Log.d(TAG, "doInBackground: ");
        try {
            doc = Jsoup.connect("http://www.radiorus.ru/tvp/").get();
            Log.d(TAG, "doInBackground: connected to site");
//            docProgs = Jsoup.connect("http://www.radiorus.ru/programs/").get();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "AsyncJsoupTesting.doInBackground: Jsoup.connect() Exception ");

        }

        // получаем данные для адаптера методом 1
//        getDataMethod1();
        // получаем данные для адаптера методом 2
        if (doc != null)
            getDataMethod2();
        else {
            Toast.makeText(context, "соединение с интернетом отсутствует!" , Toast.LENGTH_SHORT).show();}

        return null;
    }

    void getDataMethod2(){
        arraySchedule = new ArrayList<>();
        pictureLinksList = new ArrayList<>();
        Elements elemsTime = doc.select(".networkBlock-time");
        Elements elemsTitle = doc.select(".networkBlock-title");
        Elements elemsContent = doc.select(".networkBlock-content");
        for (int i = 0; i < elemsTime.size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("time", elemsTime.get(i).text());
            map.put("title", elemsTitle.get(i+2).text());
            map.put("content", elemsContent.get(i).text());
            arraySchedule.add(map);
        }
        currentProgramPosition = calculateCurrentProgramPosition();
    }

    void getDataMethod1(){
        arraySchedule = new ArrayList<>();
        Element elemRef = doc.select(".networkBlock-borderRed").first();
        discoverElements(elemRef);
        // получаем текущую int позицию списка(передача, которая идёт в настоящее время)
//        currentProgramPosition = calculateCurrentProgramPosition();
    }


    private boolean discoverElements(Element elem) {
        // первые три штуки - для адаптера: время, текст, подробности
        Element elemTime = elem.select(".networkBlock-time").first();
        Element elemTitle = elem.select(".networkBlock-title").first();
        Element elemContent = elem.select(".networkBlock-content").first();
        Map<String, String> map = new HashMap<>();
        map.put("time", elemTime.text());
        map.put("title", elemTitle.text());
        map.put("content", elemContent.text());
        arraySchedule.add(map);
        Log.d(TAG, "discoverElements: another item of data is ready");

        // ссылки на картинки для второго AsyncTask (т.е. картинки будут загружены позже):
//        String linkHref = elem.attr("href");
//        pictureLinksList.add(linkHref);

        //рекурсивный вызов для прохождения по всей HTML-странице
        Element elemNext = elem.nextElementSibling();
        if (elemNext != null) {
            discoverElements(elemNext);
        }
        return true;
    }


    private int calculateCurrentProgramPosition() {

        Elements elemsPassed = doc.select("a.passed");
        int ii = elemsPassed.size();
        Log.d(TAG, "getCurrentProgramPosition: int ii = " + ii);
        return (ii - 1);
    }
}
