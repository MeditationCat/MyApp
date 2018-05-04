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

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;

import com.abilix.myapp.common.Constants;
import com.abilix.myapp.di.component.AppComponent;
import com.abilix.myapp.di.component.DaggerAppComponent;
import com.abilix.myapp.di.module.AppModule;
import com.abilix.myapp.greendao.DaoMaster;
import com.abilix.myapp.greendao.DaoSession;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.greendao.query.QueryBuilder;


/**
 * Created by pp.tai on 16:09 2018/04/04.
 */

public class MyApp extends Application {

    private static final String TAG = "MyApp";

    private RefWatcher mRefWatcher;
    private AppComponent mAppComponent;

    private static Context mContext;

    private static DaoSession mDaoSession;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        //init LeakCanary
        initLeakCanary();
        //init Logger
        initLogger();
        //init StrictMode
        initStrictMode();
        //init AppComponent
        initAppComponent();
        //init ActivityLifeCycleLogs
        initActivityLifeCycleLogs();
    }

    private void initLeakCanary() {
        if (BuildConfig.DEBUG && !LeakCanary.isInAnalyzerProcess(this)) {
            mRefWatcher = LeakCanary.install(this);
        } else {
            mRefWatcher = RefWatcher.DISABLED;
        }
    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                //.showThreadInfo(true)   //(Optional) Whether to show thread info or not. Default is true
                .methodCount(1)         //(Optional) How many method line to show. Default is 2
                //.methodOffset(7)        //(Optional) Hides internal method calls up to offset. Default 5
                //.logStrategy(customLog) //(Optional) Change the logStrategy to print out. Default Logcat
                //.tag(TAG)                 //(Optional)
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

        //将Log日志保存到文件中
        FormatStrategy cvsFormatStrategy = CsvFormatStrategy.newBuilder()
                //.logStrategy(val)
                //.dateFormat()
                .tag(TAG)
                .build();
        Logger.addLogAdapter(new DiskLogAdapter(cvsFormatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

    private void initStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    //.penaltyDialog()
                    .build());

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }
    }

    private void initAppComponent() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    private void initActivityLifeCycleLogs() {
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Logger.v("=========onActivityCreated(): " + activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Logger.v("=========onActivityStarted(): " + activity);
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Logger.v("=========onActivityResumed(): " + activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Logger.v("=========onActivityPaused(): " + activity);
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Logger.v("=========onActivityStopped(): " + activity);
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Logger.v("=========onActivitySaveInstanceState(): " + activity);
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Logger.v("=========onActivityDestroyed(): " + activity);
            }
        });
    }

    private void setupDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constants.DB_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
        // 在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
        QueryBuilder.LOG_SQL = BuildConfig.DEBUG;
        QueryBuilder.LOG_VALUES = BuildConfig.DEBUG;
    }

    public static RefWatcher getRefWatcher(Context context) {
        MyApp myApp = (MyApp) context.getApplicationContext();
        return myApp.mRefWatcher;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }


}
