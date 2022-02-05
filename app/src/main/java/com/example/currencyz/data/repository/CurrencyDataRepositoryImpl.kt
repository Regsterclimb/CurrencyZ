package com.example.currencyz.data.repository

import android.content.Context
import com.example.currencyz.data.remote.dto.CurrencyDto
import com.example.currencyz.data.remote.dto.responses.CurrencyResponse
import com.example.currencyz.data.remote.dto.toCurrencyDto
import com.example.currencyz.data.remote.network_module.NetworkModule
import com.example.currencyz.di.NetworkModuleProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CurrencyDataRepositoryImpl() : NetworkModuleProvider, CurrencyDataRepository {

    private val networkModule = NetworkModule()

    override suspend fun loadDataCurrencyDtoList(): List<CurrencyDto> =
        withContext(Dispatchers.IO) {
            makeCurrencyResponseList().map {
                currencyResponse ->  currencyResponse.toCurrencyDto()
            }
        }

    private suspend fun makeCurrencyResponseList(): List<CurrencyResponse>  {
        return networkModule.getAllData().valute.values.toList()
    }

    override fun provideNetworkModule(): NetworkModule = networkModule

}