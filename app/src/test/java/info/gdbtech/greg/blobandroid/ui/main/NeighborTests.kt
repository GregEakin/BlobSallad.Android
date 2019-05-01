package info.gdbtech.greg.blobandroid.ui.main

import org.junit.Assert.assertEquals
import org.junit.Test

class NeighborTests {
    @Test
    fun ctorTest() {
        val cxA = 41.0f
        val cyA = 43.0f
        val pointMassA = PointMass(cxA, cyA, 0.0f)

        val cxB = 71.0f
        val cyB = 67.0f
        val pointMassB = PointMass(cxB, cyB, 0.0f)

        // sum of the two radii
        val distance = 17.0f
        val neighbor = Neighbor(pointMassA, pointMassB, distance)

        assertEquals(distance, neighbor.limit, 0.01f)
        assertEquals(distance * distance, neighbor.limitSquared, 0.01f)
    }

    @Test
    fun scaleTest() {
        val cxA = 41.0f
        val cyA = 43.0f
        val pointMassA = PointMass(cxA, cyA, 0.0f)

        val cxB = 71.0f
        val cyB = 67.0f
        val pointMassB = PointMass(cxB, cyB, 0.0f)

        // sum of the two radii
        val distance = 17.0f
        val neighbor = Neighbor(pointMassA, pointMassB, distance)
        neighbor.scale(10.0f)

        assertEquals(10.0f * distance, neighbor.limit, 0.01f)
        assertEquals(100.0f * distance * distance, neighbor.limitSquared, 0.01f)
    }

    @Test
    fun scUnderLimitTest() {
        val cxA = 1.0f
        val cyA = 3.0f
        val pointMassA = PointMass(cxA, cyA, 0.0f)

        val cxB = 4.0f
        val cyB = 7.0f
        val pointMassB = PointMass(cxB, cyB, 1.0f)

        val distance = 17.0f
        val neighbor = Neighbor(pointMassA, pointMassB, distance)
        pointMassB.force = Vector(-1.0f, -1.0f)
        pointMassB.move(1.0f)
        neighbor.sc()

        assertEquals(0.08609271f, pointMassA.xPos, 0.01f)
        assertEquals(1.6291391f, pointMassA.yPos, 0.01f)
        assertEquals(3.9139073f, pointMassB.xPos, 0.01f)
        assertEquals(7.370861f, pointMassB.yPos, 0.01f)
    }

    @Test
    fun scUnderLimitLengthTest() {
        val cxA = 1.0f
        val cyA = 3.0f
        val pointMassA = PointMass(cxA, cyA, 0.0f)

        val cxB = 4.0f
        val cyB = 7.0f
        val pointMassB = PointMass(cxB, cyB, 1.0f)

        val distance = 17.0f
        val neighbor = Neighbor(pointMassA, pointMassB, distance)
        pointMassB.force = Vector(-1.0f, -1.0f)
        pointMassB.move(1.0f)
        neighbor.sc()

        val aXbX = pointMassA.xPos - pointMassB.xPos
        val aYbY = pointMassA.yPos - pointMassB.yPos
        val lengthSquared = aXbX * aXbX + aYbY * aYbY
        val length = Math.sqrt(lengthSquared.toDouble()).toFloat()

        assertEquals(6.900691f, length, 0.01f)
        assertEquals(47.619537f, lengthSquared, 0.01f)
    }

    @Test
    fun scOverLimitTest() {
        val cxA = 1.0f
        val cyA = 3.0f
        val pointMassA = PointMass(cxA, cyA, 0.0f)

        val cxB = 4.0f
        val cyB = 7.0f
        val pointMassB = PointMass(cxB, cyB, 1.0f)

        val distance = 17.0f
        val neighbor = Neighbor(pointMassA, pointMassB, distance)
        pointMassB.force = Vector(10.0f, 10.0f)
        pointMassB.move(1.0f)
        neighbor.sc()

        assertEquals(1.0f, pointMassA.xPos, 0.01f)
        assertEquals(3.0f, pointMassA.yPos, 0.01f)
        assertEquals(14.0f, pointMassB.xPos, 0.01f)
        assertEquals(17.0f, pointMassB.yPos, 0.01f)
    }
}