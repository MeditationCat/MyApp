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

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.abilix.myapp.R;

public abstract class BaseDialogFragment extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置Style和theme
        setStyle(STYLE_NO_TITLE, R.style.CustomDialog);
        //设置触摸窗口外部是否关闭
        setCancelable(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog myDialog = getDialog();
        if (myDialog != null) {
            Window window = myDialog.getWindow();
            if (window != null) {
                WindowManager.LayoutParams windowParams = window.getAttributes();
                windowParams.dimAmount = 0.0f;
                window.setAttributes(windowParams);
                //在5.0以下的版本会出现白色背景边框，若在5.0以上设置则会造成文字部分的背景也变成透明
                if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                    if(myDialog instanceof ProgressDialog || myDialog instanceof DatePickerDialog) {//目前只有这两个dialog会出现边框
                        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    }
                }
                //设置窗口显示宽度和高度
                //DisplayMetrics dm = new DisplayMetrics();
                //getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
                //window.setLayout((int) (dm.widthPixels * 0.80f), window.getAttributes().height);
                //设置显示和关闭时的动画
                //window.setWindowAnimations(R.style.style_item);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    /**
     * get layout id
     *
     * @return layout Id
     */
    @LayoutRes
    protected abstract int getLayoutId();
}
