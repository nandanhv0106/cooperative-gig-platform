package com.example.cooperativegig.data.repository

import com.example.cooperativegig.core.network.SupabaseClient
import com.example.cooperativegig.data.model.Worker
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WorkerRepository {
    private val supabase = SupabaseClient.client

    suspend fun getWorkerProfile(): Worker? {
        return withContext(Dispatchers.IO) {
            try {
                val userId = supabase.auth.currentUserOrNull()?.id ?: return@withContext null
                val result = supabase.from("workers").select {
                    filter {
                        eq("id", userId)
                    }
                }
                result.decodeSingleOrNull<Worker>()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun updateAvailability(isAvailable: Boolean): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val userId = supabase.auth.currentUserOrNull()?.id ?: return@withContext false
                supabase.from("workers").update({
                    set("is_available", isAvailable)
                }) {
                    filter {
                        eq("id", userId)
                    }
                }
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}