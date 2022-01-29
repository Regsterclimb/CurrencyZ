package com.example.currencyz.presentation.currency_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.currencyz.R
import com.example.currencyz.domain.model.MyCurrency

class CurrencyAdapter(private val onItemListner: (myCurrency: MyCurrency) -> Unit) :
    ListAdapter<MyCurrency, CurrencyViewHolder>(CurrencyListCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.currency_view_holder, parent, false)
        return CurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.onBind(getItem(position),onItemListner)
    }
}
