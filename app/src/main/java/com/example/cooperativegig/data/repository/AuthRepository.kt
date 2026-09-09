package com.example.cooperativegig.data.repository

import com.example.cooperativegig.core.network.SupabaseClient
import com.example.cooperativegig.data.model.Profile
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository {
    private val supabase = SupabaseClient.client

    suspend fun signUp(email: String, password: String, role: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                // Sign up the user
                supabase.auth.signUpWith(Email) {
                    this.email = email
                    this.password = password
                }
                
                // Get the user ID
                val userId = supabase.auth.currentUserOrNull()?.id
                
                if (userId != null) {
                    // Create profile
                    val profile = Profile(id = userId, role = role)
                    supabase.from("profiles").insert(profile)
                }
                true
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    suspend fun signIn(email: String, password: String): Profile? {
        return withContext(Dispatchers.IO) {
            try {
                supabase.auth.signInWith(Email) {
                    this.email = email
                    this.password = password
                }
                val userId = supabase.auth.currentUserOrNull()?.id ?: return@withContext null
                val result = supabase.from("profiles").select {
                    filter {
                        eq("id", userId)
                    }
                }
                result.decodeSingleOrNull<Profile>()
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    suspend fun signOut() {
        withContext(Dispatchers.IO) {
            try {
                supabase.auth.signOut()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun checkSession(): Profile? {
        return withContext(Dispatchers.IO) {
            try {
                // check if session is active
                val session = supabase.auth.currentSessionOrNull() ?: return@withContext null
                val userId = session.user?.id ?: return@withContext null
                
                val result = supabase.from("profiles").select {
                    filter {
                        eq("id", userId)
                    }
                }
                result.decodeSingleOrNull<Profile>()
            } catch (e: Exception) {
                null
            }
        }
    }
}