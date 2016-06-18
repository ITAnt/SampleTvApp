package com.tomishi.sampletvapp.util;

import android.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Helper class for the execution in the fragment.
 */
public class SetupActionHelper {
    /**
     * Executes the action of the given {@code actionId}.
     */
    public static void onActionClick(Fragment fragment, String category, int actionId) {
        OnActionClickListener listener = null;
        if (listener == null && fragment.getActivity() instanceof OnActionClickListener) {
            listener = (OnActionClickListener) fragment.getActivity();
        }
        if (listener != null) {
            listener.onActionClick(category, actionId);
        }
    }

    /**
     * Creates an {@link OnClickListener} to handle the action.
     */
    public static OnClickListener createOnClickListenerForAction(OnActionClickListener listener,
                                                                 String category, int actionId) {
        return new OnActionClickListenerForAction(listener, category, actionId);
    }

    private static class OnActionClickListenerForAction implements OnClickListener {
        private final OnActionClickListener mListener;
        private final String mCategory;
        private final int mActionId;

        OnActionClickListenerForAction(OnActionClickListener listener, String category,
                                       int actionId) {
            mListener = listener;
            mCategory = category;
            mActionId = actionId;
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onActionClick(mCategory, mActionId);
            }
        }
    }

    private SetupActionHelper() { }
}
