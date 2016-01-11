package com.tomishi.sampletvapp.ui;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.VideoView;

import com.tomishi.sampletvapp.R;
import com.tomishi.sampletvapp.model.Video;
import com.tomishi.sampletvapp.util.Utils;

public class PlaybackOverlayActivity extends Activity {

    private VideoView mVideoView;

    private int mPosition;
    private long mStartTimeMillis;
    private long mDuration = -1;
    private String TAG = PlaybackOverlayActivity.class.getSimpleName();
    private LeanbackPlaybackState mPlaybackState = LeanbackPlaybackState.IDLE;

    public int getPosition() {
        return mPosition;
    }

    public enum LeanbackPlaybackState {
        PLAYING, PAUSED, IDLE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback_overlay);

        setupVideoView();
    }

    private void setupVideoView() {
        mVideoView = (VideoView) findViewById(R.id.videoView);
        mVideoView.setFocusable(false);
        mVideoView.setFocusableInTouchMode(false);

        Video video = (Video) getIntent().getSerializableExtra(VideoDetailsActivity.VIDEO);
        setVideoPath(Utils.getFilePath(this, video.getResourceId()));
    }

    public void setVideoPath(String videoPath) {
        setPosition(0);
        mVideoView.setVideoPath(videoPath);
        mStartTimeMillis = 0;
        mDuration = Utils.getDuration(this, videoPath);
        Log.i(TAG, videoPath + ": duration/" + mDuration);
    }

    private void stopPlayback() {
        if (mVideoView != null) {
            mVideoView.stopPlayback();
        }
    }

    private void setPosition(int position) {
        if (position > mDuration) {
            mPosition = (int) mDuration;
        } else if (position < 0) {
            mPosition = 0;
            mStartTimeMillis = System.currentTimeMillis();
        } else {
            mPosition = position;
        }
        mStartTimeMillis = System.currentTimeMillis();
        Log.d(TAG, "position set to " + mPosition);
    }

    public void playPause(boolean isPlay) {
        if (mPlaybackState == LeanbackPlaybackState.IDLE) {
            /* Callbacks for mVideoView */
            setupCallbacks();
        }

        if (isPlay && mPlaybackState != LeanbackPlaybackState.PLAYING) {
            mPlaybackState = LeanbackPlaybackState.PLAYING;
            if (mPosition > 0) {
                mVideoView.seekTo(mPosition);
            }
            mVideoView.start();
            mStartTimeMillis = System.currentTimeMillis();
        } else {
            mPlaybackState = LeanbackPlaybackState.PAUSED;
            int timeElapsedSinceStart = (int) (System.currentTimeMillis() - mStartTimeMillis);
            setPosition(mPosition + timeElapsedSinceStart);
            mVideoView.pause();
        }
    }

    private void setupCallbacks() {
        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mVideoView.stopPlayback();
                mPlaybackState = LeanbackPlaybackState.IDLE;
                return false;
            }
        });

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (mPlaybackState == LeanbackPlaybackState.PLAYING) {
                    mVideoView.start();
                }
            }
        });

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mPlaybackState = LeanbackPlaybackState.IDLE;
            }
        });
    }
}
