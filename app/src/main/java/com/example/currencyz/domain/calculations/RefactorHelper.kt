package com.example.currencyz.domain.calculations

import android.util.Log

class RefactorHelper {


    companion object {
        fun refactorNames(string: String, nominal: Int): String =
            RefactorHelper().replaceCurrencyNames(string, nominal)


        fun roundDouble(double: Double): Double =
            RefactorHelper().startRefactorToDouble(double)

        fun setDoubleToString(double: Double) = RefactorHelper().setPositive(double)

        fun checkPositiveOrNot(a: Double): Boolean =RefactorHelper().checkPositive(a)

        fun modifyString(string: String, string1: String) =
            RefactorHelper()._modifyString(string, string1)
    }

    private fun _modifyString(string: String, string1: String): String {
        return "$string($string1)"

    }

    private fun setPositive(double: Double): String {
        return when (checkPositive(double)) {
            false -> "-$double"
            else -> "+$double"
        }
    }

    private fun startRefactorToDouble(a: Double): Double {
        return Math.round(a * 10000.0) / 10000.0
    }

    private fun checkPositive(doubleDigit: Double): Boolean {
        return when {
            doubleDigit > 0 -> true
            doubleDigit < 0 -> false
            else -> true
        }

    }

    private fun startRefactor(a: Double, b: Double, nominal: Int): Pair<Double, Boolean> {
        val number = a - b
        Log.d("number", "${number}")
        val cal = startRefactorToDouble(CalculateHelper.refactorNumber(nominal, number))
        if (checkPositive(cal))
            return cal to checkPositive(cal)
        return cal to checkPositive(cal) // add one plus to positive
    }

    private fun startRefactor(a: Double, b: Double): Pair<Double, Boolean> {
        val number = startRefactorToDouble(a - b)
        Log.d("number", "${number}")
        if (checkPositive(number))
            return number to checkPositive(number)
        return number to checkPositive(number) // add one plus to positive
    }

    private fun replaceCurrencyNames(string: String, nominal: Int): String {
        return when (nominal) {
            1 -> string
            10 -> refactorCurrencyNamesTen(string)
            100 -> refactorCurrencyNamesHundred(string)
            1000 -> refactorCurrencyNamesThousand(string);
            10000 -> refactorCurrencyNamesTenThousand(string);
            else -> {
                Log.e("error", "something wrong with ${string} ")
                string
            }
        }

    }


    private fun refactorCurrencyNamesTen(string: String): String {
        return when (string) {
            "Гонконгских долларов" -> "Гонконгский доллар"
            "Индийских рупий" -> "Индийский рупий"
            "Молдавских леев" -> "Молдавский леев"
            "Норвежских крон" -> "Норвежская крона"
            "Таджикских сомони" -> "Таджикский сомони"
            "Турецких лир" -> "Турецкая лира"
            "Украинских гривен" -> "Украинская гривна"
            "Чешских крон" -> "Чешская крона"
            "Шведских крон" -> "Шведская крона"
            "Южноафриканских рэндов" -> "Южноафриканский рэнд"
            else -> {
                Log.e("error", "something wrong with ${string} ")
                string
            }

        }

    }

    private fun refactorCurrencyNamesHundred(string: String): String {
        return when (string) {
            "Армянских драмов" -> "Армянский драм"
            "Венгерских форинтов" -> "Венгерский форинт"
            "Казахстанских тенге" -> "Казахстанский тенге"
            "Киргизских сомов" -> "Киргизский сом"
            "Японских иен" -> "Японская иена"
            else -> {
                Log.e("error", "something wrong with ${string} ")
                string
            }
        }
    }

    private fun refactorCurrencyNamesThousand(string: String): String {
        return when (string) {
            "Вон Республики Корея" -> "Вона Республики Корея"
            else -> {
                Log.e("error", "something wrong with ${string} ")
                string
            }
        }
    }


    private fun refactorCurrencyNamesTenThousand(string: String): String {
        return when (string) {
            "Узбекских сумов" -> "Узбекский сум"
            else -> {
                Log.e("error", "something wrong with ${string} ")
                string
            }
        }
    }
}