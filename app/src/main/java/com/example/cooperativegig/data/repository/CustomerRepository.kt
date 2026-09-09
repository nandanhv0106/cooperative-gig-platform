package com.example.cooperativegig.data.repository

import com.example.cooperativegig.core.network.SupabaseClient
import com.example.cooperativegig.data.model.Address
import com.example.cooperativegig.data.model.Profile
import com.example.cooperativegig.data.model.Worker
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CustomerRepository {
    private val supabase = SupabaseClient.client

    suspend fun updateProfile(firstName: String, lastName: String, phone: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val userId = supabase.auth.currentUserOrNull()?.id ?: return@withContext false
                supabase.from("profiles").update({
                    set("first_name", firstName)
                    set("last_name", lastName)
                    set("phone", phone)
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

    suspend fun getAddresses(): List<Address> {
        return withContext(Dispatchers.IO) {
            try {
                val userId = supabase.auth.currentUserOrNull()?.id ?: return@withContext defaultMockAddresses
                val result = supabase.from("customer_addresses").select {
                    filter {
                        eq("customer_id", userId)
                    }
                }
                val list = result.decodeList<Address>()
                if (list.isEmpty()) defaultMockAddresses else list
            } catch (e: Exception) {
                defaultMockAddresses
            }
        }
    }

    suspend fun addAddress(address: Address): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                supabase.from("customer_addresses").insert(address)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    suspend fun deleteAddress(addressId: Long): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                supabase.from("customer_addresses").delete {
                    filter {
                        eq("id", addressId)
                    }
                }
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    companion object {
        val defaultMockAddresses = listOf(
            Address(id = 1L, customerId = "demo-customer", label = "Home", address = "Flat 402, Sector 4, Dwarka, New Delhi", latitude = 28.5921, longitude = 77.0460, isDefault = true),
            Address(id = 2L, customerId = "demo-customer", label = "Work", address = "Tech Park, Building B, Sector 62, Noida", latitude = 28.6280, longitude = 77.3649, isDefault = false)
        )
    }
}