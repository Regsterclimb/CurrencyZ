package com.example.currencyz.domain.repository

import com.example.currencyz.domain.model.MyCurrency

interface CurrencyRepository {

    suspend fun loadCurrencyList(): List<MyCurrency> // calling to api every time after configurationchange TODO()

    fun saveData(list: List<MyCurrency>)

    fun restoreData(): List<MyCurrency>?
}