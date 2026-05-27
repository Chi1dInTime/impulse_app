package com.vasal.impulse.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class DayProgressCalculatorTest {
    @Test
    fun pointsBelowMinimumFillOnlyGreenPartially() {
        val progress = DayProgressCalculator.calculate(20)

        assertEquals(0.5f, progress.minimumProgress, 0.001f)
        assertEquals(0f, progress.strongProgress, 0.001f)
        assertEquals(0f, progress.superProgress, 0.001f)
        assertEquals(DayProgressLevel.Neutral, progress.level)
        assertEquals(20, progress.pointsUntilNextLevel)
    }

    @Test
    fun pointsAboveMinimumFillBlueOverGreen() {
        val progress = DayProgressCalculator.calculate(65)

        assertEquals(1f, progress.minimumProgress, 0.001f)
        assertEquals(0.5f, progress.strongProgress, 0.001f)
        assertEquals(0f, progress.superProgress, 0.001f)
        assertEquals(DayProgressLevel.Minimum, progress.level)
        assertEquals(25, progress.pointsUntilNextLevel)
    }

    @Test
    fun pointsAboveStrongFillPurpleOverBlue() {
        val progress = DayProgressCalculator.calculate(120)

        assertEquals(1f, progress.minimumProgress, 0.001f)
        assertEquals(1f, progress.strongProgress, 0.001f)
        assertEquals(0.5f, progress.superProgress, 0.001f)
        assertEquals(DayProgressLevel.Strong, progress.level)
        assertEquals(30, progress.pointsUntilNextLevel)
    }
}
