package com.tomishi.sampletvapp.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.DetailsOverviewRowPresenter;
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
import com.tomishi.sampletvapp.util.Utils;

public class VideoDetailsFragment extends DetailsFragment {

    private String TAG = VideoDetailsFragment.class.getSimpleName();
    private Video mSelectedVideo;
    private ArrayObjectAdapter mAdapter;
    private BackgroundManager mBackgroundManager;

    private static final int TYPE_PLAY = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate DetailsFragment");
        super.onCreate(savedInstanceState);

        mSelectedVideo = (Video)getActivity().getIntent()
                .getSerializableExtra(VideoDetailsActivity.VIDEO);

        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());

        if (mSelectedVideo != null) {
            setupAdapter();

            setupDetailRow();
            setupListRow();

            setOnItemViewClickedListener(new ItemViewClickedListener());
        }
    }

    private void setupAdapter() {
        ClassPresenterSelector classPresenterSelector = new ClassPresenterSelector();

        Presenter presenter;
        if (mSelectedVideo.getId() % 2 == 0) {
            FullWidthDetailsOverviewRowPresenter detailPresenter =
                    new FullWidthDetailsOverviewRowPresenter(new VideoDetailsDescriptionPresenter());
            detailPresenter.setInitialState(FullWidthDetailsOverviewRowPresenter.STATE_HALF);
            presenter = detailPresenter;
        } else {
            presenter = new DetailsOverviewRowPresenter(new VideoDetailsDescriptionPresenter());
        }

        classPresenterSelector.addClassPresenter(DetailsOverviewRow.class, presenter);
        classPresenterSelector.addClassPresenter(ListRow.class, new ListRowPresenter());

        mAdapter = new ArrayObjectAdapter(classPresenterSelector);
        setAdapter(mAdapter);
    }

    private void setupDetailRow() {
        DetailsOverviewRow row = new DetailsOverviewRow(mSelectedVideo);

        // setup logo
        if (mSelectedVideo.getResourceId() > 0) {
            Context context = getActivity().getBaseContext();
            String videoPath = Utils.getFilePath(context, mSelectedVideo.getResourceId());
            new LogoTask(getActivity().getBaseContext(), row).execute(videoPath);
        }

        // setup action list
        SparseArrayObjectAdapter sparseArrayObjectAdapter = new SparseArrayObjectAdapter();
        sparseArrayObjectAdapter.set(0, new Action(TYPE_PLAY, "Play Video"));
        for (int i = 1; i < 5; i++){
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

    private class LogoTask extends AsyncTask<String, Void, Bitmap> {
        private DetailsOverviewRow mDetailRow;
        private Context mContext;

        public LogoTask(Context context, DetailsOverviewRow detail) {
            mContext = context;
            mDetailRow = detail;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String videoPath = params[0];
            Log.d(TAG, "ThumbTask doInBackground:" + videoPath);

            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(mContext, Uri.parse(videoPath));
            Bitmap bitmap = retriever.getFrameAtTime();

            // resize
            int width = 300;
            int height = 300;
            int srcW = bitmap.getWidth();
            int srcH = bitmap.getHeight();
            if (srcW > srcH) {
                height = srcH * width / srcW;
            } else {
                width = srcW * height / srcH;
            }
            return ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }

        protected void onPostExecute(Bitmap result) {
            Log.d(TAG, "ThumbTask onPostExecute");
            mDetailRow.setImageBitmap(mContext, result);
            mBackgroundManager.setBitmap(result);
        }
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                   RowPresenter.ViewHolder rowViewHolder, Row row) {
            if (item instanceof Action) {
                Log.i(TAG, "Action item is selected");
                Action action = (Action)item;
                if (action.getId() == TYPE_PLAY) {
                    playVideo(mSelectedVideo);
                }

            } else if (item instanceof Video) {
                Log.i(TAG, "Video item is selected ");
                Video video = (Video)item;
                playVideo(video);
            }
        }

        private void playVideo(Video video) {
            if (video.getResourceId() > 0) {
                Intent intent = new Intent(getActivity(), PlaybackOverlayActivity.class);
                intent.putExtra(VideoDetailsActivity.VIDEO, video);
                startActivity(intent);
            }
        }
    }
}
