package com.example.currencyz.domain.model

import kotlinx.serialization.SerialName

data class MyCurrency(
val charCode: String,
val id: String,
val name: String,
val nominal: Int,
val numCode: String,
val previous: Double,
val value: Double
)
