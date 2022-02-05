package com.example.currencyz.presentation.currency_list

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyz.R
import com.example.currencyz.domain.calculations.RefactorHelper
import com.example.currencyz.domain.model.MyCurrency

class CurrencyListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun onBind(currency: MyCurrency, onItemListener: (myCurrency: MyCurrency) -> Unit) {
        itemView.findViewById<TextView>(R.id.holderPrevious).text =
            RefactorHelper
                .setDoubleToString(currency.previous)

        setColors(itemView.findViewById(R.id.holderPrevious), myCurrency = currency)

        itemView.findViewById<TextView>(R.id.holderNominal).text = currency.nominal.toString()

        itemView.findViewById<TextView>(R.id.holderCurrencyName).text =
            RefactorHelper
                .modifyString(currency.name, currency.charCode)

        itemView.findViewById<TextView>(R.id.holderValue).text = currency.value.toString()

        setColors(
            itemView.findViewById(R.id.holderPrevious), myCurrency = currency
        )
        itemView.setOnClickListener {
            onItemListener(currency)
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