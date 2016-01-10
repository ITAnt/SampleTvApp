package com.tomishi.sampletvapp.ui;

import android.os.Bundle;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.SparseArrayObjectAdapter;
import android.util.Log;

import com.tomishi.sampletvapp.data.VideoProvider;
import com.tomishi.sampletvapp.model.Video;
import com.tomishi.sampletvapp.presenter.CardItemPresenter;
import com.tomishi.sampletvapp.presenter.VideoDetailsDescriptionPresenter;

public class VideoDetailsFragment extends DetailsFragment {

    private String TAG = VideoDetailsFragment.class.getSimpleName();
    private Video mSelectedVideo;
    private ArrayObjectAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate DetailsFragment");
        super.onCreate(savedInstanceState);

        mSelectedVideo = (Video)getActivity().getIntent()
                .getSerializableExtra(VideoDetailsActivity.VIDEO);

        if (mSelectedVideo != null) {
            setupAdapter();

            setupDetailRow();
            setupListRow();

            setOnItemViewClickedListener(new ItemViewClickedListener());
        }
    }

    private void setupAdapter() {
        ClassPresenterSelector classPresenterSelector = new ClassPresenterSelector();

        FullWidthDetailsOverviewRowPresenter detailPresenter =
                new FullWidthDetailsOverviewRowPresenter(new VideoDetailsDescriptionPresenter());
        detailPresenter.setInitialState(FullWidthDetailsOverviewRowPresenter.STATE_HALF);

        classPresenterSelector.addClassPresenter(DetailsOverviewRow.class, detailPresenter);
        classPresenterSelector.addClassPresenter(ListRow.class, new ListRowPresenter());

        mAdapter = new ArrayObjectAdapter(classPresenterSelector);
        setAdapter(mAdapter);
    }

    private void setupDetailRow() {
        DetailsOverviewRow row = new DetailsOverviewRow(mSelectedVideo);

        // setup action list
        SparseArrayObjectAdapter sparseArrayObjectAdapter = new SparseArrayObjectAdapter();
        for (int i = 0; i<10; i++){
            sparseArrayObjectAdapter.set(i, new Action(i, "label1", "label2"));
        }
        row.setActionsAdapter(sparseArrayObjectAdapter);

        mAdapter.add(row);
    }

    private void setupListRow() {
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new CardItemPresenter());
        listRowAdapter.addAll(0, VideoProvider.getDummpyVideos());

        HeaderItem header = new HeaderItem(0, "Related Videos");
        mAdapter.add(new ListRow(header, listRowAdapter));
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                   RowPresenter.ViewHolder rowViewHolder, Row row) {
            if (item instanceof Action) {
                Log.i(TAG, "selected action");
            }
        }
    }
}
