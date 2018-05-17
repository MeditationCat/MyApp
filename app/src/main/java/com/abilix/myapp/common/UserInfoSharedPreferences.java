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

package com.abilix.myapp.common;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.abilix.myapp.bean.UserInfo;
import com.abilix.myapp.bean.douban.TokenInfo;

public class UserInfoSharedPreferences {

    private static SharedPreferences userconfig;// = GHApplication.userconfig;


    public static void setStoreName(String storeName){
        SharedPreferences.Editor editor = userconfig.edit();
        if (storeName!=null){
            editor.putString("storeName",storeName);
        }
        editor.commit();
    }

    public static String getStoreName(){
        return userconfig.getString("storeName","");
    }
    /**
     * 保存token的信息
     *
     * @param tokenInfo
     */
    public static void setTokenInfo(TokenInfo tokenInfo) {
        SharedPreferences.Editor editor = userconfig.edit();
        if (tokenInfo.getAccess_token() != null) {
            editor.putString("access_token", tokenInfo.getAccess_token());
        }
        if (tokenInfo.getRefresh_token() != null) {
            editor.putString("refresh_token", tokenInfo.getRefresh_token());
        }
        /*if (tokenInfo.getToken_type() != null) {
            editor.putString("token_type", tokenInfo.getToken_type());
        }*/

        long expires_in = System.currentTimeMillis() + tokenInfo.getExpires_in() * 1000;
        editor.putLong("expires_in", expires_in);
        editor.commit();
    }

    /**
     * 得到保存的token信息
     *
     * @return
     */
    public static TokenInfo getTokenInfo() {
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setAccess_token(userconfig.getString("access_token", ""));
        tokenInfo.setRefresh_token(userconfig.getString("refresh_token", ""));
        //tokenInfo.setToken_type(userconfig.getString("token_type", ""));
        tokenInfo.setExpires_in(userconfig.getLong("expires_in", 0));
        return tokenInfo;
    }

    public static void setAccessToken(String access_token) {
        SharedPreferences.Editor editor = userconfig.edit();
        editor.putString("access_token", access_token);
        editor.commit();
    }

    public static String getAccessToken() {
        return userconfig.getString("access_token", "");
    }

    /**
     * 存储个人信息
     *
     * @param userInfo
     */
    public static void setUser(UserInfo userInfo) {
        SharedPreferences.Editor editor = userconfig.edit();
        if (userInfo.getFirstName() != null) {
            editor.putString("firstName", userInfo.getFirstName());
        }
        if (userInfo.getAdress() != null) {
            editor.putString("address", userInfo.getAdress());
        }
        if (userInfo.getEmail() != null) {
            editor.putString("email", userInfo.getEmail());
        }
        if (userInfo.getGender() != null) {
            editor.putString("gender", userInfo.getGender());
        }
        if (userInfo.getType() != null) {
            editor.putString("type", userInfo.getType());
        }
        if (userInfo.getHeadImageUrl() != null) {
            editor.putString("headImageUrl", userInfo.getHeadImageUrl());
        }
        if (userInfo.getLastName() != null) {
            editor.putString("lastName", userInfo.getLastName());
        }
        if (userInfo.getPhoneNumber() != null) {
            editor.putString("phoneNumber", userInfo.getPhoneNumber());
        }
        if (userInfo.getPreferdes() != null) {
            editor.putString("preferdes", userInfo.getPreferdes());
        }
        if (userInfo.getPrefertitle() != null) {
            editor.putString("prefertitle", userInfo.getPrefertitle());
        }
        if (userInfo.getUid() != null) {
            editor.putString("uid", userInfo.getUid());
        }
        if (userInfo.getPbId() != null) {
            editor.putString("pbId", userInfo.getPbId());
        }
        if (userInfo.getIsRecommend()!=null){
            editor.putBoolean("isRecommend",userInfo.getIsRecommend());
        }
        editor.commit();
    }

    /**
     * 获得个人信息
     *
     * @return
     */
    public static UserInfo getUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setFirstName(userconfig.getString("firstName", ""));
        userInfo.setAdress(userconfig.getString("address", ""));
        userInfo.setEmail(userconfig.getString("email", ""));
        userInfo.setGender(userconfig.getString("gender", ""));
        userInfo.setType(userconfig.getString("type", ""));
        userInfo.setHeadImageUrl(userconfig.getString("headImageUrl", ""));
        userInfo.setLastName(userconfig.getString("lastName", ""));
        userInfo.setPhoneNumber(userconfig.getString("phoneNumber", ""));
        userInfo.setPreferdes(userconfig.getString("preferdes", ""));
        userInfo.setPrefertitle(userconfig.getString("prefertitle", ""));
        userInfo.setUid(userconfig.getString("uid", ""));
        userInfo.setPbId(userconfig.getString("pbId", ""));
        userInfo.setIsRecommend(userconfig.getBoolean("isRecommend",false));

        return userInfo;
    }

    public static void setIsRecommend(Boolean isRecommend){
        SharedPreferences.Editor editor = userconfig.edit();
        editor.putBoolean("isRecommend", isRecommend);
        editor.commit();
    }

    /**
     * 电话号码
     *
     * @return
     */
    public static String getUserPhone() {
        return userconfig.getString("phoneNumber", "");
    }

    /**
     * 设置PbId号
     *
     * @param pbId
     */
    public static void setpbId(String pbId) {
        SharedPreferences.Editor editor = userconfig.edit();
        editor.putString("pbId", pbId);
        editor.commit();
    }

    /**
     * 清除数据
     */
    public static void clearUserData() {
        SharedPreferences.Editor editor = userconfig.edit();
        editor.clear().commit();
    }
    public static void setRefreshToken(String refreshToken){
        SharedPreferences.Editor editor = userconfig.edit();
        if (refreshToken != null) {
            editor.putString("refresh_token",refreshToken);
        }
        editor.commit();
    }
    /**
     * 是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        if (TextUtils.isEmpty(getTokenInfo().getAccess_token())) {
            return false;
        } else {
            return true;
        }
    }

    public static void setXcarAuthcode(String authcode) {
        SharedPreferences.Editor editor = userconfig.edit();
        editor.putString("xcar_authcode", authcode);
        editor.commit();
    }

    public static String getXcarAuthcode() {
        return userconfig.getString("xcar_authcode", "");
    }

}
