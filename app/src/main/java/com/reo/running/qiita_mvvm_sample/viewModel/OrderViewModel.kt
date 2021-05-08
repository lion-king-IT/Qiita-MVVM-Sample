package com.reo.running.qiita_mvvm_sample.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reo.running.qiita_mvvm_sample.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {

    // 画像のidを保持
    private val _orderImage = MutableLiveData<Int>()
    val orderImage: MutableLiveData<Int>
        get() = _orderImage

    // 注文ボタンのテキストの状態を保持
    private val _orderText = MutableLiveData<String>()
    val orderText: LiveData<String>
        get() = _orderText

    // 会計ボタンのテキストの状態を保持
    private val _billText = MutableLiveData<String>()
    val billText: LiveData<String>
        get() = _billText

    // 注文ボタンのテキストの状態だけ初期値を設定
    init {
        _orderText.value = "入店"
    }

    // お客さんの行動
    fun orderTuna() {
        when (_orderText.value) {

            "入店", "再入店" -> {
                _orderImage.value = R.drawable.sushi_syokunin_man_mask
                _orderText.value = "マグロ"
                _billText.value = "お会計"
            }

            "マグロ" -> {
                _orderImage.value = R.drawable.sushi_akami
                _orderText.value = "完食"
            }

            "完食" -> {
                _orderImage.value = R.drawable.sushi_syokunin_man_mask
                _orderText.value = "マグロ"
            }

            "帰る" -> {
                viewModelScope.launch {
                    _orderImage.value = R.drawable.tsugaku
                    _orderText.value = ""
                    delay(1000)
                    _orderImage.value = R.drawable.home_kitaku_girl
                    _orderText.value = "再入店"
                }
            }
        }
    }

    // お会計ボタンを押したときの処理
    fun pay() {
        when (_billText.value) {
            "お会計" -> {
                _orderImage.value = R.drawable.message_okaikei_ohitori
                _orderText.value = "帰る"
                _billText.value = ""
            }
        }
    }

}
