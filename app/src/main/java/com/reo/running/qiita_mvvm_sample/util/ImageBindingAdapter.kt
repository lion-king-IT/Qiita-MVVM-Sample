package com.reo.running.qiita_mvvm_sample.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter


@BindingAdapter("imageResource")
fun ImageView.setImageResource(resourceId: Int) {
    setImageResource(resourceId)
}
