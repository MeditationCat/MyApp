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

package com.abilix.myapp.bean.douban;

/**
 * Created by pp.tai on 14:16 2018/05/17.
 */

public class TokenInfo {

    /**
     * access_token : a14afef0f66fcffce3e0fcd2e34f6ff4
     * expires_in : 3920
     * refresh_token : 5d633d136b6d56a41829b73a424803ec
     * douban_user_id : 1221
     */

    private String access_token;
    private long expires_in;
    private String refresh_token;
    private String douban_user_id;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getDouban_user_id() {
        return douban_user_id;
    }

    public void setDouban_user_id(String douban_user_id) {
        this.douban_user_id = douban_user_id;
    }
}
