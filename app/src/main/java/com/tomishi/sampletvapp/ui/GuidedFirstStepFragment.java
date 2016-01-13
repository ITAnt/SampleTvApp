package com.tomishi.sampletvapp.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.GuidedStepFragment;
import android.support.v17.leanback.widget.GuidanceStylist;
import android.support.v17.leanback.widget.GuidedAction;

import com.tomishi.sampletvapp.R;

import java.util.List;

public class GuidedFirstStepFragment extends GuidedStepFragment {
    private static final int ACTION_CONTINUE = 0;
    private static final int ACTION_BACK = 1;

    @NonNull
    @Override
    public GuidanceStylist.Guidance onCreateGuidance(Bundle savedInstanceState) {
        String title = "Title";
        String breadcrumb = "Breadcrumb";
        String description = "Description";
        Drawable icon = getActivity().getDrawable(R.mipmap.ic_launcher);

        return new GuidanceStylist.Guidance(title, description, breadcrumb, icon);
    }

    @Override
    public void onCreateActions(@NonNull List actions, Bundle savedInstanceState) {
        addAction(actions, ACTION_CONTINUE, "Continue", "Go to SecondStepFragment");
        addAction(actions, ACTION_BACK, "Cancel", "Go back");
    }

    @Override
    public void onGuidedActionClicked(GuidedAction action) {

        switch ((int) action.getId()){
            case ACTION_CONTINUE:
                GuidedStepFragment.add(getFragmentManager(), new GuidedSecondStepFragment());
                break;
            case ACTION_BACK:
                getActivity().finish();
                break;
            default:
                break;
        }
    }

    private void addAction(List actions, long id, String title, String desc) {
        actions.add(new GuidedAction.Builder()
                .id(id)
                .title(title)
                .description(desc)
                .build());
    }
}
