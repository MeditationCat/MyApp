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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = BindDialog();
        if (dialog != null) {
            return dialog;
        }
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = BindLayoutView(inflater, container);
        if (view != null) {
            return view;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 绑定实例化的弹窗
     * @return 返回实例化的Dialog
     */
    protected abstract Dialog BindDialog();

    /**
     * 绑定填充的Layout视图
     * @param inflater LayoutInflater对象，可用于扩展Fragment中的任何视图
     * @param container 如果非null，这是该Fragment的UI应该附加到的父视图，Fragment不应该自己添加视图，但是可以使用它来生成视图的LayoutParams
     * @return 返回填充好的Layout视图
     */
    protected abstract View BindLayoutView(LayoutInflater inflater, @Nullable ViewGroup container);

}
