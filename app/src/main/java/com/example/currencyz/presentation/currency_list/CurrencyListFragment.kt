package com.example.currencyz.presentation.currency_list

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyz.R
import com.example.currencyz.domain.model.MyCurrency
import com.example.currencyz.presentation.App
import com.example.currencyz.presentation.ViewModel.CurrencyListModelFactory
import com.example.currencyz.presentation.ViewModel.CurrencyListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CurrencyListFragment : Fragment(R.layout.currencies_fragment) {

    private var listener: OnCurrencyClickListener? = null

    private val adapter get() = view?.findViewById<RecyclerView>(R.id.currencies_recycler)?.adapter as CurrencyListAdapter

    val app = App.instance

    private val viewModel: CurrencyListViewModel by viewModels {
        CurrencyListModelFactory(
            applicationContext = requireContext().applicationContext
        )
    }

    override fun onAttach(context: Context) {
        if (context is OnCurrencyClickListener) {
            listener = context
        }
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.currencies_recycler).apply {
            this.adapter =
                CurrencyListAdapter {
                    listener?.clickOnCurrency(it.id)
                }
            addItemDecoration(setDivider())
        }
        viewModel.currencyListLiveData.observe(this.viewLifecycleOwner)
        {
            submitList(it)
        }
        viewModel.isLoading.observe(this.viewLifecycleOwner)
        {
            loadingStateChange(it)
        }
        view.findViewById<FloatingActionButton>(R.id.refresh_button)
            .setOnClickListener {
                clickOnFloatingButton()
            }
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    private fun submitList(list: List<MyCurrency>) {
        adapter.submitList(list)
    }

    private fun clickOnFloatingButton() {
        viewModel.loadCurrencyListApi()
        adapter.notifyDataSetChanged()
    }

    private fun loadingStateChange(boolean: Boolean) {
        view?.findViewById<FloatingActionButton>(R.id.refresh_button)?.isEnabled = !boolean
        view?.findViewById<RecyclerView>(R.id.currencies_recycler)?.isVisible = !boolean
        view?.findViewById<ProgressBar>(R.id.list_loader)?.isVisible = boolean
    }

    private fun setDivider(): DividerItemDecoration {
        val dividerItemDecoration =
            DividerItemDecoration(this.context, RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.line, null)?.let {
            dividerItemDecoration.setDrawable(it)
        }
        return dividerItemDecoration
    }

    companion object {
        fun newInstance(): CurrencyListFragment = CurrencyListFragment()
    }
}

interface OnCurrencyClickListener {
    fun clickOnCurrency(myCurrencyId: String)
}