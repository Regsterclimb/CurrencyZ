package com.example.currencyz.presentation.currency_list

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyz.R
import com.example.currencyz.domain.calculations.RefactorHelper
import com.example.currencyz.domain.model.MyCurrency

class CurrencyListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val valueMinusPrevious = itemView.findViewById<TextView>(R.id.value_previous)

    private val nominal = itemView.findViewById<TextView>(R.id.nominal)

    private val namePlusCharCode = itemView.findViewById<TextView>(R.id.currency_name_and_char_code)

    private val value = itemView.findViewById<TextView>(R.id.currency_value)

    fun onBind(currency: MyCurrency, onItemListner: (myCurrency: MyCurrency) -> Unit) {
        valueMinusPrevious.text = RefactorHelper.setDoubleToString(currency.previous)
        setColors(valueMinusPrevious, myCurrency = currency)

        nominal.text = currency.nominal.toString()
        namePlusCharCode.text = RefactorHelper.modifyString(currency.name, currency.charCode)
        value.text = currency.value.toString()
        itemView.setOnClickListener {
            onItemListner(currency)
        }
    }

    private fun setColors(textView: TextView, myCurrency: MyCurrency) {
        textView.text = myCurrency.previous.toString()
        if (myCurrency.positive) {
            textView.setTextColor(Color.GREEN)
        } else {
            textView.setTextColor(Color.RED)
        }
    }

}