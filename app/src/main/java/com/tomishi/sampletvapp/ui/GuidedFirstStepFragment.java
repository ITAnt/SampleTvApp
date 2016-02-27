package com.tomishi.sampletvapp.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.GuidedStepFragment;
import android.support.v17.leanback.widget.GuidanceStylist;
import android.support.v17.leanback.widget.GuidedAction;
import android.support.v17.leanback.widget.GuidedDatePickerAction;

import com.tomishi.sampletvapp.R;

import java.util.LinkedList;
import java.util.List;

public class GuidedFirstStepFragment extends GuidedStepFragment {
    private static final int ACTION_CONTINUE = 0;
    private static final int ACTION_BACK = 1;
    private static final int ACTION_CONTINUE_NEW = 2;
    private static final int ACTION_CONTINUE_SUB = 3;
    private static final int ACTION_PASSWOARD = 4;
    private static final int ACTION_DATE = 5;

    private static final int ACTION_SUB_MENU1 = 10;
    private static final int ACTION_SUB_MENU2 = 11;

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

        // add new action which go to next step internally
        addAction(actions, ACTION_CONTINUE_NEW, "Continue(New)", "Go with new transition");

        // add new action which has sub actions
        GuidedAction action = createAction(ACTION_CONTINUE_SUB, "Menu(has sub)", "description");
        List<GuidedAction> subActions = new LinkedList<>();
        subActions.add(createAction(ACTION_SUB_MENU1, "Sub Menu 1", "sub menu 1"));
        subActions.add(createAction(ACTION_SUB_MENU2, "Sub Menu 2", "sub menu 2"));
        action.setSubActions(subActions);
        actions.add(action);

        // add new action which description is editable
        GuidedAction actionPass = new GuidedAction.Builder(getActivity())
                .id(ACTION_PASSWOARD)
                .title("Password")
                .descriptionEditable(true)
                .build();
        actions.add(actionPass);

        // add new date action
        GuidedDatePickerAction actionDate = new GuidedDatePickerAction.Builder(getActivity()).
                id(ACTION_DATE)
                .title("Date")
                .description("set date") // not affected
                .date(System.currentTimeMillis())
                .build();
        actions.add(actionDate);
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
            case ACTION_CONTINUE_NEW:
                GuidedStepFragment.add(getFragmentManager(), new GuidedButtonActionFragment());
                break;
            default:
                break;
        }
    }

    private void addAction(List actions, long id, String title, String desc) {
        actions.add(createAction(id, title, desc));
    }

    private GuidedAction createAction(long id, String title, String desc) {
        return new GuidedAction.Builder()
                .id(id)
                .title(title)
                .description(desc)
                .build();
    }
}
