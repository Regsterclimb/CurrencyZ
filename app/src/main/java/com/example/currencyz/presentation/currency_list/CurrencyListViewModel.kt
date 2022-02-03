package com.example.currencyz.presentation.currency_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyz.domain.model.MyCurrency
import com.example.currencyz.domain.repository.CurrencyRepository
import kotlinx.coroutines.launch


class CurrencyListViewModel(private val repository: CurrencyRepository) : ViewModel() {

    private var _mutableCurrencyListLiveData = MutableLiveData<List<MyCurrency>>()

    val currencyListLiveData: LiveData<List<MyCurrency>> = _mutableCurrencyListLiveData

    fun loadCurrencyListApi() {
        viewModelScope.launch {
            _mutableCurrencyListLiveData.postValue(repository.loadCurrencyList())
        }

    }

    fun loadCurrencyListSp() {
        viewModelScope.launch {
            _mutableCurrencyListLiveData.postValue(repository.getCurrencyListSp())
        }
    }

}