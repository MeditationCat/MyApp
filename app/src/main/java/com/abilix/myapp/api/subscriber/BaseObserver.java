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

package com.abilix.myapp.api.subscriber;

import com.abilix.myapp.api.exception.ApiException;
import com.abilix.myapp.api.exception.ExceptionEngine;

import io.reactivex.Observer;


public abstract class BaseObserver<T> implements Observer<T> {

    @Override
    public void onError(Throwable e) {
        ApiException apiException = ExceptionEngine.handleException(e);
        onError(apiException);
    }

    protected abstract void onError(ApiException e);
}
