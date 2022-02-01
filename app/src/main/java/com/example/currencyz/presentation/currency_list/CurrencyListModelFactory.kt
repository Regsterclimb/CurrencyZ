package com.example.currencyz.presentation.currency_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencyz.domain.repository.CurrencyRepository

class CurrencyListModelFactory(private val repository: CurrencyRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            CurrencyListViewModel::class.java -> {
                CurrencyListViewModel(repository)
            }
            else -> throw IllegalStateException("something wrong modelClass at CurrencyListViewModelFactory")
        }
        return viewModel as T
    }
}