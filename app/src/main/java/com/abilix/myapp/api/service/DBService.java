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
import com.abilix.myapp.bean.douban.MovieInfo;
import com.abilix.myapp.bean.douban.TokenInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by pp.tai on 14:06 2018/05/02.
 */

public interface DBService {


    /**
     * get authorization code
     *
     * @param client_id     必选参数，应用的唯一标识，对应于APIKey
     * @param redirect_uri  必选参数，用户授权完成后的回调地址，应用需要通过此回调地址获得用户的授权结果。此地址必须与在应用注册时填写的回调地址一致。
     * @param response_type 必选参数，此值可以为 code 或者 token 。在本流程中，此值为 code
     * @param scope         可选参数，申请权限的范围，如果不填，则使用缺省的 scope。如果申请多个 scope，使用逗号分隔。
     * @param state         可选参数，用来维护请求和回调状态的附加字符串，在授权完成回调时会附加此参数，应用可以根据此字符串来判断上下文关系。
     * @return 当用户拒绝授权时，浏览器会重定向到 redirect_uri，并附加错误信息 https://www.example.com/back?error=access_denied; 当用户同意授权时，浏览器会重定向到 redirect_uri，并附加 autorization_code 当用户同意授权时，浏览器会重定向到 redirect_uri，并附加 autorization_code
     */
    @GET("/service/auth2/auth")
    Observable<String> getAuth(@Query("client_id") String client_id,
                               @Query("redirect_uri") String redirect_uri,
                               @Query("response_type") String response_type,
                               @Query("scope") String scope,
                               @Query("state") String state);

    /**
     * get access token
     *
     * @param client_id     必选参数，应用的唯一标识，对应于 APIKey
     * @param client_secret 必选参数，应用的唯一标识，对应于豆瓣 secret
     * @param redirect_uri  必选参数，用户授权完成后的回调地址，应用需要通过此回调地址获得用户的授权结果。此地址必须与在应用注册时填写的回调地址一致
     * @param grant_type    必选参数，此值可以为 authorization_code 或者 refresh_token 。在本流程中，此值为 authorization_code
     * @param code          必选参数，上一步中获得的 authorization_code
     * @return TokenInfo
     */
    @POST("/service/auth2/token")
    Observable<TokenInfo> getToken(@Query("client_id") String client_id,
                                   @Query("client_secret") String client_secret,
                                   @Query("redirect_uri") String redirect_uri,
                                   @Query("grant_type") String grant_type,
                                   @Query("code") String code);

    /**
     * get access token
     *
     * @param client_id     必选参数，应用的唯一标识，对应于 APIKey
     * @param client_secret 必选参数，应用的唯一标识，对应于豆瓣 secret
     * @param redirect_uri  必选参数，用户授权完成后的回调地址，应用需要通过此回调地址获得用户的授权结果。此地址必须与在应用注册时填写的回调地址一致
     * @param grant_type    必选参数，此值可以为 authorization_code 或者 refresh_token。在本流程中，此值为 refresh_token
     * @param refresh_token 必选参数，刷新令牌
     * @return TokenInfo
     */
    @POST("/service/auth2/token")
    Observable<TokenInfo> refreshToken(@Query("client_id") String client_id,
                                       @Query("client_secret") String client_secret,
                                       @Query("redirect_uri") String redirect_uri,
                                       @Query("grant_type") String grant_type,
                                       @Query("refresh_token") String refresh_token);

    /**
     * get movie top250
     *
     * @param start 开始索引
     * @param count 索引个数
     * @return MovieInfo
     */
    @GET("/v2/movie/top250")
    Observable<MovieInfo> getMovieTop250(@Query("start") int start,
                                         @Query("count") int count);


}
