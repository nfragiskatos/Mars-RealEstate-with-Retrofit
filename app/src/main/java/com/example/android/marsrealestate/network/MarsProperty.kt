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

import com.squareup.moshi.Json

/**
 * This will be a data class to store parsed JSON results.
 *
 * This is used by the Moshi library.
 *
 * Typically the property names must match what's in the JSON object, but you can use the @Json annotation to customize.
 *
 * Here's an example JSON response from the API
 *
 *  {
 *      "price":8000000,
 *      "id":"424906",
 *      "type":"rent",
 *      "img_src":"http://mars.jpl.nasa.gov/msl-raw-images/msss/01000/mcam/1000ML0044631300305227E03_DXXX.jpg"
 *  }
 */
data class MarsProperty(val id: String, @Json(name = "img_src") val imgSrcUrl: String, val type: String, val price: Double)
