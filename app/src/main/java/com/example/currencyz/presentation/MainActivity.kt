package com.example.currencyz.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.currencyz.R
import com.example.currencyz.presentation.currency_detail.ClickListener
import com.example.currencyz.presentation.currency_detail.CurrencyFragment
import com.example.currencyz.presentation.currency_list.CurrencyListFragment
import com.example.currencyz.presentation.currency_list.OnCurrencyClickListener

class MainActivity : AppCompatActivity(), OnCurrencyClickListener, ClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            startCurrencyListFragment()
        }
    }

    private fun startCurrencyListFragment() {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(
                    R.id.container,
                    CurrencyListFragment.newInstance()
                )
                commit()
            }
    }

    override fun clickOnCurrency(myCurrencyId: String) {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.container, CurrencyFragment.newInstance(myCurrencyId))
                addToBackStack(null)
                commit()
            }
    }

    override fun backPressed() {
        supportFragmentManager
            .popBackStack()
    }
}