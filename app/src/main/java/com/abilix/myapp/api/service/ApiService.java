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

package com.abilix.myapp.api.service;

import com.abilix.myapp.bean.BaseEntity;
import com.abilix.myapp.bean.MovieInfo;
import com.abilix.myapp.bean.TokenInfo;
import com.abilix.myapp.bean.UserInfo;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by pp.tai on 11:21 2018/05/02.
 */

public interface ApiService {

    /**
     * get movie list
     * @param start
     * @param count
     * @return
     */
    @GET("top250")
    Observable<BaseEntity> getMovieList(@Query("start") int start,
                                                   @Query("count") int count);

    /**
     * get token
     * @param client_secret
     * @param client_id
     * @param grant_type
     * @return
     */
    @GET("/rest/oauth/token?lang=zh&curr=CNY")
    Observable<TokenInfo> getHttpToken(@Query("client_secret") String client_secret,
                                       @Query("client_id") String client_id,
                                       @Query("grant_type") String grant_type);

    /**
     * refresh token
     * @param refresh_token
     * @param client_secret
     * @param redirect_uri
     * @param client_id
     * @param grant_type
     * @return
     */
    @GET("/rest/oauth/token")
    Observable<TokenInfo> refreshToken(@Query("refresh_token") String refresh_token,
                                       @Query("client_secret") String client_secret,
                                       @Query(("redirect_uri")) String redirect_uri,
                                       @Query("client_id") String client_id,
                                       @Query("grant_type") String grant_type);

    /**
     * register
     * @param Authorization
     * @param phoneNumber
     * @param password
     * @param captcha
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded;charset=utf-8")
    @FormUrlEncoded
    @POST("/rest/v2/chinagrandauto/users")
    Observable<BaseEntity> register(@Header("Authorization") String Authorization,
                                    @Field("phoneNumber") String phoneNumber,
                                    @Field("password") String password,
                                    @Field("captcha") String captcha);

    /**
     * forget password
     * @param Authorization
     * @param phoneNumber
     * @param password
     * @param captcha
     * @return
     */
    @Headers("Content-Type:application/x-www-form-urlencoded;charset=utf-8")
    @FormUrlEncoded
    @POST("/rest/v2/chinagrandauto/users/current/forgottenpassword")
    Observable<BaseEntity> forgetPwd(@Header("Authorization") String Authorization,
                                     @Field("phoneNumber") String phoneNumber,
                                     @Field("newPassword") String password,
                                     @Field("captcha") String captcha);

    /**
     * login
     * @param username
     * @param password
     * @param grant_type
     * @param client_secret
     * @param client_id
     * @return
     */
    @GET("/rest/oauth/token")
    Observable<TokenInfo> login(@Query("username") String username,
                                @Query("password") String password,
                                @Query("grant_type") String grant_type,
                                @Query("client_secret") String client_secret,
                                @Query("client_id") String client_id
    );

    /**
     * get user info
     * @param Authorization
     * @return
     */
    @GET("/rest/v2/chinagrandauto/users/userinfo")
    Observable<UserInfo> getUserInfo(@Header("Authorization") String Authorization);

}
