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

import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;

import com.abilix.myapp.R;
import com.abilix.myapp.view.dialog.BindViewHolder;
import com.abilix.myapp.view.dialog.CommonDialog;
import com.abilix.myapp.view.dialog.DialogViewInterface;

public class CustomDialog {

    public CustomDialog() {
    }


    public static void showConfirmCancelDialog(final FragmentActivity context, final String title, final String msg) {
        CommonDialog.Builder builder = new CommonDialog.Builder(context.getSupportFragmentManager());
        CommonDialog commonDialog = builder.setStyle(CommonDialog.STYLE_NO_TITLE)
                .setTheme(R.style.CustomDialog)
                .setView(R.layout.dialog_confirm_cancel)
                .setOnBindViewListener(new DialogViewInterface.OnBindViewListener() {
                    @Override
                    public void bindView(BindViewHolder viewHolder) {
                        viewHolder.setText(R.id.tv_dialog_confirm_title, title)
                                .setText(R.id.tv_dialog_confirm_message, msg);
                    }
                })
                .addViewOnClickListener(R.id.tv_dialog_confirm_positive, R.id.tv_dialog_confirm_negative)
                .setOnViewClickListener(new DialogViewInterface.OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, CommonDialog dialog) {
                        switch (view.getId()) {
                            case R.id.tv_dialog_confirm_positive:
                                ToastUtil.showShort(context.getApplicationContext(), "BUTTON_POSITIVE");
                                dialog.dismiss();
                                break;
                            case R.id.tv_dialog_confirm_negative:
                                ToastUtil.showShort(context.getApplicationContext(), "BUTTON_NEGATIVE");
                                dialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                })
                .setCancelable(false)
                .setDimAmount(0.2f)
                .setGravity(Gravity.CENTER)
                .setWindowWidthAmount(0.8f)
                .setTag("xxxxxx")
                .create();

        commonDialog.show();
    }

    public static void showConfirmDialog(final FragmentActivity context, final String title, final String msg) {
        CommonDialog.Builder builder = new CommonDialog.Builder(context.getSupportFragmentManager());
        CommonDialog commonDialog = builder.setStyle(CommonDialog.STYLE_NO_TITLE)
                .setTheme(R.style.CustomDialog)
                .setView(R.layout.dialog_confirm)
                .setOnBindViewListener(new DialogViewInterface.OnBindViewListener() {
                    @Override
                    public void bindView(BindViewHolder viewHolder) {
                        viewHolder.setText(R.id.tv_dialog_confirm_title, title)
                                .setText(R.id.tv_dialog_confirm_message, msg);
                    }
                })
                .addViewOnClickListener(R.id.tv_dialog_confirm_positive)
                .setOnViewClickListener(new DialogViewInterface.OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, CommonDialog dialog) {
                        switch (view.getId()) {
                            case R.id.tv_dialog_confirm_positive:
                                ToastUtil.showShort(context.getApplicationContext(), "BUTTON_POSITIVE");
                                dialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                })
                .setCancelable(false)
                .setDimAmount(0.2f)
                .setGravity(Gravity.CENTER)
                .setWindowWidthAmount(0.8f)
                .setTag("xxxxxx")
                .create();

        commonDialog.show();
    }

    public static CommonDialog showLoadingDialog(final FragmentActivity context) {
        CommonDialog.Builder builder = new CommonDialog.Builder(context.getSupportFragmentManager());
        CommonDialog commonDialog = builder.setStyle(CommonDialog.STYLE_NO_TITLE)
                .setTheme(R.style.CustomDialog)
                .setView(R.layout.dialog_loading)
                .setCancelable(true)
                .setDimAmount(0.2f)
                .setGravity(Gravity.CENTER)
                .setWindowWidthAmount(0.2f)
                .setTag("xxxxxx")
                .create();

        commonDialog.show();
        return commonDialog;
    }
}
