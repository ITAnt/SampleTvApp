package com.tomishi.sampletvapp.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.RowHeaderPresenter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tomishi.sampletvapp.R;
import com.tomishi.sampletvapp.model.IconHeaderItem;

public class IconHeaderItemPresenter  extends RowHeaderPresenter {

    private float mUnselectedAlpha;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        mUnselectedAlpha = viewGroup.getResources()
                .getFraction(R.fraction.lb_browse_header_unselect_alpha, 1, 1);
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.icon_header_item, null);
        setViewAlpha(view, 0);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object o) {
        IconHeaderItem iconHeaderItem = (IconHeaderItem) ((ListRow) o).getHeaderItem();
        View rootView = viewHolder.view;
        rootView.setFocusable(true);
        
        ImageView iconView = (ImageView) rootView.findViewById(R.id.header_icon);
        int iconResId = iconHeaderItem.getIconResId();
        if( iconResId != IconHeaderItem.ICON_NONE) { // Show icon only when it is set.
            Drawable icon = rootView.getResources().getDrawable(iconResId, null);
            iconView.setImageDrawable(icon);
        }

        TextView label = (TextView) rootView.findViewById(R.id.header_label);
        label.setText(iconHeaderItem.getName());
    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        // no op
    }

    @Override
    protected void onSelectLevelChanged(RowHeaderPresenter.ViewHolder holder) {
        setViewAlpha(holder.view, holder.getSelectLevel());
    }

    private void setViewAlpha(View view, float selectedLevel) {
        view.setAlpha(mUnselectedAlpha + selectedLevel * (1.0f - mUnselectedAlpha));
    }

}
