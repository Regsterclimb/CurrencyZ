package com.example.currencyz.presentation.currency_detail

interface CurrencyView {

    fun setLoading(loading: Boolean)

    fun showEditTextError()

    fun showSuccess(resultString: String)

}