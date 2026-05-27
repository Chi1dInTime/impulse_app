package com.vasal.impulse.domain

import org.junit.Assert.assertEquals
import org.junit.Test

class AppInfoTest {
    @Test
    fun appNameIsImpulse() {
        assertEquals("Импульс", AppInfo.name)
    }
}
