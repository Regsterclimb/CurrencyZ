package com.example.currencyz.domain.repository

import com.example.currencyz.domain.model.MyCurrency
import com.example.currencyz.domain.model.RefactoredMyCurrency

interface CurrencyRepository {

    suspend fun loadCurrencyList(): List<MyCurrency> // calling to api every time after configurationchange TODO()

}