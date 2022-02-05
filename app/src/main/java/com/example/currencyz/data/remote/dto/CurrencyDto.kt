package com.example.currencyz.data.remote.dto


import com.example.currencyz.data.remote.responses.CurrencyResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class CurrencyDto(
    val charCode: String,
    val id: String,
    val name: String,
    val nominal: Int,
    val numCode: String,
    val previous: Double,
    val value: Double
)

fun CurrencyResponse.toCurrencyDto(): CurrencyDto {
    return CurrencyDto(
        charCode, id, name, nominal, numCode, previous, value
    )
}

