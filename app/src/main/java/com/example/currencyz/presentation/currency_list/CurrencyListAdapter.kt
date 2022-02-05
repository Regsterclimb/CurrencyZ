package com.example.currencyz.presentation.currency_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.currencyz.R
import com.example.currencyz.domain.model.MyCurrency

class CurrencyListAdapter(
    private val onItemListner: (myCurrency: MyCurrency) -> Unit
) : ListAdapter<MyCurrency, CurrencyListViewHolder>(CurrencyListCallBack())
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.currency_view_holder, parent, false)
        return CurrencyListViewHolder(view)
    }

    override fun onBindViewHolder(holderList: CurrencyListViewHolder, position: Int) {
        holderList.onBind(getItem(position), onItemListner)
    }
}
