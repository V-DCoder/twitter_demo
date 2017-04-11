package com.bms.twitterapidemo.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by roshan on 07/04/17.
 */

public class Util {

    static ProgressDialog progressDialog;
    public static void showProgressBar(Context context, String title, String msg)
    {
        if(progressDialog==null)
         progressDialog = new ProgressDialog(context);
        if(progressDialog.isShowing())
            return;
        progressDialog.setTitle(title);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void dismissProgressBar()
    {
        if(progressDialog!=null&&progressDialog.isShowing())
            progressDialog.dismiss();
    }
    public static void updateProgressBar(String msg)
    {
        if(progressDialog!=null&&progressDialog.isShowing())
            progressDialog.setMessage(msg);
    }
}
