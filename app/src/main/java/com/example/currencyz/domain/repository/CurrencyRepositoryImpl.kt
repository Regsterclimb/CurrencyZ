package com.example.currencyz.domain.repository

import com.example.currencyz.data.remote.dto.CurrencyDto
import com.example.currencyz.data.repository.CurrencyDataRepositoryImpl
import com.example.currencyz.di.CurrencyDataRepositoryProvider
import com.example.currencyz.domain.model.MyCurrency
import com.example.currencyz.domain.model.toMyCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class CurrencyRepositoryImpl : CurrencyRepository,
    CurrencyDataRepositoryProvider {

    private val dataRepository = CurrencyDataRepositoryImpl()

    override suspend fun loadCurrencyList(): List<MyCurrency> =
        withContext(Dispatchers.IO) {
            val loadedList = dataRepository.loadDataCurrencyDtoList()
            val list = refactorList(loadedList)
            list
        }

    // refactor list with Dto to MyCurrency
    private suspend fun refactorList(list: List<CurrencyDto>): List<MyCurrency> =
        withContext(Dispatchers.IO) {
            list.map { currencyDto -> currencyDto.toMyCurrency() }
        }

    override fun provideCurrencyDataRepository(): CurrencyDataRepositoryImpl = dataRepository


}


