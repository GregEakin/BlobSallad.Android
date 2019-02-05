package info.gdbtech.greg.myapplicationii.ui.main

import org.junit.Assert.assertEquals
import org.junit.Test

public class CollisionTests {
    @Test
    fun ctor_collisionTest() {
        val cxA = 41.0;
        val cyA = 43.0;
        val massA = 4.0;
        val pointMassA = PointMass(cxA, cyA, massA)

        val cxB = 71.0;
        val cyB = 67.0;
        val massB = 1.0;
        val pointMassB = PointMass(cxB, cyB, massB)

        // sum of the two radii
        val dist = 17.0;
        val collision = Collision(pointMassA, pointMassB, dist);

        assertEquals(17.000, collision.shortLimit, 0.01)
        assertEquals(Double.POSITIVE_INFINITY, collision.longLimit, 0.001)
    }
}