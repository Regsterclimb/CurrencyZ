package com.example.currencyz.domain.model

import com.example.currencyz.data.remote.dto.CurrencyDto
import com.example.currencyz.domain.calculations.RefactorHelper
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyCurrency(
    val charCode: String,
    val id: String,
    val name: String,
    val nominal: Int,
    val numCode: String,
    val previous: Double,
    val value: Double,
    @SerialName("positive")
    val positive : Boolean,
)
fun CurrencyDto.toMyCurrency() : MyCurrency {
    return MyCurrency(
        id = id,
        charCode = charCode,
        name = name,
        nominal = nominal,
        numCode = numCode,
        previous = RefactorHelper.roundDouble(previous-value),
        value = value,
        positive = RefactorHelper.checkPositiveOrNot(previous-value),
    )
}



