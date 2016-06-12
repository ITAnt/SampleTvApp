package com.tomishi.sampletvapp.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.app.RowsFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.PageRow;
import android.support.v17.leanback.widget.Row;

import com.tomishi.sampletvapp.data.VideoProvider;
import com.tomishi.sampletvapp.model.Video;
import com.tomishi.sampletvapp.presenter.CardItemPresenter;

import java.util.List;

public class PageBrowseFragment extends BrowseFragment {
    private static final long HEADER_ID_1 = 1;
    private static final String HEADER_NAME_1 = "Fragment 1";
    private static final long HEADER_ID_2 = 2;
    private static final String HEADER_NAME_2 = "Fragment 2";

    private ArrayObjectAdapter mRowsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();

        getMainFragmentRegistry().registerFragment(PageRow.class, new FragmentFactory() {
            @Override
            public Fragment createFragment(Object rowObj) {
                Row row = (Row) rowObj;
                if (row.getHeaderItem().getId() == HEADER_ID_1) {
                    return new SampleFragment1();
                } else if (row.getHeaderItem().getId() == HEADER_ID_2) {
                    return new SampleFragment2();
                }
                throw new IllegalArgumentException(String.format("Invalid row %s", rowObj));
            }
        });
    }

    private void loadData() {
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        setAdapter(mRowsAdapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                createRows();
                startEntranceTransition();
            }
        }, 1000);
    }

    private void createRows() {
        HeaderItem headerItem1 = new HeaderItem(HEADER_ID_1, HEADER_NAME_1);
        PageRow pageRow1 = new PageRow(headerItem1);
        HeaderItem headerItem2 = new HeaderItem(HEADER_ID_2, HEADER_NAME_2);
        PageRow pageRow2 = new PageRow(headerItem2);
        mRowsAdapter.add(pageRow1);
        mRowsAdapter.add(pageRow2);
    }

    public static class SampleFragment1 extends RowsFragment {
        private final ArrayObjectAdapter mRowsAdapter;

        public SampleFragment1() {
            mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
            setAdapter(mRowsAdapter);
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            createRows();
            getMainFragmentAdapter().getFragmentHost().notifyDataReady(getMainFragmentAdapter());
        }

        private void createRows() {
            HeaderItem header1 = new HeaderItem(0, "Row1");
            ArrayObjectAdapter adapter1 = new ArrayObjectAdapter(new CardItemPresenter());

            HeaderItem header2 = new HeaderItem(1, "Row2");
            ArrayObjectAdapter adapter2 = new ArrayObjectAdapter(new CardItemPresenter());

            List<Video> videos = VideoProvider.getDummpyVideos();
            adapter1.addAll(0, videos);
            adapter2.addAll(0, videos);

            mRowsAdapter.add(new ListRow(header1, adapter1));
            mRowsAdapter.add(new ListRow(header2, adapter2));
        }
    }

    public static class SampleFragment2 extends VerticalGridFragment implements MainFragmentAdapterProvider {
        private MainFragmentAdapter mMainFragmentAdapter = new MainFragmentAdapter(this);

        @Override
        public MainFragmentAdapter getMainFragmentAdapter() {
            return mMainFragmentAdapter;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getMainFragmentAdapter().getFragmentHost().notifyDataReady(getMainFragmentAdapter());
        }
    }
}

