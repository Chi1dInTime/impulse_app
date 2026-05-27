package com.vasal.impulse.domain

data class QuestRecommendation(
    val kind: String,
    val points: Int,
    val reason: String
)

object QuestRecommender {
    fun recommend(
        importance: Int,
        difficulty: Int,
        energyCost: Int,
        durationMinutes: Int
    ): QuestRecommendation {
        val safeImportance = importance.coerceIn(1, 5)
        val safeDifficulty = difficulty.coerceIn(1, 5)
        val safeEnergy = energyCost.coerceIn(1, 5)
        val safeDuration = durationMinutes.coerceIn(1, 240)
        val points = (
            safeImportance * 5 +
                safeDifficulty * 3 +
                safeEnergy * 2 +
                durationBonus(safeDuration)
            ).coerceIn(5, 120)

        val kind = when {
            safeDuration <= 10 && safeDifficulty <= 2 && safeEnergy <= 2 -> "импульс"
            safeImportance >= 4 && safeDifficulty >= 3 -> "дело дня"
            safeDuration <= 15 && safeEnergy <= 2 -> "рутина"
            safeImportance <= 2 && safeDifficulty <= 2 -> "гибкое"
            else -> "надо бы"
        }

        return QuestRecommendation(
            kind = kind,
            points = points,
            reason = "по важности $safeImportance, сложности $safeDifficulty, энергии $safeEnergy"
        )
    }

    private fun durationBonus(durationMinutes: Int): Int =
        when {
            durationMinutes <= 10 -> 2
            durationMinutes <= 30 -> 6
            durationMinutes <= 60 -> 10
            else -> 15
        }
}
