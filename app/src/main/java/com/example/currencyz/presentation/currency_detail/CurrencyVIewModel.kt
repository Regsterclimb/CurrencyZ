package com.example.currencyz.presentation.currency_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyz.domain.model.MyCurrency
import com.example.currencyz.domain.model.RefactoredMyCurrency
import com.example.currencyz.domain.repository.CurrencyRepository
import kotlinx.coroutines.launch
import java.util.*

class CurrencyVIewModel(private val repository: CurrencyRepository) : ViewModel() {

    private val _myCurrencyLiveData = MutableLiveData<RefactoredMyCurrency>()

    val myCurrencyLiveData: LiveData<RefactoredMyCurrency> = _myCurrencyLiveData

    fun getMyCurrency(id: String?) {
        try {
            id!!
            viewModelScope.launch {
                _myCurrencyLiveData.postValue(repository.getCurrency(id))

            }
        } catch (e: IllegalFormatException) {
            Log.e("logs", "IllegalFormatException in CurrencyViewModel")
        }

    }


}