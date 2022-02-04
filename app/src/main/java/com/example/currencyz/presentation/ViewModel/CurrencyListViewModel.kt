package com.example.currencyz.presentation.ViewModel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyz.domain.model.MyCurrency
import com.example.currencyz.domain.model.RefactoredMyCurrency
import com.example.currencyz.domain.repository.CurrencyRepository
import com.example.currencyz.domain.repository.SharedPrefData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


class CurrencyListViewModel(
    private val repository: CurrencyRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private var _mutableCurrencyListLiveData = MutableLiveData<List<MyCurrency>>()
    val currencyListLiveData: LiveData<List<MyCurrency>> = _mutableCurrencyListLiveData

    private val _myCurrencyLiveData = MutableLiveData<RefactoredMyCurrency>()
    val myCurrencyLiveData: LiveData<RefactoredMyCurrency> = _myCurrencyLiveData

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState = _loadingState

    private val _loadingStateCurrency = MutableLiveData<Boolean>()
    val loadingStateCurrency  = _loadingStateCurrency


    fun loadCurrencyListApi() {
        viewModelScope.launch {
            _loadingState.value = true

            val list = repository.loadCurrencyList()
            SharedPrefData.saveData(sharedPreferences, list)
            _mutableCurrencyListLiveData.postValue(list)

            _loadingState.value = false
        }

    }

    fun loadCurrencyListSp() {
        viewModelScope.launch {

            _loadingState.value = true
            val cachedList = SharedPrefData.restoreData(sharedPreferences)
            if (cachedList != null) {
                _mutableCurrencyListLiveData.postValue(cachedList)

            } else {

                val loadedList = repository.loadCurrencyList()
                _mutableCurrencyListLiveData.postValue(loadedList)
                SharedPrefData.saveData(sharedPreferences, loadedList)

            }
            _loadingState.value = false
        }
    }

    // don't forget to save data before calling this fun
    fun getMyCurrency(id: String?) {
        try {
            id!!

            viewModelScope.launch {
                _loadingStateCurrency.value = true

                _myCurrencyLiveData.postValue(
                    SharedPrefData.tryGetCurrencyData(sharedPreferences, id)
                )
                _loadingStateCurrency.value = false
            }

        } catch (e: IllegalFormatException) {
            Log.e("logs", "IllegalFormatException in CurrencyViewModel")
        }

    }
}
