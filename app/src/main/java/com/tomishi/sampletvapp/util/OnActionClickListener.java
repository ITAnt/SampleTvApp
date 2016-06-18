package com.tomishi.sampletvapp.util;

/**
 * A listener for the action click.
 */
public interface OnActionClickListener {
    /**
     * Called when the action is clicked.
     *
     * @param category action category.
     * @param id action id.
     */
    void onActionClick(String category, int id);
}
