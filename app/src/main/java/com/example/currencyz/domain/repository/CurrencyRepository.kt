package com.example.currencyz.domain.repository

import android.content.Context
import com.example.currencyz.data.remote.dto.CurrencyDto
import com.example.currencyz.data.remote.dto.toMyCurrency
import com.example.currencyz.data.repository.CurrencyDataRepositoryImpl
import com.example.currencyz.di.CurrencyDataRepositoryProvider
import com.example.currencyz.domain.model.MyCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface CurrencyRepository {

    suspend fun loadCurrencyList(): List<MyCurrency>

    suspend fun getCurrency()

}

class CurrencyRepositoryImpl() : CurrencyRepository,CurrencyDataRepositoryProvider {


    private val dataRepository = CurrencyDataRepositoryImpl()

    private var currencyList: List<MyCurrency>? = null


    override suspend fun loadCurrencyList() : List<MyCurrency>  = withContext(Dispatchers.IO) {
        val list = dataRepository.loadDataCurrencyList()
        currencyList = refractorList(list)
        refractorList(list)
    }

    override suspend fun getCurrency() {
        TODO("Not yet implemented")
    }

    private suspend fun refractorList(list:List<CurrencyDto>) : List<MyCurrency> = withContext(Dispatchers.IO) {
        list.map{ currencyDto -> currencyDto.toMyCurrency() }
    }



    override fun provideCurrencyDataRepository(): CurrencyDataRepositoryImpl = dataRepository


}


