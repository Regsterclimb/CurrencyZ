package com.example.currencyz.domain.model.edit_text

sealed class EditTextResult {

    class Success : EditTextResult()
    class Error : EditTextResult()

}
