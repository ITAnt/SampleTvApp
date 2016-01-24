package com.tomishi.sampletvapp.ui;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.BaseCardView;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowHoverCardView;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tomishi.sampletvapp.R;
import com.tomishi.sampletvapp.data.VideoProvider;
import com.tomishi.sampletvapp.manager.PicassoBackgroundManager;
import com.tomishi.sampletvapp.model.CustomListRow;
import com.tomishi.sampletvapp.model.IconHeaderItem;
import com.tomishi.sampletvapp.model.Photo;
import com.tomishi.sampletvapp.model.Video;
import com.tomishi.sampletvapp.presenter.CardItemPresenter;
import com.tomishi.sampletvapp.presenter.CustomListRowPresenter;
import com.tomishi.sampletvapp.presenter.GridItemPresenter;
import com.tomishi.sampletvapp.presenter.IconHeaderItemPresenter;
import com.tomishi.sampletvapp.presenter.ListRowHoverCardPresenter;
import com.tomishi.sampletvapp.presenter.PhotoItemPresenter;
import com.tomishi.sampletvapp.service.RecommendationService;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BrowseFragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private ArrayObjectAdapter mRowsAdapter;
    private PicassoBackgroundManager mBackgroundManager;
    private final int REQUEST_PERMISSION_RECORD_AUDIO = 0;

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

        setHeaderPresenterSelector(new PresenterSelector() {
            @Override
            public Presenter getPresenter(Object o) {
                return new IconHeaderItemPresenter();
            }
        });
    }

    private void loadRows() {
        ClassPresenterSelector selector = new ClassPresenterSelector();
        ListRowPresenter listRowPresenter = new ListRowPresenter();
        listRowPresenter.setHoverCardPresenterSelector(new PresenterSelector() {
            @Override
            public Presenter getPresenter(Object item) {
                return new ListRowHoverCardPresenter();
            }
        });
        selector.addClassPresenter(ListRow.class, listRowPresenter);
        selector.addClassPresenter(CustomListRow.class, new CustomListRowPresenter());
        mRowsAdapter = new ArrayObjectAdapter(selector);

        int index = 0;
        index += loadGridItemRow(index);
        index += loadPhotoItemRow(index);
        index += loadMovieItemRow(index);
        index += loadCardItemRow(index);

        /* set */
        setAdapter(mRowsAdapter);
    }

    private int loadGridItemRow(int index) {
        HeaderItem header = new IconHeaderItem(index, "GridItem", R.drawable.ic_add);

        GridItemPresenter presenter = new GridItemPresenter(getActivity().getApplicationContext());
        ArrayObjectAdapter adapter = new ArrayObjectAdapter(presenter);
        adapter.add("ErrorFragment");
        adapter.add("GuidedStepFragment");
        adapter.add("Recommendation");
        adapter.add("Spinner");
        adapter.add("VerticalGridFragment");

        mRowsAdapter.add(new ListRow(header, adapter));

        return 1;
    }

    private int loadCardItemRow(int startIndex) {
        int index = startIndex;
        HeaderItem header1 = new IconHeaderItem(index++, "CardItem(TYPE_MAIN_ONLY)", R.drawable.ic_play);
        ArrayObjectAdapter adapter1 = new ArrayObjectAdapter(
                new CardItemPresenter(BaseCardView.CARD_TYPE_MAIN_ONLY)
        );

        HeaderItem header2 = new IconHeaderItem(index++, "CardItem(TYPE_INFO_OVER)", R.drawable.ic_play);
        ArrayObjectAdapter adapter2 = new ArrayObjectAdapter(
                new CardItemPresenter(BaseCardView.CARD_TYPE_INFO_OVER)
        );

        HeaderItem header3 = new IconHeaderItem(index++, "CardItem(TYPE_INFO_UNDER)", R.drawable.ic_play);
        ArrayObjectAdapter adapter3 = new ArrayObjectAdapter(
                new CardItemPresenter(BaseCardView.CARD_TYPE_INFO_UNDER)
        );

        HeaderItem header4 = new IconHeaderItem(index++, "CustomListRow(2 Line)", R.drawable.ic_play);
        ArrayObjectAdapter adapter4 = new ArrayObjectAdapter(
                new CardItemPresenter(BaseCardView.CARD_TYPE_INFO_UNDER)
        );

        List<Video> videos = VideoProvider.getDummpyVideos();
        adapter1.addAll(0, videos);
        adapter2.addAll(0, videos);
        adapter3.addAll(0, videos);
        adapter4.addAll(0, videos);

        // ListRow
        mRowsAdapter.add(new ListRow(header1, adapter1));
        mRowsAdapter.add(new ListRow(header2, adapter2));
        mRowsAdapter.add(new ListRow(header3, adapter3));

        // CustomListRow
        mRowsAdapter.add(new CustomListRow(header4, adapter4, 2));

        return index - startIndex;
    }

    private int loadPhotoItemRow(int index) {
        HeaderItem header = new IconHeaderItem(index, "PhotoItem", R.drawable.ic_add);

        ArrayObjectAdapter adapter = new ArrayObjectAdapter(new PhotoItemPresenter());
        adapter.add(new Photo(R.drawable.photo1));
        adapter.add(new Photo(R.drawable.photo2));
        adapter.add(new Photo(R.drawable.photo3));

        mRowsAdapter.add(new ListRow(header, adapter));

        return 1;
    }

    private int loadMovieItemRow(int index) {
        HeaderItem header = new IconHeaderItem(index, "VideoItem", R.drawable.ic_play);
        ArrayObjectAdapter adapter = new ArrayObjectAdapter(new CardItemPresenter());

        List<Video> videos = VideoProvider.getVideos();
        adapter.addAll(0, videos);

        mRowsAdapter.add(new ListRow(header, adapter));

        return 1;
    }

    private void setupEventListeners() {
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
        setOnItemViewClickedListener(new ItemViewClickedListener());

        setOnSearchClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED) {

                    // check version to suppress warning for min api level
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(
                                new String[]{Manifest.permission.RECORD_AUDIO},
                                REQUEST_PERMISSION_RECORD_AUDIO);
                    } else {
                        // can't happen
                    }

                } else {
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_RECORD_AUDIO) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // launch SearchActivity
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            } else {
            }
        }
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

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                   RowPresenter.ViewHolder rowViewHolder, Row row) {
            if (item instanceof Video) {
                Video video = (Video)item;
                Intent intent = new Intent(getActivity(), VideoDetailsActivity.class);
                intent.putExtra(VideoDetailsActivity.VIDEO, video);
                startActivity(intent);
            } else if (item instanceof String) {
                if (item == "ErrorFragment") {
                    Intent intent = new Intent(getActivity(), ErrorActivity.class);
                    startActivity(intent);
                } else if (item == "GuidedStepFragment") {
                    Intent intent = new Intent(getActivity(), GuidedStepActivity.class);
                    startActivity(intent);
                } else if (item == "Recommendation") {
                    Intent intent = new Intent(getActivity(), RecommendationService.class);
                    getActivity().startService(intent);
                } else if (item == "Spinner") {
                    new ShowSpinnerTask().execute();
                } else if (item == "VerticalGridFragment") {
                    Intent intent = new Intent(getActivity(), VerticalGridActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

    private class ShowSpinnerTask extends AsyncTask<Void, Void, Void> {
        SpinnerFragment mSpinnerFragment;

        @Override
        protected void onPreExecute() {
            mSpinnerFragment = new SpinnerFragment();
            getFragmentManager().beginTransaction().add(R.id.main_browse_fragment, mSpinnerFragment).commit();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Do some background process here.
            // It just waits 5 sec in this Tutorial
            SystemClock.sleep(5000);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            getFragmentManager().beginTransaction().remove(mSpinnerFragment).commit();
        }
    }
}
