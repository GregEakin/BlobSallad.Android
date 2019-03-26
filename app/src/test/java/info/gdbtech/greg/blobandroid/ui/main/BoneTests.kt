package info.gdbtech.greg.blobandroid.ui.main

import org.junit.Assert.assertEquals
import org.junit.Test

class BoneTests {
    @Test
    fun ctorTest() {
        val cxA = 1.0f
        val cyA = 3.0f
        val pointMassA = PointMass(cxA, cyA, 0.0f)

        val cxB = 4.0f
        val cyB = 7.0f
        val pointMassB = PointMass(cxB, cyB, 0.0f)

        val short = 2.0f
        val long = 3.0f
        val bone = Bone(pointMassA, pointMassB, short, long)

        assertEquals(10.0f, bone.shortLimit, 0.01f)
        assertEquals(15.0f, bone.longLimit, 0.01f)
        assertEquals(100.0f, bone.slSquared, 0.01f)
        assertEquals(225.0f, bone.llSquared, 0.01f)
    }

    @Test
    fun scaleTest() {
        val cxA = 1.0f
        val cyA = 3.0f
        val pointMassA = PointMass(cxA, cyA, 0.0f)

        val cxB = 4.0f
        val cyB = 7.0f
        val pointMassB = PointMass(cxB, cyB, 0.0f)

        val short = 2.0f
        val long = 3.0f
        val bone = Bone(pointMassA, pointMassB, short, long)
        bone.scale(10.0f)

        assertEquals(100.0f, bone.shortLimit, 0.01f)
        assertEquals(150.0f, bone.longLimit, 0.01f)
        assertEquals(10000.0f, bone.slSquared, 0.01f)
        assertEquals(22500.0f, bone.llSquared, 0.01f)
    }

    @Test
    fun scNeitherTest() {
    }

    @Test
    fun scCompressionTest() {
    }

    @Test
    fun scCompressionLengthTest() {
    }

    @Test
    fun scExpansionTest() {
    }

    @Test
    fun scExpansionLengthTest() {
    }
}