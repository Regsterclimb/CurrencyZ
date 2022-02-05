package com.example.currencyz.domain.calculations

import android.util.Log
import java.math.BigDecimal
import java.math.RoundingMode

class CalculateHelper {

    companion object {
        fun calculate(inputNumberString: String, value: Double): String =
            CalculateHelper().startCalculate(inputNumberString, value)


        fun refactorNumber(nominal: Int, value: Double) =
            CalculateHelper()
                .roundDoubleThreeDigit(
                    CalculateHelper()
                        .refactorValueToOneNominal(nominal, value)
                )
    }

    private fun startCalculate(inputNumberString: String, value: Double): String {
        return stringBigDecimalFormat(inputNumberString.toBigDecimal() * value.toBigDecimal())
    }

    private fun stringBigDecimalFormat(bigDecimal: BigDecimal): String {
        return bigDecimal.setScale(2, RoundingMode.DOWN).toString()
    }

    private fun refactorValueToOneNominal(nominal: Int, value: Double): Double {
        return when (nominal) {
            1 -> value
            10 -> (value / 10.0)
            100 -> (value / 100.0)
            1000 -> (value / 1000.0)
            10000 -> (value / 10000.0)
            else -> {
                Log.e("error", "something wrong with $nominal ")
                0.0
            }
        }
    }

    private fun roundDoubleThreeDigit(double: Double): Double {
        return Math.round(double * 1000.0) / 1000.0

    }
}