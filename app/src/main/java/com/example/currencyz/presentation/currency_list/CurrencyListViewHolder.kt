package com.example.currencyz.presentation.currency_list

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyz.R
import com.example.currencyz.domain.calculations.RefactorHelper
import com.example.currencyz.domain.model.MyCurrency
import com.example.currencyz.domain.model.RefactoredMyCurrency

class CurrencyListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val valueMinusPrevious = itemView.findViewById<TextView>(R.id.value_previous) //str input
    private val nominal = itemView.findViewById<TextView>(R.id.nominal) //int input
    private val namePlusCharCode =
        itemView.findViewById<TextView>(R.id.currency_name_and_char_code) //str input
    private val value = itemView.findViewById<TextView>(R.id.currency_value) //int input

    fun onBind(currency: MyCurrency, onItemListner: (myCurrency: MyCurrency) -> Unit) {

        valueMinusPrevious.text = RefactorHelper.setDoubleToString(currency.previous) // from api numCode like "036" as string
        setColors(valueMinusPrevious,myCurrency = currency)

        nominal.text = currency.nominal.toString()

        namePlusCharCode.text = RefactorHelper.modifyString(currency.name,currency.charCode)
        value.text = currency.value.toString()

        itemView.setOnClickListener {
            onItemListner(currency) // makin some fragment or something else to change currency to VODKA currency
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