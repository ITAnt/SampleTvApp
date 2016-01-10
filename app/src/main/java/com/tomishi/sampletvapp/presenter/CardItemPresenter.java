package com.tomishi.sampletvapp.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v17.leanback.widget.BaseCardView;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.util.Log;
import android.view.ViewGroup;

import com.tomishi.sampletvapp.R;
import com.tomishi.sampletvapp.model.Video;

public class CardItemPresenter extends Presenter {

    private static final String TAG = CardItemPresenter.class.getSimpleName();

    private int mCardType = BaseCardView.CARD_TYPE_INFO_UNDER_WITH_EXTRA;
    private VideoThumbnailTask mThumbTask;

    public CardItemPresenter() {
        super();
    }
    public CardItemPresenter(int cardType) {
        super();
        mCardType = cardType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        Log.d(TAG, "onCreateViewHolder");
        Context context = parent.getContext();

        ImageCardView cardView = new ImageCardView(context);
        cardView.setCardType(mCardType);

        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        cardView.setBackgroundColor(context.getResources().getColor(R.color.fastlane_background));

        int bgColor = mCardType == BaseCardView.CARD_TYPE_INFO_OVER ?
                R.color.default_background_trans : R.color.default_background;
        cardView.setInfoAreaBackgroundColor(context.getResources().getColor(bgColor));

        int width = context.getResources().getDimensionPixelSize(R.dimen.card_item_width);
        int height = context.getResources().getDimensionPixelSize(R.dimen.card_item_height);
        cardView.setMainImageDimensions(width, height);
        cardView.setMainImage(context.getResources().getDrawable(R.drawable.default_video));

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        Log.d(TAG, "onBindViewHolder");
        Video video = (Video) item;
        ImageCardView cardView = (ImageCardView)viewHolder.view;

        cardView.setTitleText(video.getTitle());
        cardView.setContentText(video.getStudio());

        if (video.getResourceId() > 0) {
            mThumbTask = new VideoThumbnailTask(cardView);
            String videoPath = "android.resource://" + cardView.getContext().getPackageName() + "/" + video.getResourceId();
            mThumbTask.execute(videoPath);
        }
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        Log.d(TAG, "onUnbindViewHolder");
        if (mThumbTask != null && mThumbTask.getStatus() != AsyncTask.Status.FINISHED) {
            Log.d(TAG, "try cancel VideoThumbnailTask");
            mThumbTask.cancel(true);
        }
    }

    @Override
    public void onViewAttachedToWindow(Presenter.ViewHolder viewHolder) {
        // TO DO
    }

    private class VideoThumbnailTask extends AsyncTask<String, Void, Bitmap> {
        private final int mWidth;
        private final int mHeight;
        private ImageCardView mCardView;
        private Context mContext;

        public VideoThumbnailTask(ImageCardView view) {
            mCardView = view;
            mContext = view.getContext();
            mWidth = mContext.getResources().getDimensionPixelSize(R.dimen.card_item_width);
            mHeight = mContext.getResources().getDimensionPixelSize(R.dimen.card_item_height);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String videoPath = params[0];
            Log.d(TAG, "ThumbTask doInBackground:" + videoPath);

            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(mContext, Uri.parse(videoPath));
            Bitmap bitmap = retriever.getFrameAtTime();

            // resize
            return ThumbnailUtils.extractThumbnail(bitmap, mWidth, mHeight, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }

        protected void onPostExecute(Bitmap result) {
            Log.d(TAG, "ThumbTask onPostExecute");
            mCardView.setMainImage(new BitmapDrawable(mCardView.getResources(), result));
        }
    }
}
