package com.example.cooperativegig.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Worker(
    val id: String,
    @SerialName("cooperative_id")
    val cooperativeId: Long? = null,
    val skills: List<String> = emptyList(),
    @SerialName("verification_status")
    val verificationStatus: String = "PENDING", // PENDING, VERIFIED, REJECTED
    @SerialName("is_available")
    val isAvailable: Boolean = true,
    val rating: Double = 5.0,
    @SerialName("total_earnings")
    val totalEarnings: Double = 0.0,
    val latitude: Double? = null,
    val longitude: Double? = null
)