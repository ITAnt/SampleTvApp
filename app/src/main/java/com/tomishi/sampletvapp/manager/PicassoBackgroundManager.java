package com.tomishi.sampletvapp.manager;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v17.leanback.app.BackgroundManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Timer;
import java.util.TimerTask;

public class PicassoBackgroundManager {
    private static final String TAG = PicassoBackgroundManager.class.getSimpleName();
    private static final int BACKGROUND_UPDATE_DELAY = 500;

    private final Activity mActivity;
    private final BackgroundManager mBackgroundManager;
    private final DisplayMetrics mMetrics;
    private final PicassoBackgroundManagerTarget mBackgroundTarget;
    private int mBackgroundResId = 0;
    private Timer mBackgroundTimer;

    // Handler attached with main thread
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public PicassoBackgroundManager(Activity activity) {
        mActivity = activity;
        mBackgroundManager = BackgroundManager.getInstance(activity);
        mBackgroundManager.attach(activity.getWindow());
        mBackgroundTarget = new PicassoBackgroundManagerTarget(mBackgroundManager);
        mMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    public void updateBackground(int resId) {
        Log.i(TAG, "updateBackground:" + resId);
        mBackgroundResId = resId;
        startUpdateTimer();
    }

    private void startUpdateTimer() {
        if (mBackgroundTimer != null) {
            mBackgroundTimer.cancel();
        }
        mBackgroundTimer = new Timer();
        /* set delay time to reduce too much background image loading process */
        mBackgroundTimer.schedule(new UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY);
    }

    private class UpdateBackgroundTask extends TimerTask {
        @Override
        public void run() {
            /* Here is TimerTask thread, not UI thread */
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                     /* Here is main (UI) thread */
                    update(mBackgroundResId);
                }
            });
        }
    }

    private void update(int resId) {
        try {
            if (resId == 0) {
                mBackgroundManager.setDrawable(null);
                return;
            }

            Picasso.with(mActivity)
                    .load(resId)
                    .resize(mMetrics.widthPixels, mMetrics.heightPixels)
                    .centerCrop()
                    .into(mBackgroundTarget);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * Copied from AOSP sample code.
     * Inner class1
     * Picasso target for updating default_background images
     */
    public class PicassoBackgroundManagerTarget implements Target {
        BackgroundManager mBackgroundManager;

        public PicassoBackgroundManagerTarget(BackgroundManager backgroundManager) {
            this.mBackgroundManager = backgroundManager;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
            this.mBackgroundManager.setBitmap(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable drawable) {
            this.mBackgroundManager.setDrawable(drawable);
        }

        @Override
        public void onPrepareLoad(Drawable drawable) {
            // Do nothing, default_background manager has its own transitions
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            PicassoBackgroundManagerTarget that = (PicassoBackgroundManagerTarget) o;

            if (!mBackgroundManager.equals(that.mBackgroundManager))
                return false;

            return true;
        }

        @Override
        public int hashCode() {
            return mBackgroundManager.hashCode();
        }
    }
}
