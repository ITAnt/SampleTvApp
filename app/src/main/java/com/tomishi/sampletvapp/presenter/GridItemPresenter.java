package com.tomishi.sampletvapp.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v17.leanback.widget.Presenter;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tomishi.sampletvapp.R;

public class GridItemPresenter extends Presenter {

    private  Context mContext;

    public GridItemPresenter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        TextView view = new TextView(parent.getContext());
        Resources res = mContext.getResources();
        int width = res.getDimensionPixelSize(R.dimen.grid_item_width);
        int height = res.getDimensionPixelSize(R.dimen.grid_item_height);
        view.setLayoutParams(new ViewGroup.LayoutParams(width, height));
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.setBackgroundColor(res.getColor(R.color.default_background));
        view.setTextColor(Color.WHITE);
        view.setGravity(Gravity.CENTER);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        ((TextView) viewHolder.view).setText((String) item);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

    private GridItemPresenter() {
    }
}