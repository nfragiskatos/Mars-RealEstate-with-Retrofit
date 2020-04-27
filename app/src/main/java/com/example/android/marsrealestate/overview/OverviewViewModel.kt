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

package com.example.android.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.marsrealestate.network.MarsApiService
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.*

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the status of the most recent request
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the request status String
    val response: LiveData<String>
        get() = _status

    // MarsProperty to be loaded (just a single item for now.
    private val _property = MutableLiveData<MarsProperty>()

    val property: LiveData<MarsProperty>
        get() = _property

    /**
     * Need a job and scope now since we are working with Coroutines
     */
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getMarsRealEstateProperties()
    }

    /**
     * Sets the value of the status LiveData to the Mars API status.
     *
     * Here we are going to use Coroutines instead of Callbacks
     */
    private fun getMarsRealEstateProperties() {
        coroutineScope.launch {
            val getPropertiesDeferred = MarsApiService.MarsApi.retrofitService.getProperties()
            try
            {
                var listResult = getPropertiesDeferred.await()
                if (listResult.size > 0) {
                    _property.value = listResult[0]
                }
            } catch (e: Exception)
            {
                _status.value = "Failure: ${e.message}"
            }
        }
    }

    /**
     * Want to make sure we cancel the [Job] when the [OverviewViewModel] is destroyed since the [OverviewFragment] will be gone.
     */
    override fun onCleared()
    {
        super.onCleared()
        coroutineScope.cancel()
    }
}
