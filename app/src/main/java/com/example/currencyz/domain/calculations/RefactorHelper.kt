package com.example.currencyz.domain.calculations

import android.util.Log

class RefactorHelper {


    companion object {
        fun refactorNames(string: String, nominal: Int): String =
            RefactorHelper().replaceCurrencyNames(string, nominal)

        fun checkPositiveOrNot(a: Double, b: Double, nominal: Int): Pair<String, Boolean> =
            RefactorHelper().startRefactor(a, b, nominal)

    }

    private fun checkPositive(doubleDigit: Double): Boolean {
        return when {
            doubleDigit > 0 -> true
            doubleDigit < 0 -> false
            else -> true
        }

    }

    private fun startRefactor(
        a: Double,
        b: Double,
        nominal: Int
    ): Pair<String, Boolean> { // doing a lot work
        val number = a - b
        Log.d("number", "${number}")
        val cal = CalculateHelper.refractorNumber(nominal, number)
        if (checkPositive(cal))
            return "+$cal" to checkPositive(cal)

        return cal.toString() to checkPositive(cal) // add one plus to positive

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

    private fun startRefactorToDouble(a: Double, b: Double): String { // doing a lot work
        val number = a - b
        Log.d("number", "${number}")
        val number4Digits: Double = Math.round(number * 10000.0) / 10000.0
        return number4Digits.toString()
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