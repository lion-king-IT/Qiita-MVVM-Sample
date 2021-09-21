package com.reo.running.qiita_mvvm_sample.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reo.running.qiita_mvvm_sample.R
import com.reo.running.qiita_mvvm_sample.model.Sushi
import com.reo.running.qiita_mvvm_sample.model.SushiDao
import com.reo.running.qiita_mvvm_sample.repository.SushiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val sushiDao: SushiDao,
    private val sushiRepository: SushiRepository
) : ViewModel() {

    private val _orderImage = MutableLiveData<Int>()
    val orderImage: LiveData<Int>
        get() = _orderImage

    private val _cashDisplay = MutableLiveData<String>()
    val cashDisplay: LiveData<String>
        get() = _cashDisplay

    private val _orderText = MutableLiveData<String>()
    val orderText: LiveData<String>
        get() = _orderText

    private val _billText = MutableLiveData<String>()
    val billText: LiveData<String>
        get() = _billText

    private val _customerType = MutableLiveData(CustomerType.ENTER)

    private val _tunaCount = MutableLiveData<Int>()

    private val _totalData = MutableLiveData<List<Sushi>>()

    private var lastIndex: Int = 0

    private var _totalDish: Int = 0

    private var _amountBill: Int = 0

    val sushiList = MutableLiveData<List<String>>()

    init {
        _orderImage.value = R.drawable.sushiya_building
        _orderText.value = "入店"
        _tunaCount.value = 0
        viewModelScope.launch {
            runCatching {
                sushiList.value = sushiRepository.getSushiList()
            }.onFailure {
                Log.w("debug", "$it")
            }

        }
    }

    fun orderTuna() {
        when (_customerType.value) {
            CustomerType.ENTER, CustomerType.RE_ENTER -> {
                _cashDisplay.value = ""
                _orderImage.value = R.drawable.sushi_syokunin_man_mask
                _orderText.value = "マグロ"
                _billText.value = "お会計"
                _customerType.value = CustomerType.EAT_TUNA
            }

            CustomerType.EAT_TUNA -> {
                _orderImage.value = R.drawable.sushi_akami
                _orderText.value = "完食"
                _customerType.value = CustomerType.COMPLETED_EAT
                _tunaCount.value = _tunaCount.value?.plus(1)
            }

            CustomerType.COMPLETED_EAT -> {
                _orderImage.value = R.drawable.sushi_syokunin_man_mask
                _orderText.value = "マグロ"
                _customerType.value = CustomerType.EAT_TUNA
            }

            CustomerType.GO_HOME -> {
                viewModelScope.launch(Dispatchers.IO) {

                    sushiDao.getAll().let {
                        lastIndex = it.lastIndex
                        _totalData.postValue(it)
                    }

                    withContext(Dispatchers.Main) {
                        for (i in 0..lastIndex) {
                            _totalDish += _totalData.value?.get(i)?.orderHistory!!
                            _amountBill += _totalData.value?.get(i)?.price!!.toInt()
                        }
                        _orderImage.value = R.drawable.tsugaku
                        _orderText.value = ""
                        _cashDisplay.value = ""
                        delay(1000)
                        _orderImage.value = R.drawable.home_kitaku_girl
                        _orderText.value = "再入店"
                        _customerType.value = CustomerType.RE_ENTER
                        _cashDisplay.value =
                            "総皿数：${_totalDish}皿\n請求総額:￥${_amountBill}"
                        _tunaCount.value = 0
                    }

                }

            }
        }
    }

    fun pay() {
        when (_billText.value) {
            "お会計" -> {
                viewModelScope.launch(Dispatchers.IO) {
                    sushiDao.insertSushi(
                        Sushi(
                            0,
                            _tunaCount.value,
                            calcSushi(_tunaCount)
                        )
                    )
                    _cashDisplay.postValue("${_tunaCount.value}皿\n￥${calcSushi(_tunaCount)}")
                    withContext(Dispatchers.Main) {
                        _orderImage.value = R.drawable.message_okaikei_ohitori
                        _orderText.value = "帰る"
                        _billText.value = ""
                        _customerType.value = CustomerType.GO_HOME
                    }
                }
            }
        }
    }

    private fun calcSushi(dishCount: MutableLiveData<Int>) = dishCount.value?.times(100).toString()


    enum class CustomerType {
        ENTER,
        RE_ENTER,
        EAT_TUNA,
        COMPLETED_EAT,
        GO_HOME
    }
}
