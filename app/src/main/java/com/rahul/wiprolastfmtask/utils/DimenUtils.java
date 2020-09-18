package com.rahul.wiprolastfmtask.utils;

import android.util.DisplayMetrics;

import com.rahul.wiprolastfmtask.app.LastFMApplicationClass;

public class DimenUtils {

    public static int convertDpToPx(int dp) {
        return Math.round(dp * (LastFMApplicationClass.getContext().getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));

    }
}
