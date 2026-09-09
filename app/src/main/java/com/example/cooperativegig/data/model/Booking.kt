package com.example.cooperativegig.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Booking(
    val id: Long? = null,
    @SerialName("customer_id")
    val customerId: String,
    @SerialName("worker_id")
    val workerId: String? = null,
    @SerialName("service_id")
    val serviceId: Long,
    val status: String = "SEARCHING", // SEARCHING, ASSIGNED, ACCEPTED, ON_THE_WAY, STARTED, COMPLETED, CANCELLED
    val location: String,
    val description: String? = null,
    @SerialName("is_emergency")
    val isEmergency: Boolean = false,
    @SerialName("total_amount")
    val totalAmount: Double = 0.0,
    @SerialName("created_at")
    val createdAt: String? = null
)