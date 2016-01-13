package com.tomishi.sampletvapp.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.support.app.recommendation.ContentRecommendation;
import android.telecom.VideoProfile;
import android.util.Log;

import com.tomishi.sampletvapp.R;
import com.tomishi.sampletvapp.data.VideoProvider;
import com.tomishi.sampletvapp.model.Video;
import com.tomishi.sampletvapp.ui.VideoDetailsActivity;
import com.tomishi.sampletvapp.util.Utils;

import java.util.List;
import java.util.zip.ZipEntry;

public class RecommendationService extends IntentService {
    private static final String TAG = RecommendationService.class.getSimpleName();

    public RecommendationService(String name) {
        super(name);
    }

    public RecommendationService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "onHandleIntent");

        List<Video> videos = VideoProvider.getVideos();
        Video video = videos.get(0);

        // This will be used to build up an object for your content recommendation that will be
        // shown on the TV home page along with other provider's recommendations.
        ContentRecommendation.Builder builder = new ContentRecommendation.Builder()
                .setBadgeIcon(R.mipmap.ic_launcher);

        builder.setIdTag("Video")
                .setTitle(video.getTitle())
                .setText(video.getStudio())
                .setContentIntentData(ContentRecommendation.INTENT_TYPE_ACTIVITY,
                        buildPendingIntent(video), 0, null);

        String videoPath = Utils.getFilePath(getApplicationContext(), video.getResourceId());
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(getApplicationContext(), Uri.parse(videoPath));
        Bitmap bitmap = retriever.getFrameAtTime();

        // resize
        int width = 300;
        int height = 300;
        int srcW = bitmap.getWidth();
        int srcH = bitmap.getHeight();
        if (srcW > srcH) {
            height = srcH * width / srcW;
        } else {
            width = srcW * height / srcH;
        }
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        builder.setContentImage(bitmap);


        // Create an object holding all the information used to recommend the content.
        ContentRecommendation rec = builder.build();
        Notification notification = rec.getNotificationObject(getApplicationContext());

        NotificationManager notificationManager = (NotificationManager)getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.cancel(1);
        notificationManager.notify(1, notification);

    }

    private Intent buildPendingIntent(Video video) {
        Intent detailsIntent = new Intent(this, VideoDetailsActivity.class);
        detailsIntent.putExtra(VideoDetailsActivity.VIDEO, video);

        return detailsIntent;
    }
}
