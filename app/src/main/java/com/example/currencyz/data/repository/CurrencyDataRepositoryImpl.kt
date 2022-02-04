package com.example.currencyz.data.repository

import com.example.currencyz.data.remote.dto.CurrencyDto
import com.example.currencyz.data.remote.dto.ValuteDto
import com.example.currencyz.data.remote.dto.apiData
import com.example.currencyz.data.remote.dto.currencies.toCurrencyDto
import com.example.currencyz.data.remote.network_module.NetworkModule
import com.example.currencyz.di.NetworkModuleProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CurrencyDataRepositoryImpl : NetworkModuleProvider, CurrencyDataRepository {

    private val networkModule = NetworkModule()

    override suspend fun loadDataCurrencyDtoList(): List<CurrencyDto> =
        withContext(Dispatchers.IO) {
            makeList()
        }

    private suspend fun loadApiData(): apiData = withContext(Dispatchers.IO) {
        networkModule.getAllData()
    }

    //first list with CurrencyDTO from Api
    private suspend fun makeList(): List<CurrencyDto> =
        withContext(Dispatchers.IO) {
            val valuteDto: ValuteDto = loadApiData().valuteDto //

            //возможно есть более изящный способ TODO() подумать поискать
            val list = listOf(
                valuteDto.aMD.toCurrencyDto(),
                valuteDto.aUD.toCurrencyDto(),
                valuteDto.aZN.toCurrencyDto(),
                valuteDto.bGN.toCurrencyDto(),
                valuteDto.bRL.toCurrencyDto(),
                valuteDto.bYN.toCurrencyDto(),
                valuteDto.cAD.toCurrencyDto(),
                valuteDto.cHF.toCurrencyDto(),
                valuteDto.cNY.toCurrencyDto(),
                valuteDto.cZK.toCurrencyDto(),
                valuteDto.dKK.toCurrencyDto(),
                valuteDto.eUR.toCurrencyDto(),
                valuteDto.gBP.toCurrencyDto(),
                valuteDto.hKD.toCurrencyDto(),
                valuteDto.hUF.toCurrencyDto(),
                valuteDto.iNR.toCurrencyDto(),
                valuteDto.jPY.toCurrencyDto(),
                valuteDto.kGS.toCurrencyDto(),
                valuteDto.kRW.toCurrencyDto(),
                valuteDto.kZT.toCurrencyDto(),
                valuteDto.mDL.toCurrencyDto(),
                valuteDto.nOK.toCurrencyDto(),
                valuteDto.pLN.toCurrencyDto(),
                valuteDto.rON.toCurrencyDto(),
                valuteDto.sEK.toCurrencyDto(),
                valuteDto.sGD.toCurrencyDto(),
                valuteDto.tJS.toCurrencyDto(),
                valuteDto.tMT.toCurrencyDto(),
                valuteDto.tRY.toCurrencyDto(),
                valuteDto.uAH.toCurrencyDto(),
                valuteDto.uSD.toCurrencyDto(),
                valuteDto.uZS.toCurrencyDto(),
                valuteDto.xDR.toCurrencyDto(),
                valuteDto.zAR.toCurrencyDto(),
            ).sortedBy { it.name }
            list
        }

    override fun provideNetworkModule(): NetworkModule = networkModule

}