package com.example.cooperativegig.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val id: Long? = null,
    @SerialName("customer_id")
    val customerId: String,
    val label: String = "Home",
    val address: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
    @SerialName("is_default")
    val isDefault: Boolean = false
)