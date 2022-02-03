package com.example.currencyz.domain.model.edit_text

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class EditTextInteractor(private val dispatcher: CoroutineDispatcher) {

    suspend fun changeCurrency(value: String): EditTextResult =
        withContext(dispatcher) {
            delay(DELAY_MILLIS)
            when {
                value.isEmpty() -> EditTextResult.Error()
                EditTextHelper.countDigitAfterDot(value) -> EditTextResult.Error()
                else ->  {
                    EditTextResult.Success()
                }
            }
        }

    companion object {
        const val DELAY_MILLIS: Long = 3_000
    }
}