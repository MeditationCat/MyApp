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

package com.abilix.myapp.api.exception;

import com.abilix.myapp.bean.ResponseErrors;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.ParseException;

import retrofit2.HttpException;

public class ExceptionEngine {
    public static ApiException handleException(Throwable e){
        ApiException ex;
        if (e instanceof HttpException){             //HTTP错误
            HttpException httpException = (HttpException) e;

            if(httpException.code() == 400 || httpException.code() == 401) {
                try {
                    String errorStr = httpException.response().errorBody().string();
                    ResponseErrors errors = new Gson().fromJson(errorStr, ResponseErrors.class);
                    ex = new ApiException(e,errors.getErrors().get(0).getType());
                    String errMessage = errors.getErrors().get(0).getMessage();
                    if(errMessage == null){
                        errMessage = errors.getErrors().get(0).getType();
                    }
                    ex.setMessage(errMessage);
                    String type=errors.getErrors().get(0).getType();
                    if (type.equals("19102")){
                        ex.setMessage("所选城市未提供服务");
                    }
                    if (ex.getMessage().equals("token和phoneNumber不匹配")){
                        ex.setMessage("登录失效,请重新登录");
                    }
                    /*if(errors.getErrors().get(0).getType().equals("InvalidTokenError")){
                        String refreshToken=UserInfoSharedPreferences.getTokenInfo().getRefresh_token();
                        UserInfoSharedPreferences.clearUserData();
                        UserInfoSharedPreferences.setRefreshToken(refreshToken);
                        ex.setMessage("登录失效,请重新登录");
                    }else if (errors.getErrors().get(0).getType().equals("MissingServletRequestParameterError")){//服务器丢失参数的问题
                        ex.setMessage("网络请求错误，请重试");
                    }else if (errors.getErrors().get(0).getType().equals("InvalidGrantError")){
                        UserInfoSharedPreferences.clearUserData();
                        ex.setMessage("登录失效，请重新登录");
                    }*/
                    return ex;
                } catch (Exception exception) {
                    exception.printStackTrace();
                    ex = new ApiException(e, ERROR.HTTP_ERROR);
                    ex.setMessage("服务器错误");  //均视为网络错误
                    return ex;
                }
            }else {
                ex = new ApiException(e, ERROR.HTTP_ERROR);
                ex.setMessage("服务器错误");  //均视为网络错误
                return ex;
            }

        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException){
            ex = new ApiException(e, ERROR.PARSE_ERROR);
            ex.setMessage("解析错误");            //均视为解析错误
            return ex;
        }else if(e instanceof ConnectException){
            ex = new ApiException(e, ERROR.NETWORD_ERROR);
            ex.setMessage("网络错误，请检查网络连接");  //均视为网络错误
            return ex;
        }else if(e instanceof SocketTimeoutException){
            ex = new ApiException(e, ERROR.NETWORD_ERROR);
            ex.setMessage("连接超时");
            return ex;
        }else {
            ex = new ApiException(e, ERROR.UNKNOWN);
            if (e != null) {
                ex.setMessage(e.getMessage());          //未知错误
            } else {
                ex.setMessage("未知错误");
            }
            return ex;
        }
    }

}
