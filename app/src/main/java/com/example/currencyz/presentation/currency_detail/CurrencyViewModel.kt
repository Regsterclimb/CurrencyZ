package com.example.currencyz.presentation.currency_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyz.domain.calculations.CalculateHelper
import com.example.currencyz.domain.model.Constants.Constants.Companion.bigNumber
import com.example.currencyz.domain.model.Constants.Constants.Companion.isEmpty
import com.example.currencyz.domain.model.Constants.Constants.Companion.wrongInput
import com.example.currencyz.domain.model.RefactoredMyCurrency
import com.example.currencyz.domain.model.edit_text.EditTextHelper.Companion.countDigitAfterDot
import com.example.currencyz.domain.repository.CurrencyRepositoryImpl
import kotlinx.coroutines.launch
import java.util.*

class CurrencyViewModel(
    private val repository: CurrencyRepositoryImpl
) : ViewModel() {

    private val _myCurrencyLiveData = MutableLiveData<RefactoredMyCurrency>()
    val myCurrencyLiveData: LiveData<RefactoredMyCurrency> = _myCurrencyLiveData

    private val _isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading = _isLoadingLiveData

    private val _resultLiveData = MutableLiveData<String>()
    val resultLiveData = _resultLiveData

    private val _resultIsLoading = MutableLiveData(false)
    val resultIsLoading = _resultIsLoading


    fun getMyCurrency(id: String?) {
        try {
            viewModelScope.launch {
                _isLoadingLiveData.value = true
                _myCurrencyLiveData.value = repository.tryToFindId(id!!)
                _isLoadingLiveData.value = false
            }
        } catch (e: IllegalFormatException) {
            Log.e("logs", "IllegalFormatException in CurrencyViewModel")
        }
    }

    fun changeCurrency(input: String, value: Double) {
        viewModelScope.launch {
            _resultIsLoading.value = true
            when {
                input.isEmpty() -> resultLiveData.value = isEmpty
                countDigitAfterDot(input) -> resultLiveData.value = wrongInput
                input.length > 24 -> resultLiveData.value = bigNumber
                else -> {
                    resultLiveData.value = calculateIt(input, value)
                }
            }
            _resultIsLoading.value = false
        }
    }

    private fun calculateIt(str: String, value: Double): String =
        CalculateHelper.calculate(str, value)


}