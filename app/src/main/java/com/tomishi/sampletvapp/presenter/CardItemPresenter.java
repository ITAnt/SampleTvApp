package com.tomishi.sampletvapp.presenter;

import android.content.Context;
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
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        Log.d(TAG, "onUnbindViewHolder");
    }

    @Override
    public void onViewAttachedToWindow(Presenter.ViewHolder viewHolder) {
        // TO DO
    }

}
