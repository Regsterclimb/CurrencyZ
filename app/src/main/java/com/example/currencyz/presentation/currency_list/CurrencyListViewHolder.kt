package com.example.currencyz.presentation.currency_list

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyz.R
import com.example.currencyz.domain.model.MyCurrency

class CurrencyListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val numCode = itemView.findViewById<TextView>(R.id.num_code) //str input
    private val nominal = itemView.findViewById<TextView>(R.id.nominal) //int input
    private val namePlusCharCode =
        itemView.findViewById<TextView>(R.id.currency_name_and_char_code) //str input
    private val value = itemView.findViewById<TextView>(R.id.currency_value) //int input

    fun onBind(currency: MyCurrency, onItemListner: (myCurrency: MyCurrency) -> Unit) {
        numCode.text = currency.numCode // from api numCode like "036" as string
        nominal.text = currency.nominal.toString()
        val string =
            currency.name + "(" + currency.charCode + ")" // should relocate this to another place
        namePlusCharCode.text = string
        value.text = currency.value.toString()

        itemView.setOnClickListener {
            onItemListner(currency) // makin some fragment or something else to change currency to VODKA currency
        }


    }


}