package com.tomishi.sampletvapp.presenter;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;

import com.tomishi.sampletvapp.model.Video;

public class VideoDetailsDescriptionPresenter extends AbstractDetailsDescriptionPresenter {
    @Override
    protected void onBindDescription(ViewHolder viewHolder, Object item) {
        Video video = (Video)item;

        if (video != null) {
            viewHolder.getTitle().setText(video.getTitle());
            viewHolder.getSubtitle().setText(video.getStudio());
            viewHolder.getBody().setText(video.getDescription());
        }
    }
}
