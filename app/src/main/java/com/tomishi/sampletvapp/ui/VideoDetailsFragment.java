package com.tomishi.sampletvapp.ui;

import android.os.Bundle;
import android.support.v17.leanback.app.DetailsFragment;
import android.util.Log;

import com.tomishi.sampletvapp.model.Video;

public class VideoDetailsFragment extends DetailsFragment {

    private String TAG = VideoDetailsFragment.class.getSimpleName();
    private Video mSelectedVideo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate DetailsFragment");
        super.onCreate(savedInstanceState);

        mSelectedVideo = (Video)getActivity().getIntent()
                .getSerializableExtra(VideoDetailsActivity.VIDEO);

        if (mSelectedVideo != null) {

        }
    }
}
