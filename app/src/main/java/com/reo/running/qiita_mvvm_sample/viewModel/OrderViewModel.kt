package com.reo.running.qiita_mvvm_sample.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reo.running.qiita_mvvm_sample.R

class OrderViewModel : ViewModel() {

    // 画像のidを保持
    private val _orderImage = MutableLiveData<Int>() // 板前さんだけが持つ
    val orderImage: LiveData<Int> // お客さんに渡す方、変更できない
        get() = _orderImage

    // 注文ボタンのテキストの状態を保持
    private val _orderText = MutableLiveData<String>()
    val orderText: LiveData<String>
        get() = _orderText

    // 会計ボタンのテキストを保持
    private val _billText = MutableLiveData<String>()
    val billText: LiveData<String>
        get() = _billText

    init {
        _orderText.value = "マグロ"
        _billText.value = "会計"
        _orderImage.value = R.drawable.sushi_syokunin_man_mask
    }

    // 注文する処理
    fun orderTuna() {
        if (_orderText.value.equals("マグロ")) {
            _orderImage.value = R.drawable.sushi_akami
            _orderText.value = "完食"
        } else {
            _orderImage.value = R.drawable.sushi_syokunin_man_mask
            _orderText.value = "マグロ"
        }
    }

    // お会計処理
    fun pay() {
        _orderImage.value = R.drawable.message_okaikei_ohitori
    }

}
