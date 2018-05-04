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
import android.widget.Toast;

/**
 * Created by pp.tai on 16:05 2018/04/26.
 */

public class ToastUtil {

    public static boolean mIsShow = true;
    private static Toast mToast = null;

    private ToastUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * Set Show Toast
     * @param isShow true or false;
     */
    public static void setShow(boolean isShow) {
        mIsShow = isShow;
    }

    /**
     * cancel toast;
     */
    public static void cancel() {
        if (mToast != null && mIsShow) {
            mToast.cancel();
        }
    }

    /**
     * Show toast in short time
     * @param context context
     * @param text text
     */
    public static void showShort(Context context, CharSequence text) {
        if (mIsShow) {
            if (mToast == null) {
                mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(text);
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.show();
        }
    }

    public static void showShort(Context context, int resId) {
        if (mIsShow) {
            if (mToast == null) {
                mToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(resId);
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.show();
        }
    }

    /**
     * Show toast in long time
     * @param context context
     * @param text text
     */
    public static void showLong(Context context, CharSequence text) {
        if (mIsShow) {
            if (mToast == null) {
                mToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            } else {
                mToast.setText(text);
                mToast.setDuration(Toast.LENGTH_LONG);
            }
            mToast.show();
        }
    }

    public static void showLong(Context context, int resId) {
        if (mIsShow) {
            if (mToast == null) {
                mToast = Toast.makeText(context, resId, Toast.LENGTH_LONG);
            } else {
                mToast.setText(resId);
                mToast.setDuration(Toast.LENGTH_LONG);
            }
            mToast.show();
        }
    }

    /**
     * show toast
     * @param context context
     * @param text text
     * @param duration Toast.LENGTH_SHORT; Toast.LENGTH_LONG
     */
    public static void show(Context context, CharSequence text, int duration) {
        if (mIsShow) {
            if (mToast == null) {
                mToast = Toast.makeText(context, text, duration);
            } else {
                mToast.setText(text);
                mToast.setDuration(duration);
            }
            mToast.show();
        }
    }

    public static void show(Context context, int resId, int duration) {
        if (mIsShow) {
            if (mToast == null) {
                mToast = Toast.makeText(context, resId, duration);
            } else {
                mToast.setText(resId);
                mToast.setDuration(duration);
            }
            mToast.show();
        }
    }

    /**
     * show toast with gravity
     * @param context context
     * @param resId resId
     * @param duration duration
     * @param gravity Gravity.CENTER; Gravity.BOTTOM; Gravity.TOP;
     */
    public static void show(Context context, int resId, int duration, int gravity) {
        if (mIsShow) {
            if (mToast == null) {
                mToast = Toast.makeText(context, resId, duration);
            } else {
                mToast.setText(resId);
                mToast.setDuration(duration);
            }
            mToast.setGravity(gravity, 0, 0);
            mToast.show();
        }
    }

}
