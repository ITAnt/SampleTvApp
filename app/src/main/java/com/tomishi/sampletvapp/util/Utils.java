package com.tomishi.sampletvapp.util;

import android.content.Context;

public class Utils {
    public static String getFilePath(Context context, int resourceId) {
        return "android.resource://" + context.getPackageName() + "/" + resourceId;
    }
}
