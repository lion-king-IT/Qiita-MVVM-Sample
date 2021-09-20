package com.reo.running.qiita_mvvm_sample.view

import android.widget.ImageView
import androidx.databinding.BindingAdapter


@BindingAdapter("imageResource")
fun ImageView.setImageResourcesId(resourceId: Int) {
    setImageResource(resourceId)
}