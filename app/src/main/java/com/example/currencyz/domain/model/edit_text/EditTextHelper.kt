package com.example.currencyz.domain.model.edit_text

class EditTextHelper {

    companion object {
        fun countDigitAfterDot(string: String) = EditTextHelper().countAfterDot(string)
    }

    private fun countAfterDot(string: String): Boolean {
        if (string.contains('.')) {
            val subStr = string.substring(string.indexOf('.'), string.length - 1)
            if (subStr.length > 2) return true
        }
        return false
    }
}