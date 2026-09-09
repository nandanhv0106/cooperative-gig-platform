package com.example.cooperativegig.domain.matching

import com.example.cooperativegig.data.model.Worker
import kotlin.math.max
import kotlin.math.min

data class ScoredWorker(
    val worker: Worker,
    val totalScore: Double,
    val distanceKm: Double
)

object MatchingAlgorithm {

    fun matchAndRankWorkers(
        requestedSkill: String,
        candidateWorkers: List<Worker>,
        workerDistancesKm: Map<String, Double>,
        distanceWeight: Double = 0.40,
        skillWeight: Double = 0.30,
        ratingWeight: Double = 0.20,
        availabilityWeight: Double = 0.10
    ): List<ScoredWorker> {
        return candidateWorkers.map { worker ->
            val distanceKm = workerDistancesKm[worker.id] ?: 10.0
            
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