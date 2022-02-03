package com.example.currencyz.presentation.ViewModel

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencyz.domain.model.Constants.Constants
import com.example.currencyz.domain.repository.CurrencyRepository

class CurrencyListModelFactory(
    private val repository: CurrencyRepository,
    private val applicationContext: Context
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            CurrencyListViewModel::class.java -> {
                val sharedPreferences = applicationContext.getSharedPreferences(Constants.sharedPreferences, MODE_PRIVATE)
                CurrencyListViewModel(repository,sharedPreferences = sharedPreferences)
            }
            else -> throw IllegalStateException("something wrong modelClass at CurrencyListViewModelFactory")
        }
        return viewModel as T
    }

}