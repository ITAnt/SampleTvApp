package com.tomishi.sampletvapp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.tomishi.sampletvapp.R;

import java.util.List;

public class GuidedStepActivity extends Activity {

    private static final String TAG = GuidedStepActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }
}
