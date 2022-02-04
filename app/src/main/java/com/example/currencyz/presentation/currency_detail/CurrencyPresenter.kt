package com.example.currencyz.presentation.currency_detail

import com.example.currencyz.domain.calculations.CalculateHelper
import com.example.currencyz.domain.model.edit_text.EditTextInteractor
import com.example.currencyz.domain.model.edit_text.EditTextResult
import kotlinx.coroutines.*

class CurrencyPresenter
    (
    private val interactor: EditTextInteractor,
    mainDispatcher: CoroutineDispatcher
) {

    private val presenterScope: CoroutineScope =
        CoroutineScope(SupervisorJob() + mainDispatcher)

    private var view: CurrencyView? = null

    fun attachView(view: CurrencyView) {
        this.view = view
    }

    fun detachView() {
        this.view = null
    }

    fun onDestroy() {
        presenterScope.cancel()
    }

    fun change(number: String, value: Double) {
        presenterScope.launch {
            view?.setLoading(loading = true)

            val res = interactor.changeCurrency(number)
            when (res) {
                is EditTextResult.Error -> {
                    view?.showEditTextError()
                }
                is EditTextResult.Success -> {
                    view?.showSuccess(calculateIt(number, value))
                }
            }

            view?.setLoading(loading = false)
        }
    }

    private fun calculateIt(str: String, value: Double): String =
        CalculateHelper.calculate(str, value)
}