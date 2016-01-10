package com.tomishi.sampletvapp.ui;


import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.BaseCardView;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.tomishi.sampletvapp.R;
import com.tomishi.sampletvapp.data.VideoProvider;
import com.tomishi.sampletvapp.manager.PicassoBackgroundManager;
import com.tomishi.sampletvapp.model.Photo;
import com.tomishi.sampletvapp.model.Video;
import com.tomishi.sampletvapp.presenter.CardItemPresenter;
import com.tomishi.sampletvapp.presenter.GridItemPresenter;
import com.tomishi.sampletvapp.presenter.PhotoItemPresenter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BrowseFragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private ArrayObjectAdapter mRowsAdapter;
    private PicassoBackgroundManager mBackgroundManager;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);

        setupUIElements();

        loadRows();

        setupEventListeners();

        mBackgroundManager = new PicassoBackgroundManager(getActivity());
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

        int index = 0;
        index += loadPhotoItemRow(index);
        index += loadMovieItemRow(index);
        index += loadGridItemRow(index);
        index += loadCardItemRow(index);

        /* set */
        setAdapter(mRowsAdapter);
    }

    private int loadGridItemRow(int index) {
        HeaderItem header = new HeaderItem(index, "GridItem");

        GridItemPresenter presenter = new GridItemPresenter(getActivity().getApplicationContext());
        ArrayObjectAdapter adapter = new ArrayObjectAdapter(presenter);
        adapter.add("ITEM 1");
        adapter.add("ITEM 2");
        adapter.add("ITEM 3");

        mRowsAdapter.add(new ListRow(header, adapter));

        return 1;
    }

    private int loadCardItemRow(int startIndex) {
        int index = startIndex;
        HeaderItem header1 = new HeaderItem(index++, "CardItem(TYPE_MAIN_ONLY)");
        ArrayObjectAdapter adapter1 = new ArrayObjectAdapter(
                new CardItemPresenter(BaseCardView.CARD_TYPE_MAIN_ONLY)
        );

        HeaderItem header2 = new HeaderItem(index++, "CardItem(TYPE_INFO_OVER)");
        ArrayObjectAdapter adapter2 = new ArrayObjectAdapter(
                new CardItemPresenter(BaseCardView.CARD_TYPE_INFO_OVER)
        );

        HeaderItem header3 = new HeaderItem(index++, "CardItem(TYPE_INFO_UNDER)");
        ArrayObjectAdapter adapter3 = new ArrayObjectAdapter(
                new CardItemPresenter(BaseCardView.CARD_TYPE_INFO_UNDER)
        );

        List<Video> videos = VideoProvider.getDummpyVideos();
        adapter1.addAll(0, videos);
        adapter2.addAll(0, videos);
        adapter3.addAll(0, videos);

        mRowsAdapter.add(new ListRow(header1, adapter1));
        mRowsAdapter.add(new ListRow(header2, adapter2));
        mRowsAdapter.add(new ListRow(header3, adapter3));

        return index - startIndex;
    }

    private int loadPhotoItemRow(int index) {
        HeaderItem header = new HeaderItem(index, "PhotoItem");

        ArrayObjectAdapter adapter = new ArrayObjectAdapter(new PhotoItemPresenter());
        adapter.add(new Photo(R.drawable.photo1));
        adapter.add(new Photo(R.drawable.photo2));
        adapter.add(new Photo(R.drawable.photo3));

        mRowsAdapter.add(new ListRow(header, adapter));

        return 1;
    }

    private int loadMovieItemRow(int index) {
        HeaderItem header = new HeaderItem(index, "VideoItem");
        ArrayObjectAdapter adapter = new ArrayObjectAdapter(new CardItemPresenter());

        List<Video> videos = VideoProvider.getVideos();
        adapter.addAll(0, videos);

        mRowsAdapter.add(new ListRow(header, adapter));

        return 1;
    }

    private void setupEventListeners() {
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                    RowPresenter.ViewHolder rowViewHolder, Row row) {

            Log.i(TAG, "onItemSelected");
            if (item instanceof Photo) {
                mBackgroundManager.updateBackground(((Photo)item).getId());
            } else {
                mBackgroundManager.updateBackground(0);
            }
        }
    }
}
