package com.example.currencyz.presentation.currency_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyz.R
import com.example.currencyz.di.CurrencyRepositoryProvider
import com.example.currencyz.domain.model.MyCurrency

class CurrencyListFragment : Fragment() {

    private var listner: OnCurrencyClickListner? =
        null //  need'd  reNull this

    private val viewModel: CurrencyListViewModel by viewModels {
        CurrencyListModelFactory((requireActivity() as CurrencyRepositoryProvider).provideCurrencyRepository())
    }

    companion object {
        fun newInstance(): CurrencyListFragment = CurrencyListFragment()


    }

    /*private val string: String = """AUD": {
        "ID": "R01010",
        "NumCode": "036",
        "CharCode": "AUD",
        "Nominal": 1,
        "Name": "Австралийский доллар",
        "Value": 54.5422,
        "Previous": 55.8629
        }"""*/



    override fun onAttach(context: Context) {
        super.onAttach(context)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.currencies_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.currencies_recycler).apply {
            this.layoutManager = GridLayoutManager(this.context, 1)
            val adapter = CurrencyAdapter {
                (listner?.clickOnCurrencyCart(
                    MyCurrency(
                        "AUD",
                        "1231",
                        "Австралийский буржуй",
                        1,
                        "036",
                        54.2424,
                        53.4242
                    )
                )) //

            }
            this.adapter = adapter

            viewModel.loadCurrencyToLiveData()
            viewModel.currencyListLiveData.observe(this@CurrencyListFragment.viewLifecycleOwner, { insertCurrencyDataToAdapter(it) })
            /*insertCurrencyDataToAdapter(
                listOf(
                    MyCurrency(
                        "AUD",
                        "1231",
                        "Австралийский буржуй",
                        1,
                        "036",
                        54.2424,
                        53.4242
                    ),
                    MyCurrency("AUD", "1231", "Австралийский буржуй", 1, "036", 54.2424, 53.4242),
                    MyCurrency("AUD", "1231", "Австралийский буржуй", 1, "036", 54.2424, 53.4242)
                )
            )*/

        }
    }

    override fun onDetach() {
        super.onDetach()
        listner = null

    }


    private fun insertCurrencyDataToAdapter(list: List<MyCurrency>) {
        val adapter =
            view?.findViewById<RecyclerView>(R.id.currencies_recycler)?.adapter as CurrencyAdapter
        adapter.submitList(list)
    }


    fun setListner(clicker: OnCurrencyClickListner?) {
        listner = clicker
    }

}

interface OnCurrencyClickListner {
    fun clickOnCurrencyCart(currency: MyCurrency)
}