package com.reo.running.qiita_mvvm_sample.view

import android.widget.ImageView
import androidx.databinding.BindingAdapter


@BindingAdapter("android:src")
fun ImageView.setImageResource(resourceId: Int) {
    setImageResource(resourceId)
}