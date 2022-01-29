package com.example.currencyz.data.remote.dto


import com.example.currencyz.data.remote.dto.currencies.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ValuteDto(
    @SerialName("AMD")
    val aMD: AMD,
    @SerialName("AUD")
    val aUD: AUD,
    @SerialName("AZN")
    val aZN: AZN,
    @SerialName("BGN")
    val bGN: BGN,
    @SerialName("BRL")
    val bRL: BRL,
    @SerialName("BYN")
    val bYN: BYN,
    @SerialName("CAD")
    val cAD: CAD,
    @SerialName("CHF")
    val cHF: CHF,
    @SerialName("CNY")
    val cNY: CNY,
    @SerialName("CZK")
    val cZK: CZK,
    @SerialName("DKK")
    val dKK: DKK,
    @SerialName("EUR")
    val eUR: EUR,
    @SerialName("GBP")
    val gBP: GBP,
    @SerialName("HKD")
    val hKD: HKD,
    @SerialName("HUF")
    val hUF: HUF,
    @SerialName("INR")
    val iNR: INR,
    @SerialName("JPY")
    val jPY: JPY,
    @SerialName("KGS")
    val kGS: KGS,
    @SerialName("KRW")
    val kRW: KRW,
    @SerialName("KZT")
    val kZT: KZT,
    @SerialName("MDL")
    val mDL: MDL,
    @SerialName("NOK")
    val nOK: NOK,
    @SerialName("PLN")
    val pLN: PLN,
    @SerialName("RON")
    val rON: RON,
    @SerialName("SEK")
    val sEK: SEK,
    @SerialName("SGD")
    val sGD: SGD,
    @SerialName("TJS")
    val tJS: TJS,
    @SerialName("TMT")
    val tMT: TMT,
    @SerialName("TRY")
    val tRY: TRY,
    @SerialName("UAH")
    val uAH: UAH,
    @SerialName("USD")
    val uSD: USD,
    @SerialName("UZS")
    val uZS: UZS,
    @SerialName("XDR")
    val xDR: XDR,
    @SerialName("ZAR")
    val zAR: ZAR
)