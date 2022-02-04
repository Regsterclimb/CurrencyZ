package com.example.currencyz.domain.model

import com.example.currencyz.domain.calculations.CalculateHelper
import com.example.currencyz.domain.calculations.RefactorHelper

data class RefactoredMyCurrency(
    val charCode: String,
    val id: String,
    val name: String,
    val nominal: Int,
    val numCode: String,
    val previous: Double,
    val value: Double,
    val positive : Boolean
)

fun MyCurrency.toRefactoredMyCurrency(): RefactoredMyCurrency {
    return RefactoredMyCurrency(
        charCode = charCode,
        id = id,
        name = RefactorHelper.refactorNames(name, nominal),
        nominal = nominal,
        numCode = numCode,
        previous = CalculateHelper.refactorNumber(nominal,previous),
        value = CalculateHelper.refactorNumber(nominal, value),
        positive = positive
    )


}
