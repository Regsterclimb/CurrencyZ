package com.example.currencyz.di

import com.example.currencyz.domain.repository.CurrencyRepository
import com.example.currencyz.domain.repository.CurrencyRepositoryImpl

interface CurrencyRepositoryProvider {

    fun provideCurrencyRepository() : CurrencyRepository

}