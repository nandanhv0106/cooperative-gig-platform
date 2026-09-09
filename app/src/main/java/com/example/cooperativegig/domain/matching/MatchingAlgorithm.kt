package com.example.cooperativegig.domain.matching

import com.example.cooperativegig.data.model.Worker
import kotlin.math.*

data class ScoredWorker(
    val worker: Worker,
    val totalScore: Double,
    val distanceKm: Double
)

object MatchingAlgorithm {

    // Haversine formula to compute actual distance in kilometers
    fun calculateHaversineDistanceKm(
        lat1: Double, lon1: Double,
        lat2: Double, lon2: Double
    ): Double {
        val r = 6371.0 // Radius of the earth in km
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return (r * c).roundTo2Decimals()
    }

    private fun Double.roundTo2Decimals(): Double {
        return (this * 100).roundToInt() / 100.0
    }

    fun matchAndRankWorkers(
        requestedSkill: String,
        candidateWorkers: List<Worker>,
        customerLat: Double,
        customerLng: Double,
        distanceWeight: Double = 0.40,
        skillWeight: Double = 0.30,
        ratingWeight: Double = 0.20,
        availabilityWeight: Double = 0.10
    ): List<ScoredWorker> {
        return candidateWorkers
            .filter { it.isAvailable && it.verificationStatus == "VERIFIED" }
            .map { worker ->
                val workerLat = worker.latitude ?: customerLat
                val workerLng = worker.longitude ?: customerLng
                val distanceKm = calculateHaversineDistanceKm(customerLat, customerLng, workerLat, workerLng)

                // Distance score: 100 at 0km, 0 at 10km+
                val distanceScore = max(0.0, min(100.0, 100.0 - (distanceKm * 10.0)))

                // Skill score: 100 if exact match, 0 otherwise
                val skillScore = if (worker.skills.any { it.equals(requestedSkill, ignoreCase = true) }) 100.0 else 0.0

                // Rating score: (rating / 5.0) * 100
                val ratingScore = (worker.rating / 5.0) * 100.0

                // Availability score: 100 if online, 0 if offline
                val availabilityScore = if (worker.isAvailable) 100.0 else 0.0

                val totalScore = (distanceScore * distanceWeight) +
                        (skillScore * skillWeight) +
                        (ratingScore * ratingWeight) +
                        (availabilityScore * availabilityWeight)

                ScoredWorker(
                    worker = worker,
                    totalScore = totalScore,
                    distanceKm = distanceKm
                )
            }.sortedByDescending { it.totalScore }
    }
}