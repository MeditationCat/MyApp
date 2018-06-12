/*
 * Copyright (c) 2018. The Open Source Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.abilix.myapp.view.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.abilix.myapp.base.BaseDialogFragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CommonDialog extends BaseDialogFragment {

    private static final String KEY_ALERT_CONTROLLER = "CommonDialog:AlertController";
    protected AlertController mAlert;

    public CommonDialog() {
        mAlert = new AlertController();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mAlert = (AlertController) savedInstanceState.getSerializable(KEY_ALERT_CONTROLLER);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(KEY_ALERT_CONTROLLER, mAlert);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        DialogInterface.OnDismissListener dismissListener = mAlert.getOnDismissListener();
        if (dismissListener != null) {
            dismissListener.onDismiss(dialog);
        }
    }

    @Override
    protected View getDialogView() {
        return mAlert.getView();
    }

    @Override
    protected void BindView(View view) {
        BindViewHolder viewHolder = new BindViewHolder(view, this);
        if (mAlert.getSubViewResIds() != null && mAlert.getSubViewResIds().length > 0) {
            for (int id : mAlert.getSubViewResIds()) {
                viewHolder.addOnClickListener(id);
            }
        }
        DialogViewInterface.OnBindViewListener bindViewListener = mAlert.getOnBindViewListener();
        if (bindViewListener != null) {
            bindViewListener.bindView(viewHolder);
        }
    }

    @Override
    protected int getLayoutResId() {
        return mAlert.getLayoutResId();
    }

    @Override
    protected int getGravity() {
        return mAlert.getGravity();
    }

    @Override
    protected float getWindowWidthAmount() {
        return mAlert.getWindowWidthAmount();
    }

    @Override
    protected float getDimAmount() {
        return mAlert.getDimAmount();
    }

    @Override
    protected int getBackgroundColor() {
        return mAlert.getBackgroundColor();
    }

    public void show() {
        show(mAlert.getFragmentManager(), mAlert.getTag());
    }


    public DialogViewInterface.OnBindViewListener getOnBindViewListener() {
        return mAlert.getOnBindViewListener();
    }

    public DialogViewInterface.OnViewClickListener getOnViewClickListener() {
        return mAlert.getOnViewClickListener();
    }

    /**
     * Builder
     */
    public static class Builder {

        @IntDef({STYLE_NORMAL, STYLE_NO_TITLE, STYLE_NO_FRAME, STYLE_NO_INPUT})
        @Retention(RetentionPolicy.SOURCE)
        private @interface DialogStyle {
        }

        private final AlertController.AlertParams P;

        public Builder(@NonNull FragmentManager fragmentManager) {
            P = new AlertController.AlertParams();
            P.mFragmentManager = fragmentManager;
        }

        public Builder setStyle(@DialogStyle int style) {
            P.mStyle = style;
            return this;
        }

        public Builder setTheme(@StyleRes int theme) {
            P.mTheme = theme;
            return this;
        }

        public Builder setTag(String tag) {
            P.mTag = tag;
            return this;
        }

        public Builder setView(@LayoutRes int layoutResId) {
            P.mView = null;
            P.mViewLayoutResId = layoutResId;
            return this;
        }

        public Builder setView(View view) {
            P.mView = view;
            P.mViewLayoutResId = 0;
            return this;
        }

        public Builder setGravity(int mGravity) {
            P.mGravity = mGravity;
            return this;
        }

        public Builder setOrientation(int mOrientation) {
            P.mOrientation = mOrientation;
            return this;
        }

        public Builder setWindowWidthAmount(float mWindowWidthAmount) {
            P.mWindowWidthAmount = mWindowWidthAmount;
            return this;
        }

        public Builder setDimAmount(float mDimAmount) {
            P.mDimAmount = mDimAmount;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }

        public Builder setOnBindViewListener(DialogViewInterface.OnBindViewListener bindViewListener) {
            P.mOnBindViewListener = bindViewListener;
            return this;
        }

        public Builder addViewOnClickListener(int... viewResIds) {
            P.mSubViewResIds = viewResIds;
            return this;
        }

        public Builder setOnViewClickListener(DialogViewInterface.OnViewClickListener viewClickListener) {
            P.mOnViewClickListener = viewClickListener;
            return this;
        }

        public Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        public CommonDialog create() {
            final CommonDialog dialog = new CommonDialog();
            P.apply(dialog.mAlert);
            dialog.setStyle(P.mStyle, P.mTheme);
            dialog.setCancelable(P.mCancelable);
            return dialog;
        }

        public CommonDialog show() {
            final CommonDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }

}
