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

    private var listner: OnCurrencyClickListner? = null //  need'd  reNull this

    private val viewModel: CurrencyListViewModel by viewModels {
        CurrencyListModelFactory((requireActivity() as CurrencyRepositoryProvider).provideCurrencyRepository())
    }

    companion object {
        fun newInstance(): CurrencyListFragment = CurrencyListFragment()

    }

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
            val adapter = CurrencyListAdapter {
                (listner?.clickOnCurrencyCart(it.id)) // maybe dangerous*****

            }
            this.adapter = adapter

            viewModel.loadCurrencyToLiveData()
            viewModel.currencyListLiveData.observe(
                this@CurrencyListFragment.viewLifecycleOwner,
                { insertCurrencyDataToAdapter(it) })
        }
    }

    override fun onDetach() {
        super.onDetach()
        listner = null

    }

    private fun insertCurrencyDataToAdapter(list: List<MyCurrency>) {
        val adapter =
            view?.findViewById<RecyclerView>(R.id.currencies_recycler)?.adapter as CurrencyListAdapter
        adapter.submitList(list)
    }


    fun setListner(clicker: OnCurrencyClickListner?) {
        listner = clicker
    }

}

interface OnCurrencyClickListner {
    fun clickOnCurrencyCart(myCurrencyId: String)
}