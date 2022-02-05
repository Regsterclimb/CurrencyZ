package com.example.currencyz.presentation

import android.app.Application
import com.example.currencyz.domain.repository.CurrencyRepositoryImpl

class App : Application() {

    val repository by lazy { CurrencyRepositoryImpl(this) }

    override fun onCreate() {
        app = this
        super.onCreate()
    }

    companion object {
        lateinit var app: App
        val instance: App get() = app
    }

}