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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;


import com.abilix.myapp.utils.ToastUtil;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by pp.tai on 17:22 2018/04/09.
 */

public abstract class BaseActivity extends Activity {

    protected CompositeDisposable mDisposables;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ButterKnife.bind(this);

        initData();
        configViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unSubscribe();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * init data
     */
    protected abstract void initData();

    /**
     * configure views
     */
    protected abstract void configViews();

    /**
     * get layout id
     * @return layout Id
     */
    protected abstract int getLayoutId();

    /**
     * Start Activity with Bundle
     * @param cls class
     * @param bundle bundle
     */
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * Start Activity without bundle
     * @param cls class
     */
    protected void startActivity(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent);
    }

    /**
     * show short toast
     * @param text text to display
     */
    protected void showShortToast(String text) {
        ToastUtil.showShort(this, text);
    }

    protected void showShortToast(int resId) {
        ToastUtil.showShort(this, resId);
    }

    /**
     * show long toast
     * @param text text to display
     */
    protected void showLongToast(String text) {
        ToastUtil.showLong(this, text);
    }

    protected void showLongToast(int resId) {
        ToastUtil.showLong(this, resId);
    }

    protected void addSubscribe(Disposable d) {
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
        mDisposables.add(d);
    }

    protected void unSubscribe() {
        if (mDisposables != null) {
            mDisposables.clear();
        }
    }

}
