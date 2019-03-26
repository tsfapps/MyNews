package com.mynews.utils;

import android.util.Log;

public class CustomLog {
    public static void d(String strTag, String strMessage ){
        Log.d(strTag, strMessage);
    }

    public static void e(String strTag, String strMEssage){
        Log.e(strTag, strMEssage);
    }
}
