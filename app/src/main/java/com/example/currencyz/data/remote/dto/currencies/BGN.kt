package com.example.currencyz.data.remote.dto.currencies


import com.example.currencyz.data.remote.dto.CurrencyDto
import com.example.currencyz.domain.model.MyCurrency
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BGN(
    @SerialName("CharCode")
    val charCode: String,
    @SerialName("ID")
    val iD: String,
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
fun BGN.toCurrencyDto() : CurrencyDto {
    return CurrencyDto(
        charCode = charCode,
        id = iD,
        name = name,
        nominal = nominal,
        numCode = numCode,
        previous = previous,
        value = value
    )
}