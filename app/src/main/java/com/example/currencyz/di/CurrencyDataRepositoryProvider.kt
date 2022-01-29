package com.example.currencyz.di

import com.example.currencyz.data.repository.CurrencyDataRepository
import com.example.currencyz.data.repository.CurrencyDataRepositoryImpl

interface CurrencyDataRepositoryProvider {

    fun provideCurrencyDataRepository() : CurrencyDataRepository
}