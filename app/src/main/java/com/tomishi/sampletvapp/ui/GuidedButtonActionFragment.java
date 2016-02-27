package com.tomishi.sampletvapp.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.GuidedStepFragment;
import android.support.v17.leanback.widget.GuidanceStylist;
import android.support.v17.leanback.widget.GuidedAction;

import com.tomishi.sampletvapp.R;

import java.util.LinkedList;
import java.util.List;

public class GuidedButtonActionFragment extends GuidedStepFragment {

    @NonNull
    @Override
    public GuidanceStylist.Guidance onCreateGuidance(Bundle savedInstanceState) {
        String title = "Test for button action";
        String breadcrumb = "Breadcrumb";
        String description = "Description";
        Drawable icon = getActivity().getDrawable(R.mipmap.ic_launcher);

        return new GuidanceStylist.Guidance(title, description, breadcrumb, icon);
    }

    @Override
    public void onCreateButtonActions(@NonNull List<GuidedAction> actions, Bundle savedInstanceState) {
        super.onCreateButtonActions(actions, savedInstanceState);

        actions.add(createAction(0, "OK", "description"));
        actions.add(createAction(0, "Cancel", "description"));

    }

    private GuidedAction createAction(long id, String title, String desc) {
        return new GuidedAction.Builder()
                .id(id)
                .title(title)
                .description(desc)
                .build();
    }
}
