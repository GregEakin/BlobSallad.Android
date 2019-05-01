package dev.eakin.blobandroid.ui.main

import dev.eakin.blobandroid.ui.main.PointMass
import dev.eakin.blobandroid.ui.main.Vector
import org.junit.Assert.assertEquals
import org.junit.Test

class PointMassTests {
    @Test
    fun ctor_test() {
        val pointMass = PointMass(31.0f, 23.0f, 11.0f)
        assertEquals(11.0f, pointMass.mass, 0.001f)
    }

    @Test
    fun velocityTest() {
        val pointMass = PointMass(31.0f, 23.0f, 11.0f)
        pointMass.force = Vector(7.0f, 13.0f)
        pointMass.move(3.0f)

        val velocity = pointMass.velocity
        assertEquals(145.934f, velocity, 0.001f)
    }

    @Test
    fun move_test() {
        val pointMass = PointMass(31.0f, 23.0f, 11.0f)
        val force = Vector(7.0f, 13.0f)
        pointMass.force = force

        pointMass.move(3.0f)
        assertEquals(31.0f, pointMass.xPrev, 0.01f)
        assertEquals(23.0f, pointMass.yPrev, 0.01f)
        assertEquals(36.727f, pointMass.xPos, 0.01f)
        assertEquals(33.636f, pointMass.yPos, 0.01f)
    }
}