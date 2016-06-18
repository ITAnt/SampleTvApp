package com.tomishi.sampletvapp.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import com.tomishi.sampletvapp.R;

public class OnboardingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        Fragment fragment = new OnboardingFragment(this);
        getFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
    }
}
