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

package com.abilix.myapp.base;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public abstract class BaseDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        if (getLayoutResId() != 0) {
            view = inflater.inflate(getLayoutResId(), container, false);
        } else {
            view = getDialogView();
        }
        if (view != null) {
            BindView(view);
            return view;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            //设置窗口背景色透明
            window.setBackgroundDrawable(new ColorDrawable(getBackgroundColor()));
            //设置窗口显示宽度和高度
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            layoutParams.width = (int) (getWindowWidthAmount() * dm.widthPixels);
            //layoutParams.height = window.getAttributes().height;
            //设置窗口背后暗淡效果
            layoutParams.dimAmount = getDimAmount();
            //设置窗口显示位置
            layoutParams.gravity = getGravity();
            window.setAttributes(layoutParams);
            //设置显示和关闭时的动画
            //window.setWindowAnimations(R.style.style_item);
        }
    }

    /**
     * 获取布局Id
     * @return 返回布局Id 如果不使用布局返回 0;
     */
    @LayoutRes
    protected abstract int getLayoutResId();

    /**
     * 获取dialog视图
     * @return 返回dialog视图
     */
    protected abstract View getDialogView();

    /**
     *  绑定dialog视图
     * @param view dialog视图
     */
    protected abstract void BindView(View view);

    /**
     * 获取窗口显示重心位置
     * @return 返回窗口重心位置
     */
    protected abstract int getGravity();

    /**
     * 获取窗口宽度比例
     * @return 返回窗口宽度比例
     */
    protected abstract float getWindowWidthAmount();

    /**
     * 获取窗口背后暗淡效果暗度
     * @return 返回窗口背后暗淡效果暗度
     */
    protected abstract float getDimAmount();

    /**
     * 获取窗口背景颜色值
     * @return 返回窗口背景颜色值
     */
    protected abstract int getBackgroundColor();
}
