package info.gdbtech.greg.blobandroid.ui.main

import org.junit.Assert.assertEquals
import org.junit.Test

class NeighborTests {
    @Test
    fun ctor_neighborTest() {
        val cxA = 41.0f
        val cyA = 43.0f
        val pointMassA = PointMass(cxA, cyA, 0.0f)

        val cxB = 71.0f
        val cyB = 67.0f
        val pointMassB = PointMass(cxB, cyB, 0.0f)

        // sum of the two radii
        val dist = 17.0f
        val neighbor = Neighbor(pointMassA, pointMassB, dist)

        assertEquals(dist, neighbor.limit, 0.01f)
        assertEquals(dist * dist, neighbor.limitSquared, 0.01f)
    }

    @Test
    fun limitTest() {
        val cxA = 41.0f
        val cyA = 43.0f
        val pointMassA = PointMass(cxA, cyA, 0.0f)

        val cxB = 71.0f
        val cyB = 67.0f
        val pointMassB = PointMass(cxB, cyB, 0.0f)

        // sum of the two radii
        val dist = 17.0f
        val neighbor = Neighbor(pointMassA, pointMassB, dist)
        neighbor.scale(10.0f)

        assertEquals(10.0f * dist, neighbor.limit, 0.01f)
        assertEquals(100.0f * dist * dist, neighbor.limitSquared, 0.01f)
    }
}