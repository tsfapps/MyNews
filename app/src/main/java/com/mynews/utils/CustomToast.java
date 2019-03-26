package com.mynews.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class CustomToast {
    private Context tCtx;
    private String message;
    public CustomToast(Context tCtx) {
        this.tCtx = tCtx;
    }

    public static void tToast(Context tCtx, String message){
        Toast toast = Toast.makeText( tCtx, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, Constant.xOffSet, Constant.yOffSet);
        toast.show();
    }

    public void toastTotal(String strMsg){
        Toast toast = Toast.makeText(tCtx, "Total : "+strMsg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, Constant.xOffSet, Constant.yOffSet);
        toast.show();
    }
}
