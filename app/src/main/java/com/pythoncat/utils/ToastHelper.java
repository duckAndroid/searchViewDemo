package com.pythoncat.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/12/14.
 */

public class ToastHelper {

    private static Toast t;

    public static void show(Context c, String text) {
        cancel(c);
        t = Toast.makeText(c, text, Toast.LENGTH_SHORT);
        t.show();
    }

    public static void cancel(Context c) {
        if (t != null)
            t.cancel();

    }
}
