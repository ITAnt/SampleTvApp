package com.tomishi.sampletvapp.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v17.leanback.widget.Presenter;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tomishi.sampletvapp.R;

public class PhotoItemPresenter extends Presenter {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        ImageView view = new ImageView(parent.getContext());
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);

        Context context = parent.getContext();
        int width = context.getResources().getDimensionPixelSize(R.dimen.card_item_width);
        int height = context.getResources().getDimensionPixelSize(R.dimen.card_item_height);
        view.setLayoutParams(new ViewGroup.LayoutParams(width, height));

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        Integer id = (Integer)item;
        ImageView view = (ImageView)viewHolder.view;

        Context context = view.getContext();
        view.setImageDrawable(context.getDrawable(id));
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }
}
