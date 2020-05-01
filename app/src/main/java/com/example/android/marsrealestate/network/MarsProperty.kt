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

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

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
 *
 *  One of the goals of this object is to be able to pass it between fragments. Specifically between the OverviewFragment and
 *  the DetailFragment. Since this is a complex object, and not just a simple value like integer or String, Android requires it
 *  to be turned into a Parcel (or Bundle for multiple objects).
 *  [MarsProperty] needs to extend the [Parcelable] interface.
 *  We can implement it manually, or have Android Studio implement it for us but there is still some overhead associated with
 *  either approach, because when creating and reading from Parcels, the arguments must always be written and read in the exact
 *  order.
 *  We would normally implement those methods., but if the object changes over time and new arguments are added we have
 *  to make sure that the read + write methods are still correct.
 *  Anyway, we can use the [Parcelize] annotation instead to manage all that for us, and we don't have to even have any
 *  implementation code.
 */
@Parcelize
data class MarsProperty(val id: String, @Json(name = "img_src") val imgSrcUrl: String, val type: String, val price: Double) :
        Parcelable {
    val isRental = type == "rent"
}
