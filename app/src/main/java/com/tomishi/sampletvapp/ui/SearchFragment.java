package com.tomishi.sampletvapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.ObjectAdapter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;

import com.tomishi.sampletvapp.data.VideoProvider;
import com.tomishi.sampletvapp.model.Video;
import com.tomishi.sampletvapp.presenter.CardItemPresenter;

public class SearchFragment extends android.support.v17.leanback.app.SearchFragment
        implements android.support.v17.leanback.app.SearchFragment.SearchResultProvider {

    private ArrayObjectAdapter mRowsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        setSearchResultProvider(this);

        setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                      RowPresenter.ViewHolder rowViewHolder, Row row) {
                if (item instanceof Video) {
                    Video video = (Video)item;
                    Intent intent = new Intent(getActivity(), VideoDetailsActivity.class);
                    intent.putExtra(VideoDetailsActivity.VIDEO, video);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public ObjectAdapter getResultsAdapter() {
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new CardItemPresenter());
        listRowAdapter.addAll(0, VideoProvider.getVideos());

        HeaderItem header = new HeaderItem("Search results");
        mRowsAdapter.add(new ListRow(header, listRowAdapter));
        return mRowsAdapter;
    }

    @Override
    public boolean onQueryTextChange(String newQuery) {
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }
}
