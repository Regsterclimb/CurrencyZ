package com.example.currencyz.presentation.currency_detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencyz.presentation.App

class CurrencyModelFactory(
    private val applicationContext: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            CurrencyViewModel::class.java -> CurrencyViewModel(
                repository = (applicationContext as App).repository)
            else -> throw IllegalStateException("something wrong modelClass at CurrencyListViewModelFactory")
        }
        return viewModel as T
    }
}
