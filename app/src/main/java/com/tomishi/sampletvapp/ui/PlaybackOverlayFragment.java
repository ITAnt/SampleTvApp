package com.tomishi.sampletvapp.ui;

import android.os.Bundle;
import android.util.Log;

import com.tomishi.sampletvapp.model.Video;

public class PlaybackOverlayFragment extends android.support.v17.leanback.app.PlaybackOverlayFragment {

    private static final String TAG = PlaybackOverlayFragment.class.getSimpleName();
    private Video mSelectedVideo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        mSelectedVideo = (Video) getActivity().getIntent().getSerializableExtra(VideoDetailsActivity.VIDEO);
    }
}
