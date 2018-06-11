package com.abilix.myapp.view.dialog;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class BindViewHolder extends RecyclerView.ViewHolder {

    public View mBindView;
    private SparseArray<View> mViews;
    private CommonDialog mDialog;

    public BindViewHolder(View view) {
        super(view);
        this.mBindView = view;
        this.mViews = new SparseArray<>();
    }

    public BindViewHolder(View view, CommonDialog dialog) {
        super(view);
        this.mBindView = view;
        this.mViews = new SparseArray<>();
        this.mDialog = dialog;
    }

    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mBindView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public BindViewHolder addOnClickListener(@IdRes int viewId) {
        final View view = getView(viewId);
        if (view != null) {
            view.setClickable(true);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogViewInterface.OnViewClickListener clickListener = mDialog.getOnViewClickListener();
                    if (clickListener != null) {
                        clickListener.onViewClick(BindViewHolder.this, view, mDialog);
                    }
                }
            });
        }
        return this;
    }

    public BindViewHolder setText(@IdRes int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public BindViewHolder setText(@IdRes int viewId, @StringRes int strId) {
        TextView view = getView(viewId);
        view.setText(strId);
        return this;
    }

    public BindViewHolder setImageResource(@IdRes int viewId, @DrawableRes int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public BindViewHolder setBackgroundResource(@IdRes int viewId, @DrawableRes int resId) {
        View view = getView(viewId);
        view.setBackgroundResource(resId);
        return this;
    }

    public BindViewHolder setBackgroundColor(@IdRes int viewId, @ColorInt int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public BindViewHolder setTextColor(@IdRes int viewId, @ColorInt int color) {
        TextView view = getView(viewId);
        view.setTextColor(color);
        return this;
    }

    @IntDef({View.VISIBLE, View.INVISIBLE, View.GONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {}

    public BindViewHolder setVisibility(@IdRes int viewId, @Visibility int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    public BindViewHolder setProgress(@IdRes int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public BindViewHolder setProgress(@IdRes int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public BindViewHolder setRating(@IdRes int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public BindViewHolder setRating(@IdRes int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public BindViewHolder setTag(@IdRes int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public BindViewHolder setTag(@IdRes int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public BindViewHolder setChecked(@IdRes int viewId, boolean checked) {
        View view = getView(viewId);
        if (view instanceof Checkable) {
            ((Checkable) view).setChecked(checked);
        }
        return this;
    }

    public BindViewHolder setAdapter(@IdRes int viewId, Adapter adapter) {
        AdapterView view = getView(viewId);
        view.setAdapter(adapter);
        return this;
    }

}
