package com.example.currencyz.presentation.currency_detail

import android.view.View

interface CurrencyView {

    fun setLoading(loading: Boolean, view: View)

    fun resultError(error: String, view: View)

    fun showSuccess(resultString: String, view: View)

}