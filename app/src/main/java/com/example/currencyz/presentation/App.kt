package com.example.currencyz.presentation

import android.app.Application
import com.example.currencyz.domain.repository.CurrencyRepositoryImpl

class App : Application() {
    val repository by lazy { CurrencyRepositoryImpl(this) }
}