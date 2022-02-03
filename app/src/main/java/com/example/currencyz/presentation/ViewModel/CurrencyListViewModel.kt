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
import com.example.currencyz.domain.repository.SharedLikeDb
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

    fun loadCurrencyListApi() {
        viewModelScope.launch {
            //setLoading
            val list = repository.loadCurrencyList()
            SharedLikeDb.saveData(sharedPreferences, list)
            _mutableCurrencyListLiveData.postValue(list)
            //loading success
        }

    }

    fun loadCurrencyListSp() {
        viewModelScope.launch {
            //setLoading
            val cachedList = SharedLikeDb.restoreData(sharedPreferences)
            if (cachedList != null) {
                //setLoading true
                _mutableCurrencyListLiveData.postValue(cachedList)
            } else {
                //setLoading true*/
                val loadedList = repository.loadCurrencyList()
                _mutableCurrencyListLiveData.postValue(loadedList)
                SharedLikeDb.saveData(sharedPreferences, loadedList)
            }
            //loading success
        }
        //setLoading false
    }

    // don't forget to save data before calling this fun
    fun getMyCurrency(id: String?) {
        try {
            id!!
            viewModelScope.launch {
                //setLoading
                _myCurrencyLiveData.postValue(
                    SharedLikeDb.tryGetCurrencyData(
                        sharedPreferences,
                        id
                    )
                )
                //loaded
            }
        } catch (e: IllegalFormatException) {
            Log.e("logs", "IllegalFormatException in CurrencyViewModel")
        }

    }
}
