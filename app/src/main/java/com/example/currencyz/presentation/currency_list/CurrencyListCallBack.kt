package com.example.currencyz.presentation.currency_list

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.example.currencyz.domain.model.MyCurrency
import kotlin.math.log

class CurrencyListCallBack : DiffUtil.ItemCallback<MyCurrency>() {
    override fun areItemsTheSame(oldItem: MyCurrency, newItem: MyCurrency): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MyCurrency, newItem: MyCurrency): Boolean {
        return oldItem == newItem
    }
}