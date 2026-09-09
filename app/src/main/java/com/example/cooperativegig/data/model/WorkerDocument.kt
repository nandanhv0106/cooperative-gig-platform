package com.example.cooperativegig.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorkerDocument(
    val id: Long? = null,
    @SerialName("worker_id")
    val workerId: String,
    @SerialName("document_type")
    val documentType: String, // IDENTITY, SKILL_CERTIFICATE, EXPERIENCE
    @SerialName("file_url")
    val fileUrl: String,
    val status: String = "PENDING"
)