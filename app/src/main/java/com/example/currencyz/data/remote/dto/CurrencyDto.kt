package com.example.currencyz.data.remote.dto


import com.example.currencyz.data.remote.responses.CurrencyResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyDto(
    @SerialName("CharCode")
    val charCode: String,
    @SerialName("ID")
    val id: String,
    @SerialName("Name")
    val name: String,
    @SerialName("Nominal")
    val nominal: Int,
    @SerialName("NumCode")
    val numCode: String,
    @SerialName("Previous")
    val previous: Double,
    @SerialName("Value")
    val value: Double
)

fun CurrencyResponse.toCurrencyDto(): CurrencyDto {
    return CurrencyDto(
        charCode, id, name, nominal, numCode, previous, value
    )
}

