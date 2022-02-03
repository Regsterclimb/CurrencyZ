package com.example.currencyz.domain.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import com.example.currencyz.data.remote.dto.CurrencyDto
import com.example.currencyz.data.remote.dto.toMyCurrency
import com.example.currencyz.data.repository.CurrencyDataRepositoryImpl
import com.example.currencyz.di.CurrencyDataRepositoryProvider
import com.example.currencyz.domain.model.MyCurrency
import com.example.currencyz.domain.model.RefactoredMyCurrency
import com.example.currencyz.domain.model.toRefactoredMyCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class CurrencyRepositoryImpl(context: Context) : CurrencyRepository,
    CurrencyDataRepositoryProvider {


    private val dataRepository = CurrencyDataRepositoryImpl() // bad idea need to fix TODO()

    private  var sharedPreferences = context.getSharedPreferences(sP, MODE_PRIVATE) // really bad idea))

    private var myCurrencyList: List<MyCurrency>? = null


    override suspend fun loadCurrencyList(): List<MyCurrency> =
        withContext(Dispatchers.IO) { //saving to sharedPref
            val loadedList = dataRepository.loadDataCurrencyList()
            val list = refactorList(loadedList)
            saveData(list)
            list
        }


    override suspend fun getCurrency(id: String): RefactoredMyCurrency? =
        withContext(Dispatchers.IO) {
            val list = myCurrencyList ?: loadCurrencyList()
            findCurrency(id, list)?.toRefactoredMyCurrency()
        }


    override suspend fun getCurrencyListSp(): List<MyCurrency> = withContext(Dispatchers.Default) {
        tryToRestore()
        val cachedList = myCurrencyList
        if (cachedList != null) {
            cachedList
        } else {
            val list = loadCurrencyList()
            saveData(list)
            list
        }
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

    // saving data to SP add new or rewrite last
    private fun saveData(list: List<MyCurrency>) {
        val jsonData = Json.encodeToString(list)
        sharedPreferences.edit {
            putString(key, jsonData)
            apply()
        }
    }

    private fun restoreData() {
        val jsonData = sharedPreferences.getString(key, null)
        if (jsonData != null) {
            val list = Json.decodeFromString<List<MyCurrency>>(jsonData)
            myCurrencyList = list
        }
    }

    private fun tryToRestore() {
        restoreData()
    }

    companion object {
        private const val sP = "MY_CURRENCY_SP"
        private const val key = "MY_CURRENCY_LIST"
    }

    override fun provideCurrencyDataRepository(): CurrencyDataRepositoryImpl = dataRepository


}


