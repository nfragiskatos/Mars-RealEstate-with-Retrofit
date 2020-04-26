/*
 * Copyright 2018, The Android Open Source Project
 *
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
 *
 */

package com.example.android.marsrealestate.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://mars.udacity.com/"

/**
 * Similar to what we did with [Retrofit] we want to create a [Moshi] object using the [Moshi] builder.
 *
 * We need the [KotlinJsonAdapterFactory] in order for Moshi to work properly with Kotlin.
 */
private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

/**
Build up the retrofit object.
Give it the converter factory and the url of the resource we are trying to reach.
 */
private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL).build()

/**
Service interface API to be given to retrofit so it can build the service for us.
 */
interface MarsApiService {

    /**
    GET annotation lets [Retrofit] know what this method should do. In this case a GET request.

    The value is an endpoint for the JSON response. Retrofit appends the "realestate" string to the base url
     */
    @GET("realestate")
    fun getProperties(): Call<String>

    /**
    Expose the retrofit service to the rest of the application.

    Lazily create the MarsApi object. Needs to be a singleton.

    In here we use the [Retrofit.create] method and pass it the [MarsApiService] interface that Retrofit needs to generate the service from.

     Calling [MarsApi.retrofitService] will return a [Retrofit] object that implements [MarsApiService]
     */
    object MarsApi{
        val retrofitService : MarsApiService by lazy {
            retrofit.create(MarsApiService::class.java)
        }
    }
}

