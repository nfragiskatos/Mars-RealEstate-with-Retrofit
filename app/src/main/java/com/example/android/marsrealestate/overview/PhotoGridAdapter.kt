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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsrealestate.databinding.GridViewItemBinding
import com.example.android.marsrealestate.network.MarsProperty

/**
 * Custom implementation of a [ListAdapter] that works specifically on [MarsProperty] objects.
 * Also uses a custom implementation of [DiffUtil.ItemCallback] that the [ListAdapter] requires to compare [MarsProperty] objects
 */
class PhotoGridAdapter : ListAdapter<MarsProperty, PhotoGridAdapter.MarsPropertyViewHolder>(DiffCallback) {

    /**
     * Mostly boilerplate code.
     * Just need to make sure we return a [PhotoGridAdapter.MarsPropertyViewHolder] which is our custom implementation of
     * [RecyclerView.ViewHolder].
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoGridAdapter.MarsPropertyViewHolder {
        return MarsPropertyViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * We want to marry the [MarsProperty] item that the [PhotoGridAdapter] is currently working on with the [MarsPropertyViewHolder] that
     * [PhotoGridAdapter] is trying to populate with information.
     *
     * 1. Get the [MarsProperty] from the backing list since we know the index of it.
     * 2. Take the [MarsPropertyViewHolder] that was passed into us and bind the [MarsProperty] to it.
     *
     * @param holder The ViewHolder we are trying to populate with information.
     * @param position The position of the current working item.
     */
    override fun onBindViewHolder(holder: PhotoGridAdapter.MarsPropertyViewHolder, position: Int) {
        val marsProperty = getItem(position)
        holder.bind(marsProperty)
    }

    /**
     * Custom implementation of [RecyclerView.ViewHolder] to be used by the [PhotoGridAdapter]
     *
     * The [GridViewItemBinding] is a reference to the variable declared in the [grid_view_item.xml] layout.
     */
    class MarsPropertyViewHolder(private var binding: GridViewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        /**
         * The [binding] needs to have its information set, in this case the [MarsProperty]
         * Then we can execute all the pending binding to force a refresh of the ViewHolder if it needs to update its information.
         * @param marsProperty The list item that we are using to populate the [MarsPropertyViewHolder]
         */
        fun bind(marsProperty: MarsProperty) {
            binding.property = marsProperty
            binding.executePendingBindings()
        }

    }

    /**
     * The [ListAdapter] needs to know how to compare the different values in its backing list. Therefore we create this
     * implementation of, essentially, a comparator to give to the [ListAdapter]
     */
    companion object DiffCallback :DiffUtil.ItemCallback<MarsProperty>() {

        /***
         * This compares the references to make sure they are indeed referring to the same object.
         */
        override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem === newItem
        }

        /**
         * For the MarsProperty case, each should have a unique Id, so we can just compare the id of each MarsProperty to know if
         * the contents are the same.
         */
        override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem.id == newItem.id
        }

    }
}