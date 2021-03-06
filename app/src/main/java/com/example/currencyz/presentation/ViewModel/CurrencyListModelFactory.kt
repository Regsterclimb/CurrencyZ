package com.example.currencyz.presentation.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencyz.presentation.App

class CurrencyListModelFactory(
    private val applicationContext: Context
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            CurrencyListViewModel::class.java -> {
                CurrencyListViewModel(repository = (applicationContext as App).repository)
            }
            else -> throw IllegalStateException("something wrong modelClass at CurrencyListViewModelFactory")
        }
        return viewModel as T
    }

}