package com.example.cooperativegig.data.repository

import com.example.cooperativegig.core.network.SupabaseClient
import com.example.cooperativegig.data.model.Service
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ServiceRepository {
    private val supabase = SupabaseClient.client

    suspend fun getServices(): List<Service> {
        return withContext(Dispatchers.IO) {
            val result = supabase.from("services").select()
            result.decodeList<Service>()
        }
    }
}