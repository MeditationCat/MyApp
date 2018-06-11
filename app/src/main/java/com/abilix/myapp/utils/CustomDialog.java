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
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abilix.myapp.R;
import com.abilix.myapp.base.BaseDialogFragment;
import com.abilix.myapp.view.dialog.CommonDialog;
import com.orhanobut.logger.Logger;

import butterknife.BindView;

public class CustomDialog {

    public CustomDialog() {
    }


    public static void showConfirmDialog(final FragmentActivity context, String title, String msg) {
        CommonDialog.Builder builder = new CommonDialog.Builder(context.getSupportFragmentManager());
        CommonDialog commonDialog = builder.setStyle(CommonDialog.STYLE_NO_TITLE)
                .setTheme(R.style.CustomConfirmDialog)
                .setView(R.layout.layout_dialog_confirm)
                //.setCancelable(false)
                //.setDimAmount(0.2f)
                //.setGravity(Gravity.BOTTOM)
                //.setWindowWidthAmount(0.8f)
                .setTag("xxxxxx")
                .create();

        commonDialog.show();


        /*AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);

        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Logger.d("setDialogButtonListener: onClick()----> " + which);
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        ToastUtil.showShort(context, "BUTTON_POSITIVE");
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        ToastUtil.showShort(context, "BUTTON_NEGATIVE");
                        break;
                    case DialogInterface.BUTTON_NEUTRAL:
                        ToastUtil.showShort(context, "BUTTON_NEUTRAL");
                        break;
                    default:
                        break;
                }
            }
        };
        alertBuilder.setView(R.layout.layout_dialog_confirm)
                .setCancelable(false)
                .setTitle("提示")
                .setMessage("测试信息")
                .setPositiveButton("确认", onClickListener)
                .setNegativeButton("取消", onClickListener).show();
*/
    }
}
