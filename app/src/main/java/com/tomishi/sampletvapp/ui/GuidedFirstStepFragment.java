package com.tomishi.sampletvapp.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.GuidedStepFragment;
import android.support.v17.leanback.widget.GuidanceStylist;
import android.support.v17.leanback.widget.GuidedAction;
import android.support.v17.leanback.widget.GuidedDatePickerAction;
import android.text.TextUtils;

import com.tomishi.sampletvapp.R;

import java.util.LinkedList;
import java.util.List;

public class GuidedFirstStepFragment extends GuidedStepFragment {
    private static final int ACTION_CONTINUE = 0;
    private static final int ACTION_BACK = 1;
    private static final int ACTION_CONTINUE_SUB = 3;
    private static final int ACTION_EDITABLE_ITEM = 4;
    private static final int ACTION_EDITABLE_DESC_ITEM = 5;
    private static final int ACTION_DATE = 6;
    private static final int ACTION_CONFIRM = 6;

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

        // add new action which has sub actions
        GuidedAction action = createAction(ACTION_CONTINUE_SUB, "Menu(has sub)", "description");
        List<GuidedAction> subActions = new LinkedList<>();
        subActions.add(createAction(ACTION_SUB_MENU1, "Sub Menu 1", "sub menu 1"));
        subActions.add(createAction(ACTION_SUB_MENU2, "Sub Menu 2", "sub menu 2"));
        action.setSubActions(subActions);
        actions.add(action);

        // add new action which description is editable
        GuidedAction actionEdit = new GuidedAction.Builder(getActivity())
                .id(ACTION_EDITABLE_ITEM)
                .title("Editable item")
                .editTitle("")
                .description("description")
                .editDescription("edit description")
                .editable(true)
                .build();
        actions.add(actionEdit);

        GuidedAction actionEditDesc = new GuidedAction.Builder(getActivity())
                .id(ACTION_EDITABLE_ITEM)
                .title("Editable description item")
                .editTitle("")
                .description("description")
                .descriptionEditable(true)
                .build();
        actions.add(actionEditDesc);

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
    public void onCreateButtonActions(@NonNull List<GuidedAction> actions, Bundle savedInstanceState) {
        super.onCreateButtonActions(actions, savedInstanceState);
        addAction(actions, ACTION_CONFIRM, "Confirm", "");
    }

    @Override
    public void onGuidedActionClicked(GuidedAction action) {

        switch ((int) action.getId()){
            case ACTION_CONTINUE:
                GuidedStepFragment.add(getFragmentManager(), new GuidedSecondStepFragment());
                break;
            case ACTION_BACK:
            case ACTION_CONFIRM:
                getActivity().finish();
                break;
            default:
                break;
        }
    }

    @Override
    public long onGuidedActionEditedAndProceed(GuidedAction action) {
        //return super.onGuidedActionEditedAndProceed(action);
        if (action.getId() == ACTION_EDITABLE_ITEM) {
            CharSequence title = action.getEditTitle();
            if (!TextUtils.isEmpty(title)) {
                action.setTitle(title);
            }
        }
        return GuidedAction.ACTION_ID_CURRENT;
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
