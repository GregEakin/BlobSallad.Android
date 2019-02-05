package info.gdbtech.greg.myapplicationii.ui.main

import org.junit.Assert.assertEquals
import org.junit.Test

public class PointMassTests {
    @Test
    fun ctor_test() {
        val pointMass = PointMass(31.0, 23.0, 11.0)
        assertEquals(11.0, pointMass.mass, 0.001)
    }

    @Test
    fun velocityTest() {
        val pointMass = PointMass(31.0, 23.0, 11.0)
        pointMass.force = Vector(7.0, 13.0)
        pointMass.move(3.0)

        val velocity = pointMass.velocity
        assertEquals(145.934, velocity, 0.001)
    }

    @Test
    fun move_test() {
        val pointMass = PointMass(31.0, 23.0, 11.0)
        val force = Vector(7.0, 13.0)
        pointMass.force = force

        pointMass.move(3.0)
        assertEquals(31.0, pointMass.xPrev, 0.01);
        assertEquals(23.0, pointMass.yPrev, 0.01);
        assertEquals(36.727, pointMass.xPos, 0.01);
        assertEquals(33.636, pointMass.yPos, 0.01);
    }
}