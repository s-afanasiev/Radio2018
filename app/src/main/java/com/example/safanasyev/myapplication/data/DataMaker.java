package com.example.safanasyev.myapplication.data;

public class DataMaker
{
    private static final int DATASET_COUNT = 50;
    
    public static String[] makeEmptyStringArray() {
        String[] mDataset = new String[DATASET_COUNT];
        for (int i = 0; i < DATASET_COUNT; i++) {
            mDataset[i] = "This is element #" + i;
        }
        return mDataset;
    }
}
