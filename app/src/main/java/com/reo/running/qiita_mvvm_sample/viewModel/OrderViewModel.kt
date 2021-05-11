package com.reo.running.qiita_mvvm_sample.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reo.running.qiita_mvvm_sample.R
import com.reo.running.qiita_mvvm_sample.model.Sushi
import com.reo.running.qiita_mvvm_sample.model.SushiDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OrderViewModel @ViewModelInject constructor(private val sushiDao: SushiDao) : ViewModel() {

    // 画像のidを保持
    private val _orderImage = MutableLiveData<Int>()
    val orderImage: LiveData<Int>
        get() = _orderImage

    // 表示する値段を保持
    private val _cashDisplay = MutableLiveData<String>()
    val cashDisplay: LiveData<String>
        get() = _cashDisplay

    // 注文ボタンのテキストの状態を保持
    private val _orderText = MutableLiveData<String>()
    val orderText: LiveData<String>
        get() = _orderText

    // 会計ボタンのテキストの状態を保持
    private val _billText = MutableLiveData<String>()
    val billText: LiveData<String>
        get() = _billText

    private val _tunaCount = MutableLiveData<Int>()

    private val _customerType = MutableLiveData(CustomerType.ENTER)

    // 注文ボタンのテキストの状態だけ初期値を設定
    init {
        _orderText.value = "入店"
        _orderImage.value = R.drawable.sushi_syokunin_man_mask
        _tunaCount.value = 0
    }


    // お客さんの行動
    fun orderTuna() {
        when (_customerType.value) {
            CustomerType.ENTER, CustomerType.RE_ENTER -> {
                _orderImage.value = R.drawable.sushi_syokunin_man_mask
                _orderText.value = "マグロ"
                _billText.value = "お会計"
                _customerType.value = CustomerType.EAT_TUNA
            }

            CustomerType.EAT_TUNA -> {
                _orderImage.value = R.drawable.sushi_akami
                _orderText.value = "完食"
                _customerType.value = CustomerType.COMPLETED_EAT
                _tunaCount.value?.plus(1)
            }

            CustomerType.COMPLETED_EAT -> {
                _orderImage.value = R.drawable.sushi_syokunin_man_mask
                _orderText.value = "マグロ"
                _customerType.value = CustomerType.EAT_TUNA
            }

            CustomerType.GO_HOME -> {
                viewModelScope.launch {
                    _orderImage.value = R.drawable.tsugaku
                    _orderText.value = ""
                    _cashDisplay.value = ""
                    delay(1000)
                    _orderImage.value = R.drawable.tsugaku
                    _orderText.value = "再入店"
                    _customerType.value = CustomerType.RE_ENTER
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
                _customerType.value = CustomerType.GO_HOME
                viewModelScope.launch(Dispatchers.IO) {
                    val history = Sushi(
                        0,
                        _tunaCount.value.toString(),
                        _tunaCount.value?.times(100).toString()
                    )
                    sushiDao.insertSushi(history)
                    _cashDisplay.value =
                        history.orderHistory + "皿\n" + history.price
                }
            }
        }
    }

    enum class CustomerType {
        ENTER,
        RE_ENTER,
        EAT_TUNA,
        COMPLETED_EAT,
        GO_HOME
    }

}
