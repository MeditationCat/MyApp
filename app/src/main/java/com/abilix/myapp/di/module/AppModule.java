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

package com.abilix.myapp.di.module;

import android.content.Context;

import com.abilix.myapp.MyApp;
import com.abilix.myapp.di.scope.ContextLife;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by pp.tai on 15:59 2018/04/04.
 */
@Module
public class AppModule {

    private MyApp mApplication;

    public AppModule(MyApp application) {
        this.mApplication = application;
    }

    @Provides
    @Singleton
    @ContextLife("Application")
    public Context provideAppContext() {
        return mApplication;
    }
}
