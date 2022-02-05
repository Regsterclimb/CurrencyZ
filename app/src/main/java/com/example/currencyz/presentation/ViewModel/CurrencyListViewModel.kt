package com.example.currencyz.presentation.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyz.domain.model.MyCurrency
import com.example.currencyz.domain.repository.CurrencyRepositoryImpl
import kotlinx.coroutines.launch


class CurrencyListViewModel(
    private val repository: CurrencyRepositoryImpl
) : ViewModel() {

    private val _mutableCurrencyListLiveData = MutableLiveData<List<MyCurrency>>()
    val currencyListLiveData: LiveData<List<MyCurrency>> = _mutableCurrencyListLiveData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    init {
        loadCurrencyListSp()
    }

    fun loadCurrencyListApi() {
        viewModelScope.launch {
            _isLoading.value = true
            val list = repository.loadCurrencyList()
            repository.saveData(list)
            _mutableCurrencyListLiveData.postValue(list)
            _isLoading.value = false
        }
    }

    private fun loadCurrencyListSp() {
        viewModelScope.launch {
            _isLoading.value = true
            val cachedList = repository.restoreData()
            if (cachedList != null) {
                _mutableCurrencyListLiveData.postValue(cachedList!!)
            } else {
                loadCurrencyListApi()
            }
            _isLoading.value = false
        }
    }
}
