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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;


import com.abilix.myapp.api.exception.ApiException;
import com.abilix.myapp.api.exception.ExceptionEngine;
import com.abilix.myapp.api.subscriber.BaseObserver;
import com.abilix.myapp.bean.BaseEntity;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by pp.tai on 17:22 2018/04/09.
 */

public abstract class BaseActivity extends FragmentActivity implements BaseView {

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
     *
     * @return layout Id
     */
    protected abstract int getLayoutId();

    /**
     * Start Activity with Bundle
     *
     * @param cls    class
     * @param bundle bundle
     */
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(BaseActivity.this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * Start Activity without bundle
     *
     * @param cls class
     */
    protected void startActivity(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(BaseActivity.this, cls);
        startActivity(intent);
    }

    @Override
    public void addSubscribe(Disposable d) {
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

    /*@SuppressWarnings("unchecked")
    protected void subScribe(@NonNull Observable<BaseEntity> observable, @NonNull final BaseObserver baseObserver) {
        observable.subscribe(new Observer<BaseEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
                baseObserver.onSubscribe(d);
            }

            @Override
            public void onNext(BaseEntity baseEntity) {
                Logger.d("onNext===" + baseEntity.toString());
                if (baseEntity.isSuccess()) {
                    baseObserver.onNext(baseEntity.getData());
                } else {
                    onError(new Throwable(baseEntity.getMsg()));
                }
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("onError===" + e.getMessage());
                ApiException apiException = ExceptionEngine.handleException(e);
                baseObserver.onError(apiException);
            }

            @Override
            public void onComplete() {
                baseObserver.onComplete();
            }
        });
    }*/
}
