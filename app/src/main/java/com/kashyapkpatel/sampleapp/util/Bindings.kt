package com.kashyapkpatel.sampleapp.util

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout


@BindingAdapter(value = ["setAdapter"])
fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>) {
    this.run {
        this.adapter = adapter
    }
}

@BindingAdapter(value = ["setMoviePostureUrl"])
fun ImageView.bindImage(posturePath: String) {
    this.run {
        Glide.with(this.context).load("https://image.tmdb.org/t/p/w500/$posturePath").into(this)
    }
}

@BindingAdapter(value = ["setErrorText"])
fun TextInputLayout.bindError(error: String?) {
    this.run {
        this.error = error
    }
}