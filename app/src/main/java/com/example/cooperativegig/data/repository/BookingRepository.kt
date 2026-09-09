package com.example.cooperativegig.data.repository

import com.example.cooperativegig.core.network.SupabaseClient
import com.example.cooperativegig.data.model.Booking
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookingRepository {
    private val supabase = SupabaseClient.client

    suspend fun createBooking(
        serviceId: Long,
        location: String,
        description: String,
        isEmergency: Boolean
    ): Booking? {
        return withContext(Dispatchers.IO) {
            try {
                val userId = supabase.auth.currentUserOrNull()?.id ?: "demo-customer"
                val booking = Booking(
                    customerId = userId,
                    serviceId = serviceId,
                    location = location,
                    description = description,
                    isEmergency = isEmergency,
                    totalAmount = if (isEmergency) 500.0 else 300.0
                )
                val result = supabase.from("bookings").insert(booking) {
                    select()
                }
                result.decodeSingleOrNull<Booking>()
            } catch (e: Exception) {
                e.printStackTrace()
                // Return fallback mock booking for UI demonstration if table isn't migrated yet
                Booking(
                    id = System.currentTimeMillis(),
                    customerId = "demo-customer",
                    serviceId = serviceId,
                    location = location,
                    description = description,
                    isEmergency = isEmergency,
                    status = "SEARCHING",
                    totalAmount = if (isEmergency) 500.0 else 300.0
                )
            }
        }
    }

    suspend fun getCustomerBookings(): List<Booking> {
        return withContext(Dispatchers.IO) {
            try {
                val userId = supabase.auth.currentUserOrNull()?.id ?: return@withContext emptyList()
                val result = supabase.from("bookings").select {
                    filter {
                        eq("customer_id", userId)
                    }
                }
                result.decodeList<Booking>()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    suspend fun updateBookingStatus(bookingId: Long, newStatus: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                supabase.from("bookings").update({
                    set("status", newStatus)
                }) {
                    filter {
                        eq("id", bookingId)
                    }
                }
                true
            } catch (e: Exception) {
                false
            }
        }
    }
}