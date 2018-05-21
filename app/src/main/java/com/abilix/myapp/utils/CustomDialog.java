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

package com.abilix.myapp.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abilix.myapp.R;
import com.abilix.myapp.base.BaseDialogFragment;

public class CustomDialog extends BaseDialogFragment {

    public CustomDialog() {
    }


    @Override
    protected int getLayoutId() {
        return R.layout.layout_dialog_confirm;
    }

    public static void showConfirmDialog(FragmentActivity context, String title, String msg) {
        CustomDialog customDialog = new CustomDialog();
        customDialog.show(context.getSupportFragmentManager(), "xxxxxxxxxxxxxxxxx");
    }
}
