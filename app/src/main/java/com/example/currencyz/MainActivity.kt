package com.example.currencyz

import android.os.Bundle
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.currencyz.di.CurrencyRepositoryProvider
import com.example.currencyz.domain.repository.CurrencyRepository
import com.example.currencyz.domain.repository.CurrencyRepositoryImpl
import com.example.currencyz.presentation.currency_detail.CurrencyFragment
import com.example.currencyz.presentation.currency_list.CurrencyListFragment
import com.example.currencyz.presentation.currency_list.OnCurrencyClickListner
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), OnCurrencyClickListner, CurrencyRepositoryProvider {

    private val repositoryImpl = CurrencyRepositoryImpl()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            startCurrencyListFragment()
        }

    }

    private fun startCurrencyListFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container,
                CurrencyListFragment.newInstance()
                    .apply {
                        setListner(this@MainActivity)
                    })
            commit()
        }

    }

    override fun clickOnCurrencyCart(myCurrencyId: String) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, CurrencyFragment.newInstance(myCurrencyId))
            addToBackStack(null)
            commit()
        }
    }

    override fun provideCurrencyRepository(): CurrencyRepository = repositoryImpl


}