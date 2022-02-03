package com.example.currencyz.presentation.ViewModel

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyz.domain.model.Constants.Constants
import com.example.currencyz.domain.model.MyCurrency
import com.example.currencyz.domain.model.RefactoredMyCurrency
import com.example.currencyz.domain.model.toRefactoredMyCurrency
import com.example.currencyz.domain.repository.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*


class CurrencyListViewModel(
    private val repository: CurrencyRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {


    private var _mutableCurrencyListLiveData = MutableLiveData<List<MyCurrency>>()

    val currencyListLiveData: LiveData<List<MyCurrency>> = _mutableCurrencyListLiveData

    private val _myCurrencyLiveData = MutableLiveData<RefactoredMyCurrency>()

    val myCurrencyLiveData: LiveData<RefactoredMyCurrency> = _myCurrencyLiveData

    fun loadCurrencyListApi() {
        viewModelScope.launch {
            saveData(repository.loadCurrencyList())
            _mutableCurrencyListLiveData.postValue(repository.loadCurrencyList())
        }

    }

    fun loadCurrencyListSp() {
        viewModelScope.launch {
            val cachedList = restoreData()
            Log.d("restore1" ,"$cachedList")
            if (cachedList != null) {
                _mutableCurrencyListLiveData.postValue(cachedList)
            } else {
                val loadedList = repository.loadCurrencyList()
                _mutableCurrencyListLiveData.postValue(loadedList)
                saveData(loadedList)
            }
        }
    }

    fun getMyCurrency(id: String?) {
        try {
            id!!
            viewModelScope.launch {
                if (restoreData() != null) {

                    _myCurrencyLiveData.postValue(tryToFindId(id))
                } else {
                    _myCurrencyLiveData.postValue(repository.getCurrency(id))
                }
            }
        } catch (e: IllegalFormatException) {
            Log.e("logs", "IllegalFormatException in CurrencyViewModel")
        }

    }

    // saving data to SP add new or rewrite last
    private fun saveData(list: List<MyCurrency>) {
        val jsonData = Json.encodeToString(list)
        sharedPreferences.edit {
            putString(Constants.keySp, jsonData)
            apply()
        }
    }

    private fun restoreData(): List<MyCurrency>?  {
            val jsonData = sharedPreferences.getString(Constants.keySp, null)
            if (jsonData != null) {
                Log.d("restore" ,"${Json.decodeFromString<List<MyCurrency>>(jsonData)}")
                return Json.decodeFromString<List<MyCurrency>>(jsonData)
            }
            return null
        }

    private suspend fun tryToFindId(id: String?): RefactoredMyCurrency? =
        withContext(Dispatchers.Default) {
            Log.d("tryToFind", "id = ${restoreData()?.find { it.id == id }?.toRefactoredMyCurrency().toString()}, id")
            restoreData()?.find { it.id == id }?.toRefactoredMyCurrency()

        }

}