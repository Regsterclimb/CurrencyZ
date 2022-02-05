package com.example.currencyz.domain.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import com.example.currencyz.data.remote.dto.CurrencyDto
import com.example.currencyz.data.repository.CurrencyDataRepositoryImpl
import com.example.currencyz.di.CurrencyDataRepositoryProvider
import com.example.currencyz.domain.model.Constants.Constants
import com.example.currencyz.domain.model.MyCurrency
import com.example.currencyz.domain.model.RefactoredMyCurrency
import com.example.currencyz.domain.model.toMyCurrency
import com.example.currencyz.domain.model.toRefactoredMyCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class CurrencyRepositoryImpl(appContext: Context) : CurrencyRepository,
    CurrencyDataRepositoryProvider {

    private val dataRepository = CurrencyDataRepositoryImpl()

    private val sharedPreferences =
        appContext.getSharedPreferences(Constants.sharedPreferences, MODE_PRIVATE)

    override suspend fun loadCurrencyList(): List<MyCurrency> = withContext(Dispatchers.IO) {
        refactorList(dataRepository.loadDataCurrencyDtoList())
    }

    override fun saveData(list: List<MyCurrency>) {
        sharedPreferences
            .edit {
                putString(Constants.keySp, Json.encodeToString(list))
                apply()
            }
    }

    override fun restoreData(): List<MyCurrency>? {
        if (sharedPreferences.getString(Constants.keySp, null) != null) {
            val json = Json { ignoreUnknownKeys = true }
            return json.decodeFromString<List<MyCurrency>>(
                sharedPreferences.getString(Constants.keySp, null)!!
            )
        }
        return null
    }

    private fun refactorList(list: List<CurrencyDto>): List<MyCurrency> {
        return list.map { currencyDto ->
            currencyDto.toMyCurrency()
        }
    }

    fun tryToFindId(
        id: String
    ): RefactoredMyCurrency? {
        return restoreData()?.find { it.id == id }?.toRefactoredMyCurrency()
    }

    override fun provideCurrencyDataRepository(): CurrencyDataRepositoryImpl = dataRepository

}


