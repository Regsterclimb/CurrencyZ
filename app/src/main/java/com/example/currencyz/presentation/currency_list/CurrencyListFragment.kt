package com.example.currencyz.presentation.currency_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyz.R
import com.example.currencyz.di.CurrencyRepositoryProvider
import com.example.currencyz.domain.model.MyCurrency
import com.example.currencyz.presentation.ViewModel.CurrencyListModelFactory
import com.example.currencyz.presentation.ViewModel.CurrencyListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CurrencyListFragment : Fragment() {

    private var listner: OnCurrencyClickListner? = null //  need'd  reNull this

    private val viewModel: CurrencyListViewModel by viewModels {
        CurrencyListModelFactory((requireActivity() as CurrencyRepositoryProvider).provideCurrencyRepository(), requireActivity().applicationContext)
    }

    companion object {
        fun newInstance(): CurrencyListFragment = CurrencyListFragment()

    }

    override fun onAttach(context: Context) {
        if (context is OnCurrencyClickListner) {
            listner = context
        }
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
            val adapter = CurrencyListAdapter {
                listner?.clickOnCurrency(it.id)
            }
            this.adapter = adapter
            viewModel.loadCurrencyListSp()
            viewModel.currencyListLiveData.observe(
                this@CurrencyListFragment.viewLifecycleOwner,
                { insertCurrencyListToAdapter(it) })

        }
        view.findViewById<FloatingActionButton>(R.id.refresh_button).apply {
            setOnClickListener {
                clickOnFloatingButton()
            }
        }
    }

    override fun onDetach() {
        listner
        super.onDetach()
    }

    private fun insertCurrencyListToAdapter(list: List<MyCurrency>) {
        val adapter =
            view?.findViewById<RecyclerView>(R.id.currencies_recycler)?.adapter as CurrencyListAdapter
        adapter.submitList(list)
    }

    fun setListner(clicker: OnCurrencyClickListner?) {
        listner = clicker
    }

    private fun clickOnFloatingButton() {
        viewModel.loadCurrencyListApi()
        val adapter =
            view?.findViewById<RecyclerView>(R.id.currencies_recycler)?.adapter as CurrencyListAdapter
        adapter.notifyDataSetChanged()
    }
}

interface OnCurrencyClickListner {
    fun clickOnCurrency(myCurrencyId: String)
}