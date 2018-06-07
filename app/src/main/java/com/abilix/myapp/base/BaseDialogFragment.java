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
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.abilix.myapp.R;
import com.orhanobut.logger.Logger;

public class BaseDialogFragment extends DialogFragment {

    //窗口重心位置
    private int mGravity = Gravity.CENTER;
    //背景暗淡效果
    private boolean mHaveDimAmount = false;
    private float mDimAmount = 0.0f;
    //自定义窗口布局
    private boolean mCustomDialog = false;
    private int mLayoutId = 0;
    //窗口占屏幕宽度百分比
    private float mWindowWidthAmount = 0.8f;

    private String mTitle;
    private String mMessage;
    private String mPositiveButtonText;
    private String mNegativeButtonText;
    private String mNeutralButtonText;
    private DialogInterface.OnClickListener mDialogButtonListener;


    public BaseDialogFragment() {
    }

    //设置标题
    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setTitle(@StringRes int resId) {
        this.mTitle = getString(resId);
    }

    //设置内容
    public void setMessage(String message) {
        this.mMessage = message;
    }

    public void setMessage(@StringRes int resId) {
        this.mMessage = getString(resId);
    }

    //设置确认按钮
    public void setPositiveButton(String text) {
        this.mPositiveButtonText = text;
    }

    public void setPositiveButton(@StringRes int resId) {
        this.mPositiveButtonText = getString(resId);
    }

    //设置取消按钮
    public void setNegativeButton(String text) {
        this.mNegativeButtonText = text;
    }

    public void setNegativeButton(@StringRes int resId) {
        this.mNegativeButtonText = getString(resId);
    }

    //设置按钮监听
    public void setDialogButtonListener(DialogInterface.OnClickListener listener) {
        mDialogButtonListener = listener;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("onCreate()");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Logger.d("onCreateDialog()");
        if (!mCustomDialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(mTitle)
                .setMessage(mMessage)
                .setPositiveButton(mPositiveButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mDialogButtonListener != null) {
                            mDialogButtonListener.onClick(dialog, which);
                        }
                    }
                })
                .setNegativeButton(mNegativeButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mDialogButtonListener != null) {
                            mDialogButtonListener.onClick(dialog, which);
                        }
                    }
                })
                //.setNeutralButton("Neutral", null)
                .setCancelable(isCancelable());
        return  builder.create();
        }
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Logger.d("onCreateView()");
        if (mCustomDialog) {
            View view = inflater.inflate(mLayoutId, container);
            ConfirmDialogViewHolder confirmDialogViewHolder = new ConfirmDialogViewHolder(view);
            confirmDialogViewHolder.setTitle(mTitle);
            confirmDialogViewHolder.setMessage(mMessage);
            confirmDialogViewHolder.setPositiveButton(mPositiveButtonText, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (mDialogButtonListener != null) {
                        mDialogButtonListener.onClick(getDialog(), DialogInterface.BUTTON_POSITIVE);
                    }
                }
            });
            confirmDialogViewHolder.setNegativeButton(mNegativeButtonText, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (mDialogButtonListener != null) {
                        mDialogButtonListener.onClick(getDialog(), DialogInterface.BUTTON_NEGATIVE);
                    }
                }
            });

            return view;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.d("onStart()");
        Dialog myDialog = getDialog();
        if (myDialog != null) {
            Window window = myDialog.getWindow();
            if (window != null) {
                //在5.0以下的版本会出现白色背景边框，若在5.0以上设置则会造成文字部分的背景也变成透明
                if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                    //目前只有这两个dialog会出现边框
                    if(myDialog instanceof ProgressDialog || myDialog instanceof DatePickerDialog) {
                        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    }
                }
                //设置窗口背后暗淡效果
                if (mHaveDimAmount) {
                    window.setDimAmount(mDimAmount);
                }
                //设置窗口显示位置
                window.setGravity(mGravity);
                //设置窗口显示宽度和高度
                DisplayMetrics dm = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
                window.setLayout((int) (dm.widthPixels * mWindowWidthAmount), window.getAttributes().height);
                //设置显示和关闭时的动画
                //window.setWindowAnimations(R.style.style_item);
            }
        }
    }


    /**
     * 设置窗口布局
     * @param resId 窗口布局ID
     */
    public void setLayoutId(@LayoutRes int resId) {
        this.mCustomDialog = true;
        this.mLayoutId = resId;
    }

    /**
     * 设置窗口占屏幕宽度百分比
     * @param amount 0.0不显示到1.0宽度填满
     */
    public void setWindowWidthAmount(float amount) {
        this.mWindowWidthAmount = amount;
    }

    /**
     * 设置窗口显示重心位置
     * @param gravity 所需的引力常数
     */
    public void setGravity(int gravity) {
        this.mGravity = gravity;
    }

    /**
     *设置窗口后的暗淡效果
     * @param amount 暗淡效果量，从0.0为无暗淡效果到1.0为全不暗淡效果
     */
    public void setDimAmount(float amount) {
        this.mHaveDimAmount = true;
        this.mDimAmount = amount;
    }

    //确认弹框View绑定
    class ConfirmDialogViewHolder implements View.OnClickListener {
        TextView title;
        TextView message;
        TextView positiveBtn;
        TextView negativeBtn;
        TextView neutralBtn;

        private View.OnClickListener mPositiveButtonListener;
        private View.OnClickListener mNegativeButtonListener;
        private View.OnClickListener mNeutralButtonListener;

        public ConfirmDialogViewHolder(View view) {
            if (view != null) {
                title = view.findViewById(R.id.tv_dialog_confirm_title);
                message = view.findViewById(R.id.tv_dialog_confirm_message);
                positiveBtn = view.findViewById(R.id.tv_dialog_confirm_positive);
                if (positiveBtn != null) {
                    positiveBtn.setOnClickListener(this);
                }
                negativeBtn = view.findViewById(R.id.tv_dialog_confirm_negative);
                if (negativeBtn != null) {
                    negativeBtn.setOnClickListener(this);
                }
            }
        }

        //设置标题
        public void setTitle(String title) {
            if (this.title != null) {
                this.title.setText(title);
            }
        }

        //设置内容
        public void setMessage(String message) {
            if (this.message != null) {
                this.message.setText(message);
            }
        }

        //设置确认按钮
        public void setPositiveButton(String positiveBtn, View.OnClickListener positiveButtonListener) {
            if (this.positiveBtn != null) {
                this.positiveBtn.setText(positiveBtn);
            }
            mPositiveButtonListener = positiveButtonListener;
        }

        //设置取消按钮
        public void setNegativeButton(String negativeBtn, View.OnClickListener negativeButtonListener) {
            if (this.negativeBtn != null) {
                this.negativeBtn.setText(negativeBtn);
            }
            mNegativeButtonListener = negativeButtonListener;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_dialog_confirm_negative:
                    if (mNegativeButtonListener != null) {
                        mNegativeButtonListener.onClick(v);
                    }
                    break;
                case R.id.tv_dialog_confirm_positive:
                    if (mPositiveButtonListener != null) {
                        mPositiveButtonListener.onClick(v);
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
