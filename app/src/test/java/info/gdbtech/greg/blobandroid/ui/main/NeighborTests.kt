package info.gdbtech.greg.blobandroid.ui.main

import org.junit.Assert.assertEquals
import org.junit.Test

class NeighborTests {
    @Test
    fun ctor_neighborTest() {
        val cxA = 41.0f
        val cyA = 43.0f
        val massA = 4.0f
        val pointMassA = PointMass(cxA, cyA, massA)

        val cxB = 71.0f
        val cyB = 67.0f
        val massB = 1.0f
        val pointMassB = PointMass(cxB, cyB, massB)

        // sum of the two radii
        val dist = 17.0f
        val neighbor = Neighbor(pointMassA, pointMassB, dist)

        assertEquals(dist, neighbor.limit, 0.01f)
    }
}