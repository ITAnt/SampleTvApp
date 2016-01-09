package com.tomishi.sampletvapp.ui;


import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.BaseCardView;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.tomishi.sampletvapp.R;
import com.tomishi.sampletvapp.model.Video;
import com.tomishi.sampletvapp.presenter.CardItemPresenter;
import com.tomishi.sampletvapp.presenter.GridItemPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BrowseFragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private ArrayObjectAdapter mRowsAdapter;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);

        setupUIElements();

        loadRows();
    }

    private void setupUIElements() {
        // setBadgeDrawable(getActivity().getResources().getDrawable(R.drawable.videos_by_google_banner));
        setTitle("Sample TV App");

        // over title
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        // set fastLane (or headers) background color
        setBrandColor(getResources().getColor(R.color.fastlane_background));
        // set search icon color
        setSearchAffordanceColor(getResources().getColor(R.color.search_opaque));
    }

    private void loadRows() {
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        loadGridItemRow();
        loadCardItemRow();

        /* set */
        setAdapter(mRowsAdapter);
    }

    private void loadGridItemRow() {
        HeaderItem header = new HeaderItem(0, "GridItem");

        GridItemPresenter presenter = new GridItemPresenter(getActivity().getApplicationContext());
        ArrayObjectAdapter adapter = new ArrayObjectAdapter(presenter);
        adapter.add("ITEM 1");
        adapter.add("ITEM 2");
        adapter.add("ITEM 3");

        mRowsAdapter.add(new ListRow(header, adapter));
    }

    private void loadCardItemRow() {
        HeaderItem header1 = new HeaderItem(1, "CardItem(TYPE_MAIN_ONLY)");
        ArrayObjectAdapter adapter1 = new ArrayObjectAdapter(
                new CardItemPresenter(BaseCardView.CARD_TYPE_MAIN_ONLY)
        );

        HeaderItem header2 = new HeaderItem(2, "CardItem(TYPE_INFO_OVER)");
        ArrayObjectAdapter adapter2 = new ArrayObjectAdapter(
                new CardItemPresenter(BaseCardView.CARD_TYPE_INFO_OVER)
        );

        HeaderItem header3 = new HeaderItem(3, "CardItem(TYPE_INFO_UNDER)");
        ArrayObjectAdapter adapter3 = new ArrayObjectAdapter(
                new CardItemPresenter(BaseCardView.CARD_TYPE_INFO_UNDER)
        );

        for (int i = 0; i < 10; i++) {
            Video video = new Video();
            video.setId(i);
            video.setTitle("title" + i);
            video.setStudio("studio" + i);
            adapter1.add(video);
            adapter2.add(video);
            adapter3.add(video);
        }

        mRowsAdapter.add(new ListRow(header1, adapter1));
        mRowsAdapter.add(new ListRow(header2, adapter2));
        mRowsAdapter.add(new ListRow(header3, adapter3));
    }
}
