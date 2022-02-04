package com.example.currencyz.domain.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.currencyz.domain.model.Constants.Constants
import com.example.currencyz.domain.model.MyCurrency
import com.example.currencyz.domain.model.RefactoredMyCurrency
import com.example.currencyz.domain.model.toRefactoredMyCurrency
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SharedPrefData {
    companion object {

        fun saveData(sharedPreferences: SharedPreferences, list: List<MyCurrency>) =
            SharedPrefData().saveDataInside(sharedPreferences, list)

        fun restoreData(sharedPreferences: SharedPreferences) =
            SharedPrefData().restoreDataInside(sharedPreferences)

        fun tryGetCurrencyData(sharedPreferences: SharedPreferences, id: String) =
            SharedPrefData().tryToFindId(sharedPreferences, id)
    }

    // saving data to SP add new or rewrite
    private fun saveDataInside(sharedPreferences: SharedPreferences, list: List<MyCurrency>) {
        val jsonData = Json.encodeToString(list)
        sharedPreferences.edit {
            putString(Constants.keySp, jsonData)
            apply()
        }
    }

    // restore from SP
    private fun restoreDataInside(sharedPreferences: SharedPreferences): List<MyCurrency>? {
        val jsonData = sharedPreferences.getString(Constants.keySp, null)
        if (jsonData != null) {
            return Json.decodeFromString<List<MyCurrency>>(jsonData)
        }
        return null
    }

    // finding id in file
    private fun tryToFindId(
        sharedPreferences: SharedPreferences,
        id: String
    ): RefactoredMyCurrency? {

        return restoreDataInside(sharedPreferences)?.find { it.id == id }?.toRefactoredMyCurrency()
    }


}