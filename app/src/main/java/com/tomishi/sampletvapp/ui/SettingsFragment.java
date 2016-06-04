package com.tomishi.sampletvapp.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v14.preference.PreferenceFragment;
import android.support.v17.preference.LeanbackPreferenceFragment;
import android.support.v17.preference.LeanbackSettingsFragment;
import android.support.v7.preference.DialogPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.widget.Toast;

import com.tomishi.sampletvapp.R;

import java.util.Arrays;
import java.util.Stack;

public class SettingsFragment extends LeanbackSettingsFragment implements DialogPreference.TargetFragment {

    private final Stack<Fragment> fragments = new Stack<Fragment>();

    @Override
    public void onPreferenceStartInitialScreen() {
        startPreferenceFragment(buildPreferenceFragment(R.xml.prefs, null));
    }

    @Override
    public boolean onPreferenceStartFragment(PreferenceFragment preferenceFragment,
                                             Preference preference) {
        return false;
    }

    @Override
    public boolean onPreferenceStartScreen(PreferenceFragment preferenceFragment,
                                           PreferenceScreen preferenceScreen) {
        PreferenceFragment frag = buildPreferenceFragment(R.xml.prefs, preferenceScreen.getKey());
        startPreferenceFragment(frag);
        return true;
    }

    @Override
    public Preference findPreference(CharSequence prefKey) {
        return ((PreferenceFragment) fragments.peek()).findPreference(prefKey);
    }

    private PreferenceFragment buildPreferenceFragment(int preferenceResId, String root) {
        PreferenceFragment fragment = new PrefFragment();
        Bundle args = new Bundle();
        args.putInt("preferenceResource", preferenceResId);
        args.putString("root", root);
        fragment.setArguments(args);
        return fragment;
    }

    private class PrefFragment extends LeanbackPreferenceFragment {

        @Override
        public void onCreatePreferences(Bundle bundle, String s) {
            String root = getArguments().getString("root", null);
            int prefResId = getArguments().getInt("preferenceResource");
            if (root == null) {
                addPreferencesFromResource(prefResId);
            } else {
                setPreferencesFromResource(prefResId, root);
            }
        }

        @Override
        public boolean onPreferenceTreeClick(Preference preference) {
            final String[] keys = {"prefs_wifi_connect_wps", "prefs_date", "prefs_time",
                    "prefs_date_time_use_timezone", "app_banner_sample_app", "pref_force_stop",
                    "pref_uninstall", "pref_more_info"};
            if (Arrays.asList(keys).contains(preference.getKey())) {
                Toast.makeText(getActivity(), "Implement your own action handler.", Toast.LENGTH_SHORT).show();
                return true;
            }
            return super.onPreferenceTreeClick(preference);
        }

        @Override
        public void onAttach(Context context) {
            fragments.push(this);
            super.onAttach(context);
        }

        @Override
        public void onDetach() {
            fragments.pop();
            super.onDetach();
        }
    }
}
