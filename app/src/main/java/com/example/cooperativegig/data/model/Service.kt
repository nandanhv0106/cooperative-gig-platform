package com.example.cooperativegig.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Service(
    val id: Long,
    val name: String,
    val description: String? = null,
    @SerialName("base_price")
    val basePrice: Double = 0.0
)