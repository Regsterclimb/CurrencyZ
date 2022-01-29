package com.example.currencyz.data.repository

import com.example.currencyz.data.remote.dto.CurrencyDto

interface CurrencyDataRepository {

    suspend fun loadDataCurrency(id: String) : CurrencyDto?

    suspend fun loadDataCurrencyList(): List<CurrencyDto>

}