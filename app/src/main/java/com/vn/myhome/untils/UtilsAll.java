package com.vn.myhome.untils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 01-March-2020
 * Time: 10:22
 * Version: 1.0
 */
public class UtilsAll {

    public static int get_width(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        return width;
    }

    public static int get_height(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        return height;
    }
}
