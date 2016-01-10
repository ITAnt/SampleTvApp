package com.tomishi.sampletvapp.ui;

import android.os.Bundle;
import android.app.Activity;

import com.tomishi.sampletvapp.R;

public class VideoDetailsActivity extends Activity {

    public static final String VIDEO = "video";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);
    }

}
