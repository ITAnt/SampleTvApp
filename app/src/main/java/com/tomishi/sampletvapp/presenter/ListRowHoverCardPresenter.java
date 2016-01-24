package com.tomishi.sampletvapp.presenter;

import android.support.v17.leanback.widget.ListRowHoverCardView;
import android.support.v17.leanback.widget.Presenter;
import android.view.ViewGroup;

import com.tomishi.sampletvapp.model.Video;

public class ListRowHoverCardPresenter extends Presenter {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new ViewHolder(new ListRowHoverCardView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        if (item instanceof Video) {
            ListRowHoverCardView cardView = (ListRowHoverCardView) viewHolder.view;
            Video video = (Video) item;

            cardView.setTitle(video.getTitle());
            cardView.setDescription(video.getDescription());
        }
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }
}
