package com.example.guessriddle;

import android.util.Log;

import java.io.Serializable;
import java.lang.*;
import java.util.*;

public class Data implements Serializable {
    protected Map<Integer,String> songs;
    protected char[] alphas;

    public Data(){
        // 24+10 我也懒得算了，反正写的是 java
        alphas = new char[50];
        songs = new HashMap<Integer, String>();
    }

    public Data(Data otherData){
        // 24+10 我也懒得算了，反正写的是 java
        alphas = otherData.getAlpha(114514);
        songs = otherData.getSongs(114514);
    }

    public Data(String[] datas){
        alphas = new char[50];
        songs = new HashMap<Integer, String>();

        Integer i = 0;
        try {
            for (String string : datas) {
                songs.put(i, string);
                ++i;
            }
        }
        catch(Exception err){
            // 类型插入错误或者内存爆了
            Log.i("myError" , err.getMessage());
        }
    }

    public Data(char[] chars){
        alphas = new char[50];
        songs = new HashMap<Integer, String>();

        int i = 0;
        try {
            for (char ch : chars) {
                alphas[i] = ch;
                ++i;
            }
        }
        catch(Exception err){
            // 下标越界，基本上都是
            Log.i("myError" , err.getMessage());
        }
    }

    public Data(char[] chars, String[] datas){
        alphas = new char[50];
        songs = new HashMap<Integer, String>();

        Integer i = 0;
        try {
            for (String string : datas) {
                songs.put(i, string);
                ++i;
            }

            i = 0;
            for (char ch : chars) {
                alphas[i.intValue()] = ch;
                ++i;
            }
        }
        catch(Exception err){
            // 类型插入错误或者内存爆了
            Log.i("myError" , err.getMessage());
        }
    }



    public void setData(String[] strings){
        Integer i = 0;
        try {
            for (String string : strings) {
                songs.put(i, string);
                ++i;
            }
        }
        catch(Exception err){
            // 类型插入错误或者内存爆了
            Log.i("myError" , err.getMessage());
        }
    }

    public void setAlpha(char[] chars){
        int i = 0;
        try {
            for (char ch : chars) {
                alphas[i] = ch;
                ++i;
            }
        }
        catch(Exception err){
            // 下标越界，基本上都是
            Log.i("myError" , err.getMessage());
        }
    }

    public char[] getAlpha(int pwd){
        if(pwd == 114514)
            return alphas;
        else
            return null;
    }

    public Map<Integer,String> getSongs(int pwd){
        if(pwd == 114514)
            return songs;
        else
            return null;
    }

    @Override
    public String toString(){
        return songs.values().toString();
    }
}
