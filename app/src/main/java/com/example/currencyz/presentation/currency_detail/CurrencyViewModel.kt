package com.example.currencyz.presentation.currency_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyz.domain.model.RefactoredMyCurrency
import com.example.currencyz.domain.model.edit_text.EditTextHelper
import com.example.currencyz.domain.model.edit_text.EditTextInteractor
import com.example.currencyz.domain.model.edit_text.EditTextResult
import com.example.currencyz.domain.repository.CurrencyRepositoryImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class CurrencyViewModel(
    private val repository: CurrencyRepositoryImpl
) : ViewModel() {

    private val _myCurrencyLiveData = MutableLiveData<RefactoredMyCurrency>()
    val myCurrencyLiveData: LiveData<RefactoredMyCurrency> = _myCurrencyLiveData

    private val _resultLiveData = MutableLiveData<String>()
    val resultLiveData = _resultLiveData

    private val _loadingStateCurrency = MutableLiveData<Boolean>()
    val loadingStateCurrency = _loadingStateCurrency

    fun getMyCurrency(id: String?) {
        try {
            id
            viewModelScope.launch {
                _loadingStateCurrency.value = true
                _myCurrencyLiveData.value = repository.tryToFindId(id!!)
                _loadingStateCurrency.value = false
            }
        } catch (e: IllegalFormatException) {
            Log.e("logs", "IllegalFormatException in CurrencyViewModel")
        }
    }


}