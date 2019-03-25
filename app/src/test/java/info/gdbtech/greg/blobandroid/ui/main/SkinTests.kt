package info.gdbtech.greg.blobandroid.ui.main

import org.junit.Assert.assertEquals
import org.junit.Test

class SkinTests {
    @Test
    fun ctor_neighborTest() {
        val cxA = 1.0f
        val cyA = 3.0f
        val pointMassA = PointMass(cxA, cyA, 0.0f)

        val cxB = 4.0f
        val cyB = 7.0f
        val pointMassB = PointMass(cxB, cyB, 0.0f)

        val skin = Skin(pointMassA, pointMassB)

        assertEquals(5.0f, skin.length, 0.01f)
        assertEquals(25.0f, skin.lengthSquared, 0.01f)
    }

    @Test
    fun limitTest() {
        val cxA = 1.0f
        val cyA = 3.0f
        val pointMassA = PointMass(cxA, cyA, 0.0f)

        val cxB = 4.0f
        val cyB = 7.0f
        val pointMassB = PointMass(cxB, cyB, 0.0f)

        val skin = Skin(pointMassA, pointMassB)
        skin.scale(10.0f)

        assertEquals(50.0f, skin.length, 0.01f)
        assertEquals(2500.0f, skin.lengthSquared, 0.01f)
    }
}