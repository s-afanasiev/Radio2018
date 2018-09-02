package com.example.sea.myradio2.Media;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public interface Readyness {
    boolean onReady(List<Map<String, String>> list, int currentProgramPosition);
}
