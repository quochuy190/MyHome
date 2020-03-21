package com.vn.myhome.untils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 06-March-2020
 * Time: 11:38
 * Version: 1.0
 */
public class Utils {
    public static int get_screen_height(Activity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return height;
    }
    public static int get_screen_width(Activity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return height;
    }
}
