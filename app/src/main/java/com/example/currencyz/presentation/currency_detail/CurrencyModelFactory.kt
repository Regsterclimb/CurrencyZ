package com.example.currencyz.presentation.currency_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencyz.domain.repository.CurrencyRepository

class CurrencyModelFactory(private val repository: CurrencyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            CurrencyVIewModel::class.java -> CurrencyVIewModel(repository)

            else -> throw IllegalStateException("something wrong modelClass at CurrencyViewModelFactory")
        }
        return  viewModel as T
    }
}