package com.example.currencyz.domain.model

import android.util.Log

data class MyCurrency(
    val charCode: String,
    val id: String,
    val name: String,
    val nominal: Int,
    val numCode: String,
    val previous: Double,
    val value: Double
)

fun MyCurrency.toRefactoredMyCurrency(): RefactoredMyCurrency {
    return RefactoredMyCurrency(
        charCode = charCode,
        id = id,
        name = replaceCurrencyNames(name, nominal),
        nominal = nominal,
        numCode = numCode,
        previous = checkPositive(aMunusb(previous,value)),
        value = value
    )


}


fun checkPositive(doubleDigit: Double): Pair<String,Boolean> {
    return when  {
        doubleDigit>0 -> ("+" + doubleDigit.toString() to true)
        doubleDigit<0 -> (doubleDigit.toString() to false)
        else ->("+" + doubleDigit.toString() to true)
    }

}

fun aMunusb(a: Double, b: Double): Double { // doing a lot work
    val number = a - b
    Log.d("number", "${number}")
    val number3digits: Double = Math.round(number * 10000.0) / 10000.0

    return number3digits

}

fun replaceCurrencyNames(string: String, nominal: Int): String { // TODO()
    return when (nominal) {
        1 -> string
        10 -> refactorCurrencyNamesTen(string)
        100 -> refactorCurrencyNamesHundred(string)
        1000 -> refactorCurrencyNamesThousand(string);
        10000 -> refactorCurrencyNamesTenThousand(string);
        else -> string
    }

}

fun refactorCurrencyNamesTen(string: String): String {
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
        else -> string

    }

}

fun refactorCurrencyNamesHundred(string: String): String {
    return when (string) {
        "Армянских драмов" -> "Армянский драм"
        "Венгерских форинтов" -> "Венгерский форинт"
        "Казахстанских тенге" -> "Казахстанский тенге"
        "Киргизских сомов" -> "Киргизский сом"
        "Японских иен" -> "Японская иена"
        else -> string
    }
}

fun refactorCurrencyNamesThousand(string: String): String {
    return when (string) {
        "Вон Республики Корея" -> "Вона Республики Корея"
        else -> string
    }
}


fun refactorCurrencyNamesTenThousand(string: String): String {
    return when (string) {
        "Узбекских сумов" -> "Узбекский сум"
        else -> string
    }
}
