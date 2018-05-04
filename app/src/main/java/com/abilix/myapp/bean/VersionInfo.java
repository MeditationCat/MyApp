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

package com.abilix.myapp.bean;

import java.util.List;

public class VersionInfo {

    /**
     * code : false
     * message :
     * data : [{"packageName":"stm32","verName":"02.02.00.51","verCode":33685585,"apkname":"3505146118.bin","FilePath":"http://file.abilixstore.com/brainb/201804/16/c0d5cdde61488d86c0add35680cae5f2.bin","message_cn":"","message_en":""},{"packageName":"com.abilix.brainset","verName":"1.0.0.81","verCode":81,"apkname":"BrainSet.apk","FilePath":"http://file.abilixstore.com/brainb/201803/28/0d93111a3d3c6bfb0e093be896c39783.apk","message_cn":"","message_en":""},{"packageName":"com.abilix.explainer","verName":"1.0.0.46","verCode":46,"apkname":"C_Explainer.apk","FilePath":"http://file.abilixstore.com/brainb/201712/21/6a3c85640538a090fb57918f1f3a685b.apk","message_cn":"","message_en":""},{"packageName":"com.abilix.volumecontrol","verName":"1.0.0.8","verCode":8,"apkname":"C_VolumeControl.apk","FilePath":"http://file.abilixstore.com/brainb/201712/21/f8421a9c4900c4c8faa9a82f7f4bb0d4.apk","message_cn":"","message_en":""},{"packageName":"com.abilix.learn.rtspserver","verName":"1.0.0.14","verCode":14,"apkname":"C_RtspServer.apk","FilePath":"http://file.abilixstore.com/brainb/201712/21/ebb2bf6c11a55ef9a16a4af4e135673c.apk","message_cn":"","message_en":""},{"packageName":"com.abilix.control","verName":"1.0.13.16","verCode":1316,"apkname":"Control.apk","FilePath":"http://file.abilixstore.com/brainb/201803/28/5ffe3a8d4717ef3adda70a73ca45fc36.apk","message_cn":"","message_en":""},{"packageName":"com.abilix.brain","verName":"1.1.1.74","verCode":10174,"apkname":"Brain.apk","FilePath":"http://file.abilixstore.com/brainb/201803/28/a24093f6460b0e0dbd8fab5de91a28ed.apk","message_cn":"","message_en":""},{"packageName":"com.abilix.updateonlinetest","verName":"1.0.1.39","verCode":139,"apkname":"UpdateOnline.apk","FilePath":"http://file.abilixstore.com/brainb/201803/28/0e8026298a3413b46ecb2b788111fec6.apk","message_cn":"","message_en":""}]
     */

    private boolean code;
    private String message;
    private List<DataBean> data;

    public boolean isCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * packageName : stm32
         * verName : 02.02.00.51
         * verCode : 33685585
         * apkname : 3505146118.bin
         * FilePath : http://file.abilixstore.com/brainb/201804/16/c0d5cdde61488d86c0add35680cae5f2.bin
         * message_cn :
         * message_en :
         */

        private String packageName;
        private String verName;
        private int verCode;
        private String apkname;
        private String FilePath;
        private String message_cn;
        private String message_en;

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getVerName() {
            return verName;
        }

        public void setVerName(String verName) {
            this.verName = verName;
        }

        public int getVerCode() {
            return verCode;
        }

        public void setVerCode(int verCode) {
            this.verCode = verCode;
        }

        public String getApkname() {
            return apkname;
        }

        public void setApkname(String apkname) {
            this.apkname = apkname;
        }

        public String getFilePath() {
            return FilePath;
        }

        public void setFilePath(String FilePath) {
            this.FilePath = FilePath;
        }

        public String getMessage_cn() {
            return message_cn;
        }

        public void setMessage_cn(String message_cn) {
            this.message_cn = message_cn;
        }

        public String getMessage_en() {
            return message_en;
        }

        public void setMessage_en(String message_en) {
            this.message_en = message_en;
        }
    }
}
