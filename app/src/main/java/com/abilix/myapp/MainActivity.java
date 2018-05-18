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

package com.abilix.myapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.abilix.myapp.api.DBApi;
import com.abilix.myapp.api.exception.ApiException;
import com.abilix.myapp.api.observer.BaseObserver;
import com.abilix.myapp.base.BaseActivity;
import com.abilix.myapp.bean.douban.MovieInfo;
import com.orhanobut.logger.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;


/**
 * Created by pp.tai on 16:05 2018/04/04.
 */

public class MainActivity extends BaseActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @BindView(R.id.sample_text)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        // Example of a call to a native method
        textView.setText(stringFromJNI());


    }

    int NUM_OF_CORES = Runtime.getRuntime().availableProcessors();
    int KEEP_ALIVE_TIME = 1;
    TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    BlockingQueue<Runnable> taskQueue = new LinkedBlockingDeque<Runnable>();
    ExecutorService executorService = new ThreadPoolExecutor(NUM_OF_CORES,
            NUM_OF_CORES * 2,
            KEEP_ALIVE_TIME,
            KEEP_ALIVE_TIME_UNIT,
            taskQueue,
            new BackgroundThreadFactory(),
            new DefaultRejectedExecutionHandler());
    @Override
    protected void initData() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {

            }
        });

        DBApi.getInstance().getMovieTop250(0, 1)
                .subscribe(new BaseObserver<MovieInfo>(this) {
                    @Override
                    public void onNext(MovieInfo movieInfo) {
                        Logger.d("onNext: %s",movieInfo.getTitle());
                        textView.setText(movieInfo.getTitle());
                    }

                    @Override
                    public void onComplete() {
                        Logger.d("onComplete:");
                    }

                    @Override
                    protected void onError(ApiException e) {
                        Logger.d("onError: %s", e.getMessage());
                    }
                });
        /*subScribe(Api.getInstance().getMovieList(0, 2), new BaseObserver<MovieInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                Logger.d("onSubscribe: %b", d.isDisposed());
            }

            @Override
            public void onNext(MovieInfo movieInfo) {
                Logger.d("onNext: %s",movieInfo.getTitle());
            }

            @Override
            public void onComplete() {
                Logger.d("onComplete:");
            }

            @Override
            public void onError(ApiException e) {
                Logger.d("onError: %s", e.getMessage());
            }
        });
        Api.getInstance().getMovieList(0, 2)
                .subscribe(new BaseObserver<MovieInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                        Logger.d("onSubscribe: %b", d.isDisposed());
                    }

                    @Override
                    public void onNext(final MovieInfo movieInfo) {
                        Logger.d("onNext: %s",movieInfo.getTitle());

                        List<MovieInfo.SubjectsBean> list = movieInfo.getSubjects();
                        for (MovieInfo.SubjectsBean sub : list) {
                            Logger.d("onNext: id=%s year=%s title:%s", sub.getId(), sub.getYear() ,sub.getTitle());
                        }
                        final TextView textView = findViewById(R.id.sample_text);
                        textView.setText(movieInfo.getTitle());
                    }

                    @Override
                    public void onComplete() {
                        Logger.d("onComplete:");
                    }

                    @Override
                    public void onError(ApiException e) {
                        Logger.d("onError: %s", e.getMessage());
                    }
                });*/
    }

    @Override
    protected void configViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    class BackgroundThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r);
        }
    }

    class DefaultRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            executor.remove(r);
        }
    }
}
