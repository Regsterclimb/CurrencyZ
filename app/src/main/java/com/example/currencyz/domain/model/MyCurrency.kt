package com.example.currencyz.domain.model

import com.example.currencyz.domain.calculations.CalculateHelper
import com.example.currencyz.domain.calculations.RefactorHelper
import kotlinx.serialization.Serializable

@Serializable
data class MyCurrency(
    val charCode: String,
    val id: String,
    val name: String,
    val nominal: Int,
    val numCode: String,
    val previous: Double,
    val value: Double
)

fun MyCurrency.toRefactoredMyCurrency(): RefactoredMyCurrency {
    return RefactoredMyCurrency(
        charCode = charCode,
        id = id,
        name = RefactorHelper.refactorNames(name, nominal),
        nominal = nominal,
        numCode = numCode,
        previous = (RefactorHelper.checkPositiveOrNot(previous, value,nominal)),
        value = CalculateHelper.refractorNumber(nominal, value)
    )


}
