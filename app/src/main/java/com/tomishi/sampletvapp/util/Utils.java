package com.tomishi.sampletvapp.util;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;

import java.util.HashMap;

public class Utils {
    public static String getFilePath(Context context, int resourceId) {
        return "android.resource://" + context.getPackageName() + "/" + resourceId;
    }

    public static long getDuration(Context context, String filePath) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(context, Uri.parse(filePath));
        return Long.parseLong(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
    }
}
