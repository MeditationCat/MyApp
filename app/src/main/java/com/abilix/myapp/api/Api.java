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

package com.abilix.myapp.api;

import android.content.Context;

import com.abilix.myapp.api.service.ApiService;
import com.abilix.myapp.base.BaseView;
import com.abilix.myapp.bean.BaseEntity;
import com.abilix.myapp.bean.MovieInfo;
import com.abilix.myapp.bean.TokenInfo;
import com.abilix.myapp.bean.UserInfo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    public static final String BASE_URL = "https://api.douban.com/v2/movie/";
    public static final HttpUrl HTTP_URL = HttpUrl.parse(BASE_URL);
    public static final int CACHE_SIZE = 1024 * 1024 * 10; // 10MB
    public static final int CONNECT_TIMEOUT = 30; // seconds
    public static final int READ_TIMEOUT = 60; // seconds
    public static final int WRITE_TIMEOUT = 60; // seconds


    private static ApiService apiService;
    private static Api instance;
    private Api() {
        OkHttpClient client = new OkHttpClient.Builder()
                //.cache(new Cache(app.getCacheDir, CACHE_SIZE))
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                                .addHeader("Accept", "application/json")
                                .addHeader("Connection", "persistent")
                                .addHeader("User-Agent", System.getProperties().getProperty("http.agent") + " OCCAndroidSDK")
                                .build();

                        return chain.proceed(request);
                    }
                })
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                //.sslSocketFactory()
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        if (HTTP_URL.host().equals(hostname)) {
                            return true;
                        } else {
                            HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                            return hv.verify(hostname, session);
                        }
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public static Api getInstance() {
        if (instance == null) {
            synchronized (Api.class) {
                instance = new Api();
            }
        }
        return instance;
    }


    public Observable<BaseEntity> getMovieList(int start, int count) {
        if (apiService != null) {
            return apiService.getMovieList(start, count)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
        return null;
    }


    /**
     * get token
     * @return
     */
    public Observable<TokenInfo> getToken() {
        if (apiService != null) {
            return apiService.getHttpToken("secret", "mobile_android", "client_credentials");
        }
        return null;
    }

    /**
     * refresh token
     * @param refresh_token
     * @return
     */
    public Observable<TokenInfo> refershToken(String refresh_token) {
        if (apiService != null) {
            return apiService.refreshToken(refresh_token, "secret", BASE_URL + "/oauth2_callback", "mobile_android", "refresh_token")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
        return null;
    }

    /**
     * register
     * @param Authorization
     * @param phoneNumber
     * @param password
     * @param captcha
     * @return
     */
    public Observable<BaseEntity> register(String Authorization, String phoneNumber, String password, String captcha) {
        if (apiService != null) {
            return apiService.register(Authorization, phoneNumber, password, captcha)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
        return null;
    }

    /**
     * forget password
     * @param Authorization
     * @param phoneNumber
     * @param password
     * @param captcha
     * @return
     */
    public Observable<BaseEntity> forgetPwd(String Authorization, String phoneNumber, String password, String captcha) {
        if (apiService != null) {
            return apiService.forgetPwd(Authorization, phoneNumber, password, captcha)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
        return null;
    }

    /**
     * login
     * @param username
     * @param password
     * @return
     */
    public Observable<TokenInfo> login(String username, String password) {
        if (apiService != null) {
            return apiService.login(username, password, "password", "secret", "mobile_android")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
        return null;
    }

    /**
     * get user info
     * @param Authorization
     * @return
     */
    public Observable<UserInfo> getUserInfo(String Authorization) {
        if (apiService != null) {
            return apiService.getUserInfo(Authorization)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
        return null;
    }

}
