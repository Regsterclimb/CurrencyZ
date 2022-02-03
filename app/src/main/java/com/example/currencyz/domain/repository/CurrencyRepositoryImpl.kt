package com.example.currencyz.domain.repository

import android.content.Context
import com.example.currencyz.data.remote.dto.CurrencyDto
import com.example.currencyz.data.remote.dto.toMyCurrency
import com.example.currencyz.data.repository.CurrencyDataRepositoryImpl
import com.example.currencyz.di.CurrencyDataRepositoryProvider
import com.example.currencyz.domain.model.MyCurrency
import com.example.currencyz.domain.model.RefactoredMyCurrency
import com.example.currencyz.domain.model.toRefactoredMyCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class CurrencyRepositoryImpl() : CurrencyRepository,
    CurrencyDataRepositoryProvider {

    private val dataRepository = CurrencyDataRepositoryImpl() // bad idea need to fix TODO()

    private var myCurrencyList: List<MyCurrency>? = null

    override suspend fun loadCurrencyList(): List<MyCurrency> =
        withContext(Dispatchers.IO) { //saving to sharedPref
            val loadedList = dataRepository.loadDataCurrencyList()
            val list = refactorList(loadedList)
            list
        }

    override suspend fun getCurrency(id: String): RefactoredMyCurrency? =
        withContext(Dispatchers.IO) {
            val list = myCurrencyList ?: loadCurrencyList()
            findCurrency(id, list)?.toRefactoredMyCurrency()
        }

    // refactor list with Dto to MyCurrency
    private suspend fun refactorList(list: List<CurrencyDto>): List<MyCurrency> =
        withContext(Dispatchers.IO) {
            list.map { currencyDto -> currencyDto.toMyCurrency() }
        }

    private suspend fun findCurrency(id: String, list: List<MyCurrency>): MyCurrency? =
        withContext(Dispatchers.IO) {
            list.find { it.id == id }
        }


    override fun provideCurrencyDataRepository(): CurrencyDataRepositoryImpl = dataRepository


}


