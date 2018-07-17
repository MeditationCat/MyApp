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
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.abilix.myapp.api.DBApi;
import com.abilix.myapp.api.exception.ApiException;
import com.abilix.myapp.api.observer.BaseObserver;
import com.abilix.myapp.base.BaseActivity;
import com.abilix.myapp.bean.douban.MovieInfo;
import com.abilix.myapp.socket.MulticastSocketListener;
import com.abilix.myapp.socket.MulticastSocketUtil;
import com.abilix.myapp.socket.UserInfo;
import com.abilix.myapp.utils.CustomDialog;
import com.abilix.myapp.utils.NetworkUtil;
import com.abilix.myapp.view.dialog.CommonDialog;
import com.orhanobut.logger.Logger;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import butterknife.BindView;


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

    @BindView(R.id.btn_01)
    Button mBtnConfirmCancel;

    @BindView(R.id.btn_02)
    Button mBtnConfirm;

    @BindView(R.id.btn_03)
    Button mBtnLoading;

    @BindView(R.id.btn_04)
    Button mBtnSend;

    @BindView(R.id.btn_05)
    Button mBtnBack;

    @BindView(R.id.et_packet)
    EditText mEditText;

    private MulticastSocketUtil mMulticastSocketUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        mMulticastSocketUtil = new MulticastSocketUtil();
        //mMulticastSocketUtil.setNickName("K5-01");
        mMulticastSocketUtil.setMulticastSocketListener(new MulticastSocketListener() {
            @Override
            public void onStateChanged(UserInfo user, int onlineState) {
                final String name = user.getName();
                final String state = onlineState == 0x01 ? "login" : "logout";
                Logger.d(user.toString() + state);
                updateMsg(name + " " + state + "\n");
            }

            @Override
            public void onReceived(UserInfo user, byte[] data) {
                final String msg = new String(data);
                final String name = user.getName();
                Logger.d("onReceived: " + msg);
                updateMsg(name + ": " + msg + "\n");
            }
        });
    }

    private void updateMsg(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.append(msg);
                int offset = textView.getLineCount() * textView.getLineHeight();
                if (offset > textView.getHeight()) {
                    textView.scrollTo(0, offset - textView.getHeight() + textView.getLineHeight());
                }
            }
        });
    }

    @Override
    protected void configViews() {
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        textView.setText(NetworkUtil.getLocalIPAddress() + "\n");

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = mEditText.getText().toString();
                try {
                    mMulticastSocketUtil.sendMulticastSocket(msg.getBytes(StandardCharsets.UTF_8));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mBtnConfirmCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBApi.getInstance().getMovieTop250(0, 1)
                        .subscribe(new BaseObserver<MovieInfo>(MainActivity.this) {
                            @Override
                            public void onNext(MovieInfo movieInfo) {
                                Logger.d("onNext: %s",movieInfo.getTitle());
                                textView.setText(movieInfo.getTitle());
                                CustomDialog.showConfirmCancelDialog(MainActivity.this, movieInfo.getTitle(), movieInfo.getSubjects().get(0).getId());
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

            }
        });
        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBApi.getInstance().getMovieTop250(0, 1)
                        .subscribe(new BaseObserver<MovieInfo>(MainActivity.this) {
                            @Override
                            public void onNext(MovieInfo movieInfo) {
                                Logger.d("onNext: %s",movieInfo.getTitle());
                                textView.setText(movieInfo.getTitle());
                                CustomDialog.showConfirmDialog(MainActivity.this, movieInfo.getTitle(), movieInfo.getSubjects().get(0).getId());
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

            }
        });
        mBtnLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CommonDialog loadingDialog = CustomDialog.showLoadingDialog(MainActivity.this);
                DBApi.getInstance().getMovieTop250(0, 1)
                        .subscribe(new BaseObserver<MovieInfo>(MainActivity.this) {
                            @Override
                            public void onNext(MovieInfo movieInfo) {
                                Logger.d("onNext: %s",movieInfo.getTitle());
                                textView.setText(movieInfo.getTitle());
                                //CustomDialog.showLoadingDialog(MainActivity.this);
                                loadingDialog.dismiss();
                            }

                            @Override
                            public void onComplete() {
                                Logger.d("onComplete:");
                                loadingDialog.dismiss();
                            }

                            @Override
                            protected void onError(ApiException e) {
                                Logger.d("onError: %s", e.getMessage());
                                loadingDialog.dismiss();
                            }
                        });

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMulticastSocketUtil != null) {
            mMulticastSocketUtil.close();
            mMulticastSocketUtil = null;
        }
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
