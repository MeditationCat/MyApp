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

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abilix.myapp.R;

public abstract class BaseDialogFragment extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialog);//设置无标题、透明背景、无边框等等属性
        setCancelable(false);//设置点击除了对话框以外的部分就关闭
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置宽度满屏
        //DisplayMetrics dm = new DisplayMetrics();
        //getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        //getDialog().getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
        //设置显示和关闭时的动画
        //getDialog().getWindow().setWindowAnimations(R.style.style_item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    /**
     * get layout id
     *
     * @return layout Id
     */
    @LayoutRes
    protected abstract int getLayoutId();
}
