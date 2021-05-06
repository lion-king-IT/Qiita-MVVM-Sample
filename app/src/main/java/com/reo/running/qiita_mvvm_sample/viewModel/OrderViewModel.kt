package com.reo.running.qiita_mvvm_sample.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderViewModel : ViewModel() {
    // ボタンテキストの状態と画像の状態を保持
    val orderImage: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
}

/*




 */