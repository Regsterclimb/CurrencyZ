package com.example.currencyz.data.remote.dto


import com.example.currencyz.data.remote.dto.responses.CurrencyResponse
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class ApiData(
    @SerialName("Date")
    val date: String,
    @SerialName("PreviousDate")
    val previousDate: String,
    @SerialName("PreviousURL")
    val previousURL: String,
    @SerialName("Timestamp")
    val timestamp: String,
    @Serializable(with = CurrencyMapSerializer::class)
    @SerialName("Valute")
    val valute: Map<String, CurrencyResponse>
)

object CurrencyMapSerializer: KSerializer<Map<String, CurrencyResponse>> {

    private val mapSerializer = MapSerializer(String.serializer(), CurrencyResponse.serializer())

    override val descriptor: SerialDescriptor = mapSerializer.descriptor

    override fun serialize(encoder: Encoder, value: Map<String, CurrencyResponse>) {
        mapSerializer.serialize(encoder, value)
    }

    override fun deserialize(decoder: Decoder): Map<String, CurrencyResponse> = mapSerializer.deserialize(decoder)
}