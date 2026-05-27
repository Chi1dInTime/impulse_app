package com.vasal.impulse.domain

data class DayProgress(
    val minimumProgress: Float,
    val strongProgress: Float,
    val superProgress: Float,
    val level: DayProgressLevel,
    val pointsUntilNextLevel: Int?
)

enum class DayProgressLevel {
    Neutral,
    Minimum,
    Strong,
    Super
}

object DayProgressCalculator {
    private const val MINIMUM_POINTS = 40
    private const val STRONG_POINTS = 90
    private const val SUPER_POINTS = 150

    fun calculate(points: Int): DayProgress {
        val safePoints = points.coerceAtLeast(0)
        return DayProgress(
            minimumProgress = progressBetween(safePoints, 0, MINIMUM_POINTS),
            strongProgress = progressBetween(safePoints, MINIMUM_POINTS, STRONG_POINTS),
            superProgress = progressBetween(safePoints, STRONG_POINTS, SUPER_POINTS),
            level = levelFor(safePoints),
            pointsUntilNextLevel = pointsUntilNextLevel(safePoints)
        )
    }

    private fun progressBetween(points: Int, start: Int, end: Int): Float {
        if (points <= start) return 0f
        return ((points - start).toFloat() / (end - start).toFloat()).coerceIn(0f, 1f)
    }

    private fun levelFor(points: Int): DayProgressLevel = when {
        points >= SUPER_POINTS -> DayProgressLevel.Super
        points >= STRONG_POINTS -> DayProgressLevel.Strong
        points >= MINIMUM_POINTS -> DayProgressLevel.Minimum
        else -> DayProgressLevel.Neutral
    }

    private fun pointsUntilNextLevel(points: Int): Int? = when {
        points < MINIMUM_POINTS -> MINIMUM_POINTS - points
        points < STRONG_POINTS -> STRONG_POINTS - points
        points < SUPER_POINTS -> SUPER_POINTS - points
        else -> null
    }
}
