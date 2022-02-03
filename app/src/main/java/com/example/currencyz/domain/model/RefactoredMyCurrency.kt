package com.example.currencyz.domain.model

data class RefactoredMyCurrency(
    val charCode: String,
    val id: String,
    val name: String,
    val nominal: Int,
    val numCode: String,
    val previous: Pair<String,Boolean>,
    val value: Double
)
