package com.example.currencyz.domain.calculations

import android.util.Log
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

class CalculateHelper {

    companion object {
        fun calculate(
            inputNumberString: String,
            nominal: Int,
            value: Double
        ): String {
            return CalculateHelper().startCalculate(inputNumberString, nominal, value)
        }

        fun refractorNumber(nominal: Int, value: Double) = CalculateHelper().roundDouble(
            CalculateHelper().refactorValueToOneNominal(
                nominal,
                value
            )
        )
    }

    private fun startCalculate(
        inputNumberString: String,
        nominal: Int,
        value: Double
    ): String {
        val firstDouble = (inputNumberString).toBigDecimal()
        val secondDouble = (refactorValueToOneNominal(nominal, value)).toBigDecimal()
        val bigDecimal: BigDecimal = (firstDouble * secondDouble)
        return stringDecimalFormat(bigDecimal)

    }

    private fun stringDecimalFormat(bigDecimal: BigDecimal): String {
        val nf = DecimalFormat("#.##")
        return bigDecimal.toString()
    }


    private fun refactorValueToOneNominal(nominal: Int, value: Double): Double {

        return when (nominal) {
            1 -> value
            10 -> (value / 10.0)
            100 -> (value / 100.0)
            1000 -> (value / 1000.0)
            10000 -> (value / 10000.0)
            else -> {
                Log.e("error", "something wrong with ${nominal} ")
                0.0
            }
        }

    }

    private fun roundDouble(double: Double): Double {
        val number3Digits: Double = Math.round(double * 1000.0) / 1000.0
        val number2Digits: Double = Math.round(number3Digits * 100.0) / 100.0
        return number2Digits

    }

    private fun roudBigDecimal(bigDecimal: BigDecimal): BigDecimal {

        return bigDecimal.setScale(2, RoundingMode.DOWN)

    }

    private fun toBigDecimal(str: String): BigDecimal {
        try {
            str.toBigDecimal()
        } catch (e: NumberFormatException) {
            Log.d("exception", "NumberFormatException in Calculate.kt str $str")
        }
        return str.toBigDecimal()
    }

}